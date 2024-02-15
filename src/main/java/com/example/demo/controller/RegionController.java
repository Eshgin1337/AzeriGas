package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Region;
import com.example.demo.service.RegionService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/regions")
public class RegionController {

    private RegionService regionService;
    
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public List<Region> getRegions() {
        return regionService.getRegions(); 
    }

    @GetMapping("{id}")
    public ResponseEntity<Region> getRegionById(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<Region>(regionService.getRegionById(id), HttpStatus.OK);
    }
    
    
}
