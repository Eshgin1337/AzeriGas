package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Abonent;
import com.example.demo.model.Depts;


public interface DeptsRepository extends JpaRepository<Depts, Long>{
    List<Depts> findByAbonent(Abonent abonent);
    List<Depts> findByAbonent_AbonentCode(String abonentCode);
}
