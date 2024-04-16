package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Abonent;
import com.example.demo.model.User;
import com.example.demo.model.UserAbonent;
import com.example.demo.repository.AbonentRepository;
import com.example.demo.repository.UserAbonentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import org.modelmapper.ModelMapper;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    private UserRepository userRepository;
    private UserAbonentRepository userAbonentRepository;
    private AbonentRepository abonentRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, 
            UserAbonentRepository userAbonentRepository, AbonentRepository abonentRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userAbonentRepository = userAbonentRepository;
    }

    @Override
    public UserDTO registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("username already exists!");
        }

        String encodedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(encodedPassword);

        User savedUser = userRepository.save(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(savedUser.getId());
        userDTO.setUsername(savedUser.getUsername());

        return userDTO;
    }

    @Override
    public UserDTO getUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("This user does not exist!");
        }

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return userDTO;
    }

    @Override
    public UserDTO updateUsername(Long id, String username) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser == null) {
            throw new RuntimeException("This user does not exist!");
        }

        existingUser.setUsername(username);
        userRepository.save(existingUser);

        UserDTO userDTO = modelMapper.map(existingUser, UserDTO.class);

        return userDTO;
    }

    @Override
    public ResponseEntity<Void> updatePassword(Long id, String oldPassword, String newPassword) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (!passwordEncoder.matches(oldPassword, existingUser.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Old password doesn't match
        }

        String newHashedPassword = passwordEncoder.encode(newPassword);
        existingUser.setPasswordHash(newHashedPassword);
        userRepository.save(existingUser);
        return ResponseEntity.status(HttpStatus.OK).build(); // Password updated successfully
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        existingUser.setIsActive(false);
        userRepository.save(existingUser);
        userAbonentRepository.deleteById(existingUser.getId());
        return ResponseEntity.status(HttpStatus.OK).build(); // User deleted successfully
    }

    @Override
    public ResponseEntity<Void> connectUserToAbonent(long userId, long abonentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        Abonent abonent = abonentRepository.findById(abonentId)
                .orElseThrow(() -> new ResourceNotFoundException("Abonent", "Id", abonentId));

        UserAbonent userAbonent = new UserAbonent();
        userAbonent.setUserID(user);
        userAbonent.setAbonentID(abonent);

        userAbonentRepository.save(userAbonent);
        return ResponseEntity.status(HttpStatus.OK).build(); 
    }
}
