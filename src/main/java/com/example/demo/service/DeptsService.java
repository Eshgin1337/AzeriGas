package com.example.demo.service;


import java.util.List;

import com.example.demo.model.Depts;

public interface DeptsService {
    Depts addDeptUpdate(Integer payment, String abonentCode);
    List<Depts> getDeptsByAbonentCode(String abonentCode);
}
