package com.example.demo.service;

import org.springframework.http.ResponseEntity;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;

public interface UserService {
    UserDTO registerUser(User user);
    UserDTO getUser(String username);
    UserDTO updateUsername(Long id, String username);
    ResponseEntity<Void> updatePassword(Long id, String oldPassword, String newPassword);
    ResponseEntity<Void> deleteUser(Long id);
    ResponseEntity<Void> connectUserToAbonent(long userId, long abonentId);
}
