package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Abonent;

public interface AbonentRepository extends JpaRepository<Abonent, Long>{
    Abonent findByAbonentCode(String abonentCode);
}
