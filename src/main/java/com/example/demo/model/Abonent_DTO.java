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
public class Abonent_DTO {

    private long id;

    private String name;

    private String surname;

    private String patronymic;

    private String address;

    private String telephone;

    private String getMeterNumber;

    private String abonentCode;

    private String regionName;

    private String tariffName;

}
