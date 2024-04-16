package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Abonent;
import com.example.demo.model.User;
import com.example.demo.model.UserAbonent;
import java.util.List;

public interface UserAbonentRepository extends JpaRepository<UserAbonent, Long> {
    List<UserAbonent> findByUserID_Id(Long userId);
    void deleteByUserID(User user);
    UserAbonent findByUserIDAndAbonentID(User user, Abonent abonent);
    void deleteByAbonentID(Abonent abonentId);
}