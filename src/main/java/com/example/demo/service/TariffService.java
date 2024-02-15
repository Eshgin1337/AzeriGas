package com.example.demo.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Tariff;

public interface TariffService{
    List<Tariff> getTariffs();
    Tariff getTariffById(Integer id);
    Tariff getTariffByName(String tariffName);
}
