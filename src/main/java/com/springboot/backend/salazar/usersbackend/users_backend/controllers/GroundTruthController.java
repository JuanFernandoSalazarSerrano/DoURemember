package com.springboot.backend.salazar.usersbackend.users_backend.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.springboot.backend.salazar.usersbackend.users_backend.entities.GroundTruthAnswer;
import com.springboot.backend.salazar.usersbackend.users_backend.entities.GroundTruthResponse;
import com.springboot.backend.salazar.usersbackend.users_backend.services.GroundTruthService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/groundtruth")
public class GroundTruthController {

    @Autowired
    private GroundTruthService service;

    private final List<SseEmitter> emitters = Collections.synchronizedList(new ArrayList<>());

    @GetMapping("/stream")
    public SseEmitter stream() {
        SseEmitter emitter = new SseEmitter(0L); // 0 = no timeout / adjust as needed
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(e -> emitters.remove(emitter));

        return emitter;
    }

    @PostMapping("/sendUserAnswer")
    public ResponseEntity<?> sendUserAnswer(@RequestBody GroundTruthAnswer userAnswer) {
        service.generateGroundTruthTestSendAnswer(userAnswer.getUserAnswer());
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("succes", "LLM evaluating your answer"));
    }

    @PostMapping("/saveAiResponse")
    public ResponseEntity<GroundTruthResponse> saveAiResponse(@RequestBody GroundTruthResponse llmResponse) {
        System.out.println("1789");
        service.saveGroundTruthResponse(llmResponse);

        // broadcast to all connected clients
        synchronized (emitters) {
            for (SseEmitter emitter : emitters) {
                try {
                    emitter.send(SseEmitter.event()
                        .name("saved")
                        .data(llmResponse)); // sends JSON via message event
                } catch (IOException e) {
                    emitter.completeWithError(e);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(llmResponse);
    }
}