package com.springboot.backend.salazar.usersbackend.users_backend.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.springboot.backend.salazar.usersbackend.users_backend.entities.User;
import com.springboot.backend.salazar.usersbackend.users_backend.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;


// the user interacts with the controller and this one comunicates with the service
// it works with DTO'S
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService service;


    @GetMapping
    public List<User> listAllUsers(){
        return service.findAll();
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

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result){

        if (result.hasErrors()) {
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable Long id, @Valid @RequestBody User user, BindingResult result) {
        
        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<User> userOptional = service.findById(id);

        if (userOptional.isPresent()){
            User userDb = userOptional.orElseThrow();
            userDb.setEmail(user.getEmail());
            userDb.setLastname(user.getLastname());
            userDb.setName(user.getName());
            userDb.setPassword(user.getPassword());
            userDb.setUsername(user.getUsername());

            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(userDb));

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
