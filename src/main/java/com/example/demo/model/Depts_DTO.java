package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Depts_DTO {

    public Depts_DTO(long id, Integer remainingDept, LocalDateTime date, String abonentCode) {
        this.id = id;
        this.remainingDept = remainingDept;
        this.date = date;
        this.abonentCode = abonentCode;
    }

    private long id;

    private Integer remainingDept;

    private LocalDateTime date;

    private String abonentCode;   
}
