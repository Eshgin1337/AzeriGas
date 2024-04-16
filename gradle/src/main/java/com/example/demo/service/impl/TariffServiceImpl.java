package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Tariff;
import com.example.demo.repository.TariffRepository;
import com.example.demo.service.TariffService;

@Service
public class TariffServiceImpl implements TariffService {

    private TariffRepository tariffRepository;

    public TariffServiceImpl(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }


    @Override
    public List<Tariff> getTariffs() {
        return tariffRepository.findAll();
    }


    @Override
    public Tariff getTariffById(Integer id) {
        return tariffRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tariff", "id", id));
    }


    @Override
    public Tariff getTariffByName(String tariffName) {
        return tariffRepository.findByTariffName(tariffName);
    }
    
}
