package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Transactions_DTO {
    private Integer transactionAmount;
    private LocalDateTime transactionDate;
}
