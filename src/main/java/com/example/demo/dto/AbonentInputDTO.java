package com.example.demo.dto;

import lombok.Data;

@Data
public class AbonentInputDTO {

    private long id;

    private String name;

    private String surname;

    private String patronymic;

    private String address;

    private String telephone;

    private String meterNumber;

    private String abonentCode;

    private String regionName;

    private String tariffName;

}
