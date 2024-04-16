package com.example.demo.service;

import org.springframework.http.ResponseEntity;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.User;

public interface UserService {
    boolean isAuthorized(String username);
    String getUsername();
    User getUserById(Long id);
    ResponseEntity<?> registerUser(RegisterRequest user);
    ResponseEntity<?> login(LoginRequest request);
    ResponseEntity<?> getUser();
    ResponseEntity<?> updateUsername(String newUsername);
    ResponseEntity<?> updatePassword(String oldPassword, String newPassword);
    ResponseEntity<?> deleteUser();
    ResponseEntity<?> connectUserToAbonent(String abonentCode);
}
