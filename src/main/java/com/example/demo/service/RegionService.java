package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Region;

public interface RegionService{
    List<Region> getRegions(); 
    Region getRegionById(Integer id);
    Region getRegionByName(String regionName);
}
