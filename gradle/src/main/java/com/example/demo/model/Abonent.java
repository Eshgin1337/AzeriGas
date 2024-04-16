package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "abonents")
public class Abonent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "patronymic", nullable = false)
    private String patronymic;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Column(name = "meter_number", nullable = false)
    private String meterNumber;

    @Column(name = "abonent_code", nullable = false)
    private String abonentCode;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive;

    public Abonent() {
        this.isActive = true; // Set default value to true
    }

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region regionID;

    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private Tariff tariffID;

}
