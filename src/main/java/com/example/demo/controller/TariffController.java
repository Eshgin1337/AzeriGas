package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Tariff;
import com.example.demo.service.TariffService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/tariffs")
public class TariffController {
    private TariffService tariffService;

    public TariffController(TariffService tariffService) {
        super();
        this.tariffService = tariffService;
    }

    @GetMapping
    public List<Tariff> getTariffs() {
        return tariffService.getTariffs();
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Tariff> getTariffById(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<Tariff>(tariffService.getTariffById(id), HttpStatus.OK);
    }
    
}
