package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.example.demo.service.AbonentService;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Abonent;
import com.example.demo.repository.AbonentRepository;;

@Service
public class AbonentServiceImpl implements AbonentService{
    private AbonentRepository abonentRepository;

    public AbonentServiceImpl(AbonentRepository abonentRepository) {
        super();
        this.abonentRepository = abonentRepository;
    }

    @Override
    public Abonent saveAbonent(Abonent abonent) {
        return abonentRepository.save(abonent);
    }

    @Override
    public List<Abonent> getAbonents() {
        return abonentRepository.findAll();
    }

    @Override
    public Abonent getAbonentById(long Id) {
        Optional<Abonent> abonent = abonentRepository.findById(Id);
        if (abonent.isPresent()) {
            return abonent.get();
        } else {
            throw new ResourceNotFoundException("Abonent", "Id", Id);
        }
    }

    @Override
    public Abonent updateAbonent(Abonent abonent, long Id) {
        Abonent existingAbonent = abonentRepository.findById(Id).orElseThrow(() -> 
            new ResourceNotFoundException("Abonent", "Id", Id));
        existingAbonent.setName(abonent.getName());
        existingAbonent.setSurname(abonent.getSurname());
        abonentRepository.save(existingAbonent);
        return existingAbonent;
    }

    @Override
    public void deleteAbonent(long Id) {
        abonentRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", Id));
        abonentRepository.deleteById(Id);
    }

    @Override
    public Abonent getAbonentByAbonentCode(String abonentCode) {
        return abonentRepository.findByAbonentCode(abonentCode);
    }
}
