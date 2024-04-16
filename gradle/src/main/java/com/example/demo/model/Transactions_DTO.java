package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Transactions_DTO {
    private Integer transactionAmount;
    private LocalDateTime transactionDate;
}
