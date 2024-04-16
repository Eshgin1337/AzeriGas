package com.example.demo.dto;

import lombok.Data;

@Data
public class AbonentOutputDTO {
    private String abonentCode;
    private String name;
    private String surname;
    private String patronymic;
    private String meterNumber; // meternumber only
    private Integer latestRemainingDept;
    private String tariffName;
    private String regionName;
    //private List<Depts_DTO> deptHistory;
}
