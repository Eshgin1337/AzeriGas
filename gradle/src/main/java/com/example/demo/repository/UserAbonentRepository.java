package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.UserAbonent;

public interface UserAbonentRepository extends JpaRepository<UserAbonent, Long> {
    
}
