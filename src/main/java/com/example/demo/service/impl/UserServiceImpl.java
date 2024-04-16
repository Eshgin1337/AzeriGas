package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Abonent;
import com.example.demo.model.AuthenticationResponse;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.Role;
import com.example.demo.model.Role.Roles;
import com.example.demo.model.User;
import com.example.demo.model.UserAbonent;
import com.example.demo.repository.AbonentRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserAbonentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import jakarta.transaction.Transactional;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.modelmapper.ModelMapper;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserAbonentRepository userAbonentRepository;
    private AbonentRepository abonentRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, 
            UserAbonentRepository userAbonentRepository, AbonentRepository abonentRepository, 
            RoleRepository roleRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userAbonentRepository = userAbonentRepository;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
        this.abonentRepository = abonentRepository;
    }

    @Override
    public ResponseEntity<?> registerUser(RegisterRequest user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists!");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        
        Role role = roleRepository.findByRole(Roles.ABONENT)
                .orElseThrow(() -> new RuntimeException("Role not found!"));

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPasswordHash(encodedPassword);
        newUser.setRoleID(role);
        userRepository.save(newUser);

        String token = jwtService.generateToken(newUser);
        
        return ResponseEntity.ok(AuthenticationResponse.builder().token(token).build());
    }

    @Override
    public ResponseEntity<?> login(LoginRequest request) {
        if (!userRepository.findByUsername(request.getUsername()).getIsActive()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This account has been deleted");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(AuthenticationResponse.builder().token(token).build());
    }

    @Override
    public ResponseEntity<?> getUser() {
        
        User user = userRepository.findByUsername(getUsername());
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setId(user.getId());
        List<UserAbonent> userAbonentList = userAbonentRepository.findByUserID_Id(user.getId());

        List<Abonent> abonents = new ArrayList<>();

        for (UserAbonent userAbonent : userAbonentList) {
            Abonent abonent = userAbonent.getAbonentID();
            abonents.add(abonent);
        }

        userDTO.setAbonents(abonents);

        return ResponseEntity.ok(userDTO);
    }




    @Override
    public ResponseEntity<?> updateUsername(String newUsername) {
        User existingUser = userRepository.findByUsername(getUsername());
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This account does not exist");
        }

        if (!existingUser.getIsActive()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This account has been deleted");
        }

        existingUser.setUsername(newUsername);
        userRepository.save(existingUser);

        UserDTO userDTO = modelMapper.map(existingUser, UserDTO.class);

        return ResponseEntity.ok(userDTO);
    }

    @Override
    public ResponseEntity<?> updatePassword(String oldPassword, String newPassword) {
        User existingUser = userRepository.findByUsername(getUsername());

        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"); // User not found
        }
        
        if (oldPassword.equals(newPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old and new passwords are the same"); 
        }

        if (!passwordEncoder.matches(oldPassword, existingUser.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad credentials"); // Old password doesn't match
        }

        String newHashedPassword = passwordEncoder.encode(newPassword);
        existingUser.setPasswordHash(newHashedPassword);
        userRepository.save(existingUser);
        return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully"); // Password updated successfully
    }


    @Override
    @Transactional
    public ResponseEntity<?> deleteUser() {
        User existingUser = userRepository.findByUsername(getUsername());
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"); // User not found
        }

        if (!existingUser.getIsActive()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This account has been deleted");
        }
        
        existingUser.setIsActive(false);
        userRepository.save(existingUser);
        
        // Delete rows from userAbonent table
        userAbonentRepository.deleteByUserID(existingUser);

        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully"); 
    }


    @Override
    public ResponseEntity<?> connectUserToAbonent(String abonentCode) {
        User user = userRepository.findByUsername(getUsername());

        Abonent abonent = abonentRepository.findByAbonentCode(abonentCode);

        if (abonent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This abonent does not exist");
        }

        if (!abonent.getIsActive()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This abonent has been deleted by admin");
        }
        
        UserAbonent existingConnection = userAbonentRepository.findByUserIDAndAbonentID(user, abonent);
        if (existingConnection != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This abonent is already connected to this user");
        }

        UserAbonent userAbonent = new UserAbonent();
        userAbonent.setUserID(user);
        userAbonent.setAbonentID(abonent);

        userAbonentRepository.save(userAbonent);
        return ResponseEntity.status(HttpStatus.OK).build(); 
    }

    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        return user;
    }

    @Override
    public boolean isAuthorized(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getName().equals(username);
    }

    @Override
    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
