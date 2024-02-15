package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Abonent;
import com.example.demo.model.Abonent_DTO;
import com.example.demo.model.Region;
import com.example.demo.model.Tariff;
import com.example.demo.repository.RegionRepository;
import com.example.demo.repository.TariffRepository;
import com.example.demo.service.AbonentService;
import com.example.demo.service.RegionService;
import com.example.demo.service.TariffService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

import org.modelmapper.ModelMapper;



@RestController
@RequestMapping("/api/abonents")
public class AbonentController {
    
    private AbonentService abonentService;
    private RegionService regionService;
    private TariffService tariffService;

    @Autowired
    private ModelMapper modelMapper;

    public AbonentController(AbonentService abonentService, RegionService regionService, TariffService tariffService, ModelMapper modelMapper) {
        this.abonentService = abonentService;
        this.tariffService = tariffService;
        this.regionService = regionService;
        this.modelMapper = modelMapper;
    }


    @PostMapping
    public ResponseEntity<Abonent_DTO> saveAbonent(@RequestBody Abonent_DTO abonent_dto) {

        Abonent abonent = modelMapper.map(abonent_dto, Abonent.class);

        Region region = regionService.getRegionByName(abonent_dto.getRegionName());
        abonent.setRegion(region);

        // Find tariff by name and set the tariff_id
        Tariff tariff = tariffService.getTariffByName(abonent_dto.getTariffName());
        abonent.setTariff(tariff);
        
        Abonent_DTO abonentDTO = modelMapper.map(abonent, Abonent_DTO.class);
        abonentService.saveAbonent(abonent);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(abonentDTO);
    }


    // Build get all employees rest API
    @GetMapping
    public List<Abonent> getAbonents() {
        return abonentService.getAbonents();
    }
    
    @GetMapping("{abonentCode}")
    public ResponseEntity<Abonent_DTO> getAbonentById(@PathVariable(name = "abonentCode") String abonentCode) {
        Abonent abonent = abonentService.getAbonentByAbonentCode(abonentCode);
        Abonent_DTO abonentDTO = modelMapper.map(abonent, Abonent_DTO.class);

        return ResponseEntity.status(HttpStatus.FOUND).body(abonentDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<Abonent> updateAbonent(@PathVariable("id") long id, @RequestBody Abonent abonent) {
        return new ResponseEntity<Abonent>(abonentService.updateAbonent(abonent, id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAbonent(@PathVariable("id") long id) {
        abonentService.deleteAbonent(id);
        return new ResponseEntity<String>("Employee deleted successfully!", HttpStatus.OK);
    }
}
