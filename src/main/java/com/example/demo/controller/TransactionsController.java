package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;

import com.example.demo.model.AbonentOutputDTO;
import com.example.demo.model.Transactions;
import com.example.demo.model.Transactions_DTO;
import com.example.demo.service.DeptsService;
import com.example.demo.service.TransactionsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {
    
    private TransactionsService transactionsService;

    
    @Autowired
    private ModelMapper modelMapper;

    public TransactionsController(TransactionsService transactionsService, DeptsService deptsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping
    public ResponseEntity<Transactions> addTransaction(@RequestParam("transactionAmount") Integer transactionAmount, @RequestParam("abonentCode") String abonentCode) {
        return new ResponseEntity<Transactions>(transactionsService.addTransaction(transactionAmount, abonentCode), HttpStatus.OK);
    }

    @GetMapping("{abonentCode}")
    public List<Transactions_DTO> getTransactionsByAbonentCode(@PathVariable("abonentCode") String abonentCode) {
        List<Transactions> transactions = transactionsService.getTransactionsByAbonentCode(abonentCode);


        
        List<Transactions_DTO> dtoList = transactions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        return dtoList;
    }

    private Transactions_DTO convertToDTO(Transactions transaction) {
        Transactions_DTO dto = new Transactions_DTO();
        dto.setTransactionAmount(transaction.getTransactionAmount());
        dto.setTransactionDate(transaction.getTransactionDate());
        return dto;
    }
}
