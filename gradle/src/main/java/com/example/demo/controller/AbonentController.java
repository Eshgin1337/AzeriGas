package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

import com.example.demo.dto.AbonentInputDTO;
import com.example.demo.dto.AbonentOutputDTO;
import com.example.demo.model.Abonent;
import com.example.demo.model.Depts;
import com.example.demo.model.Region;
import com.example.demo.model.Tariff;
import com.example.demo.service.AbonentService;
import com.example.demo.service.DeptsService;
import com.example.demo.service.RegionService;
import com.example.demo.service.TariffService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import org.modelmapper.ModelMapper;



@RestController
@RequestMapping("/api/abonents")
public class AbonentController {
    
    private AbonentService abonentService;
    private RegionService regionService;
    private TariffService tariffService;

    @Autowired
    private DeptsService deptsService;

    @Autowired
    private ModelMapper modelMapper;

    public AbonentController(AbonentService abonentService, RegionService regionService, 
            TariffService tariffService, ModelMapper modelMapper) {
        this.abonentService = abonentService;
        this.tariffService = tariffService;
        this.regionService = regionService;
        this.modelMapper = modelMapper;
    }


    @PostMapping
    public ResponseEntity<AbonentInputDTO> saveAbonent(@RequestBody AbonentInputDTO abonent_dto) {

        Abonent abonent = modelMapper.map(abonent_dto, Abonent.class);

        Region region = regionService.getRegionByName(abonent_dto.getRegionName());
        abonent.setRegionID(region);

        // Find tariff by name and set the tariff_id
        Tariff tariff = tariffService.getTariffByName(abonent_dto.getTariffName());
        abonent.setTariffID(tariff);
        
        AbonentInputDTO abonentDTO = modelMapper.map(abonent, AbonentInputDTO.class);
        abonentService.saveAbonent(abonent);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(abonentDTO);
    }


    // Build get all employees rest API
    @GetMapping
    public List<Abonent> getAbonents() {
        return abonentService.getAbonents();
    }
    
    @GetMapping("{abonentCode}")
    public ResponseEntity<AbonentOutputDTO> getAbonentById(@PathVariable String abonentCode) {
        Abonent abonent = abonentService.getAbonentByAbonentCode(abonentCode);
        List<Depts> deptHistory = deptsService.getDeptsByAbonentCode(abonentCode);
        
        // Sort deptHistory by date in descending order
        deptHistory.sort(Comparator.comparing(Depts::getDate).reversed());

        // Get the latest remaining dept
        Integer latestRemainingDept = deptHistory.isEmpty() ? null : deptHistory.get(0).getRemainingDept();

        // Map deptHistory to include only remainingDept and date
        // List<Depts_DTO> filteredDeptHistory = deptHistory.stream()
        //         .map(dept -> new Depts_DTO(dept.getId(), dept.getRemainingDept(), dept.getDate(), dept.getAbonent().getAbonentCode()))
        //         .collect(Collectors.toList());

        AbonentOutputDTO abonentOutputDTO = modelMapper.map(abonent, AbonentOutputDTO.class);
        abonentOutputDTO.setLatestRemainingDept(latestRemainingDept);
        abonentOutputDTO.setRegionName(abonent.getRegionID().getRegionName());
        abonentOutputDTO.setTariffName(abonent.getTariffID().getTariffName());
        // abonentOutputDTO.setDeptHistory(filteredDeptHistory);

        return ResponseEntity.ok(abonentOutputDTO);
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
