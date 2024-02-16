package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Depts;
import com.example.demo.repository.DeptsRepository;
import com.example.demo.service.DeptsService;

@Service
public class DeptsServiceImpl implements DeptsService {

    
    private DeptsRepository deptsRepository;

    public DeptsServiceImpl(DeptsRepository deptsRepository) {
        this.deptsRepository = deptsRepository;
    }

    @Override
    public Depts addDeptUpdate(Integer payment, String abonentCode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateDepts'");
    }

    @Override
    public List<Depts> getDeptsByAbonentCode(String abonentCode) {
        List<Depts> deptHistory = deptsRepository.findByAbonent_AbonentCode(abonentCode);
        return deptHistory;
    }
    
}
