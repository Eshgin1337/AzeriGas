package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tariff")
public class Tariff {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tariff_id;

    @Column(name = "tariff_name", nullable = false)
    private String tariffName;
}
