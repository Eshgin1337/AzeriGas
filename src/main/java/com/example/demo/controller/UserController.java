package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.LoginRequest;
import com.example.demo.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api")
public class UserController {
    
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser() {
        ResponseEntity<?> userDtoResponse = userService.getUser();
        if (userDtoResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(userDtoResponse.getBody());
        } else {
            return ResponseEntity.status(userDtoResponse.getStatusCode()).body(userDtoResponse.getBody());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest user) {
            return ResponseEntity.ok(userService.registerUser(user));
    }

    @PatchMapping("/user/changepassword")
    public ResponseEntity<?> updatePassword(
        @RequestParam(name = "oldPassword") String oldPassword, 
        @RequestParam(name = "newPassword") String newPassword) 
    {
        ResponseEntity<?> responseEntity = userService.updatePassword(oldPassword, newPassword);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }


    @DeleteMapping("/user/delete")
    public ResponseEntity<?> deleteUser() {
        ResponseEntity<?> responseEntity = userService.deleteUser();
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    @PatchMapping("/user/changeusername")
    public ResponseEntity<?> updateUsername(@RequestParam String newUsername) {
        ResponseEntity<?> changeStatus = userService.updateUsername(newUsername);
        return ResponseEntity.status(changeStatus.getStatusCode()).body(changeStatus.getBody());
    }

    @PostMapping("/user/addabonent")
    public ResponseEntity<?> connectUserToAbonent(
            @RequestParam(name = "abonentCode") String abonentCode) {
                System.out.println(abonentCode);
        ResponseEntity<?> responseEntity = userService.connectUserToAbonent(abonentCode);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest user) {
        ResponseEntity<?> responseEntity = userService.login(user);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }
    
}
