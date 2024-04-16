package com.example.demo.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AbonentInputDTO;
import com.example.demo.service.AbonentService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/abonents")
public class AbonentController {
    
    private final AbonentService abonentService;

    public AbonentController(AbonentService abonentService) {
        this.abonentService = abonentService;
    }


    @PostMapping
    public ResponseEntity<?> saveAbonent(@RequestBody AbonentInputDTO abonentDTO) {
        ResponseEntity<?> response = abonentService.saveAbonent(abonentDTO);
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }

    // Build get all employees rest API
    @GetMapping
    public ResponseEntity<?> getAbonents() {
        ResponseEntity<?> response = abonentService.getAbonents();
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }

    
    @GetMapping("{abonentCode}")
    public ResponseEntity<?> getAbonentByAbonentCode(@PathVariable String abonentCode) {
        ResponseEntity<?> response = abonentService.getAbonentByAbonentCode(abonentCode);
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }


    @PutMapping("{id}")
    public ResponseEntity<?> updateAbonent(@PathVariable("id") long id, @RequestBody AbonentInputDTO abonentDTO) {
        ResponseEntity<?> response = abonentService.updateAbonent(abonentDTO, id);
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }



    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAbonent(@PathVariable("id") long id) {
        ResponseEntity<?> response = abonentService.deleteAbonent(id);
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok("Abonent deleted successfully!");
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }

}