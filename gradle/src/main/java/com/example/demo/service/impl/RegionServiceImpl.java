package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Region;
import com.example.demo.repository.RegionRepository;
import com.example.demo.service.RegionService;


@Service
public class RegionServiceImpl implements RegionService {

    private RegionRepository regionRepository;
    

    public RegionServiceImpl(RegionRepository regionRepository) {
        super();
        this.regionRepository = regionRepository;
    }


    @Override
    public List<Region> getRegions() {
        return regionRepository.findAll();
    }


    @Override
    public Region getRegionById(Integer id) {
        return regionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("region", "id", id));
    }


    @Override
    public Region getRegionByName(String regionName) {
        return regionRepository.findByRegionName(regionName);
    }
    
}
