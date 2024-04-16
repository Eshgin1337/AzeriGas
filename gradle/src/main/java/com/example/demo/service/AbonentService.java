package com.example.demo.service;

import java.util.List;
import com.example.demo.model.Abonent;

public interface AbonentService {
    Abonent saveAbonent(Abonent abonent);
    List<Abonent> getAbonents();
    // Abonent getAbonentById(long Id);
    Abonent updateAbonent(Abonent abonent, long Id);
    void deleteAbonent(long Id);
    Abonent getAbonentByAbonentCode(String abonentCode);
}
