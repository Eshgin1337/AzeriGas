package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Region;

public interface RegionRepository extends JpaRepository<Region, Integer>{
    // @Query("SELECT t FROM Thing t WHERE t.region_name = ?1")
    // Region findBy(String region_name);

    Region findByRegionName(String regionName);
}
