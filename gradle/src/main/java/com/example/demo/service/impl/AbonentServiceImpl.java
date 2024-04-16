package com.example.demo.service.impl;

import java.util.List;
// import java.util.Optional;
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

    // @Override
    // public Abonent getAbonentById(long Id) {
    //     Optional<Abonent> abonent = abonentRepository.findById(Id);
    //     if (abonent.isPresent()) {
    //         return abonent.get();
    //     } else {
    //         throw new ResourceNotFoundException("Abonent", "Id", Id);
    //     }
    // }

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
        Abonent abonent = abonentRepository.findById(Id)
            .orElseThrow(() -> new ResourceNotFoundException("Abonent", "id", Id));

        if (abonent.getIsActive()) {
            abonent.setIsActive(false);
            abonentRepository.save(abonent);
        } else {
            // Abonent is already inactive
            throw new RuntimeException("Abonent with id " + Id + " is already inactive.");
        }
    }

    @Override
    public Abonent getAbonentByAbonentCode(String abonentCode) {
        Abonent abonent = abonentRepository.findByAbonentCode(abonentCode);

        if (abonent == null) {
            throw new ResourceNotFoundException("Abonent", "abonentCode", abonentCode);
        }

        if (!abonent.getIsActive()) {
            throw new ResourceNotFoundException("Abonent", "abonentCode", abonentCode + " is inactive.");
        }

        return abonent;
    }

}
