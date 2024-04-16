package com.example.demo.dto;

import lombok.Data;
import java.util.List;

import com.example.demo.model.Abonent;

@Data
public class UserDTO {
    private long id;
    private String username;
    private List<Abonent> abonents;
}
