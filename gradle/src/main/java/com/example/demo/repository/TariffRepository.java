package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Tariff;

public interface TariffRepository extends JpaRepository<Tariff, Integer>{
    Tariff findByTariffName(String tariffName);
}
