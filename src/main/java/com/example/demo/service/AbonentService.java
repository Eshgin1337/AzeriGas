package com.example.demo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.demo.dto.AbonentInputDTO;
import com.example.demo.model.Abonent;

public interface AbonentService {
    ResponseEntity<?> saveAbonent(AbonentInputDTO abonentDTO);
    ResponseEntity<?> getAbonents();
    // Abonent getAbonentById(long Id);
    ResponseEntity<?> updateAbonent(AbonentInputDTO abonent, long Id);
    ResponseEntity<?> deleteAbonent(long Id);
    ResponseEntity<?> getAbonentByAbonentCode(String abonentCode);
}
