package com.springboot.backend.salazar.usersbackend.users_backend.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.backend.salazar.usersbackend.users_backend.entities.MemoryRecall;
import com.springboot.backend.salazar.usersbackend.users_backend.entities.User;
import com.springboot.backend.salazar.usersbackend.users_backend.models.UserRequest;
import com.springboot.backend.salazar.usersbackend.users_backend.repositories.MemoryRecallRepository;
import com.springboot.backend.salazar.usersbackend.users_backend.repositories.UserRepository;
import com.springboot.backend.salazar.usersbackend.users_backend.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;


// the user interacts with the controller and this one comunicates with the service
// it works with DTO'S
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    private UserService service;

    @Autowired
    private MemoryRecallRepository memoryRecallRepository;

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> listAllUsers(){
        return service.findAll();
    }

    @GetMapping("/page/{page}")
    public Page<User> listPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 3);
        return service.findAll(pageable);
    }

    @GetMapping("/getAllUserMemoryRecalls/{id}")
    public List<MemoryRecall> getAllUserMemoryRecalls(@PathVariable Long id) {
        return memoryRecallRepository.findAllByUserId(id);
    }

    @GetMapping("/getAllDoctorPatients/{id}")
    public List<User> getAllDoctorPatients(@PathVariable Long id) {
        return userRepository.findAllByDoctor_Id(id);
    }

    @DeleteMapping("/deleteMemoryRecall/{memoryRecallId}")
    public ResponseEntity<?> deleteMemoryRecallById(@PathVariable Long memoryRecallId){
        memoryRecallRepository.deleteById(memoryRecallId);
        return ResponseEntity.status(HttpStatus.OK).body(memoryRecallId);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result){

        if (result.hasErrors()) {
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id) {

        Optional<User> userOptional = service.findById(id);

        if (userOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                userOptional);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Collections.singletonMap(
                    "error", "User not found with ID: " + id));
        }
    }

    @PostMapping("/createMemoryRecall")
    public ResponseEntity<?> createMemoryRecall(@Valid @RequestBody MemoryRecall memory, BindingResult result){

        if (result.hasErrors()) {
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(memoryRecallRepository.save(memory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable Long id, @Valid @RequestBody UserRequest user, BindingResult result){
        
        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<User> userOptional = service.update(user, id);

        if (userOptional.isPresent()){

            return ResponseEntity.ok(userOptional.orElseThrow());

        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Collections.singletonMap(
                    "error", "The user you want to update could'nt be found with the id: " + id));
        }
    }


    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "Invalid field " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){

        Optional<User> userOptional = service.findById(id);

        if (userOptional.isPresent()){
            service.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                userOptional);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Collections.singletonMap(
                    "error", "User to delete not found with ID: " + id));
        }
    }
}
