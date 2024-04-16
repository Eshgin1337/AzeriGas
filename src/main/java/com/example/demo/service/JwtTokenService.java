package com.example.demo.service;

import org.springframework.security.core.Authentication;
import java.util.List;

public interface JwtTokenService {
    String generateToken(Authentication authentication);
    boolean validateToken(String token);
    String getUsernameFromToken(String token);
    List<String> getRolesFromToken(String token);
}