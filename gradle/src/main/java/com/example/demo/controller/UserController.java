package com.example.demo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api")
public class UserController {
    
    private UserService userService;
    private ModelMapper modelMapper;
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable(name = "username") String username) {
        try {
            return new ResponseEntity<UserDTO>(userService.getUser(username), HttpStatus.FOUND);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // User not found
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user) {
        try {
            return new ResponseEntity<UserDTO>(userService.registerUser(user), HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Username already exists
        }
    }

    
    
}
