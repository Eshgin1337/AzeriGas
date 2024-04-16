package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Comparator;

// import org.apache.logging.log4j.util.PropertySource.Comparator;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import java.util.Optional;
import org.springframework.stereotype.Service;

import com.example.demo.service.AbonentService;
import com.example.demo.service.DeptsService;
import com.example.demo.service.RegionService;
import com.example.demo.service.TariffService;
import com.example.demo.dto.AbonentInputDTO;
import com.example.demo.dto.AbonentOutputDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Abonent;
import com.example.demo.model.Depts;
import com.example.demo.model.Region;
import com.example.demo.model.Tariff;
import com.example.demo.repository.AbonentRepository;
import com.example.demo.repository.UserAbonentRepository;;

@Service
public class AbonentServiceImpl implements AbonentService{
    private final AbonentRepository abonentRepository;
    private final UserAbonentRepository userAbonentRepository;
    private final RegionService regionService;
    private final TariffService tariffService;
    private final DeptsService deptsService;
    private final ModelMapper modelMapper;

    public AbonentServiceImpl(AbonentRepository abonentRepository, UserAbonentRepository userAbonentRepository, 
            RegionService regionService, TariffService tariffService, DeptsService deptsService, ModelMapper modelMapper) 
    {
        this.abonentRepository = abonentRepository;
        this.userAbonentRepository = userAbonentRepository;
        this.regionService = regionService;
        this.tariffService = tariffService;
        this.deptsService = deptsService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<?> saveAbonent(AbonentInputDTO abonentDTO) {
        // Check if an abonent already exists with the provided abonentCode
        Abonent existingAbonentByCode = abonentRepository.findByAbonentCode(abonentDTO.getAbonentCode());

        if (existingAbonentByCode != null) {
            // An abonent already exists with the provided abonentCode
            if (existingAbonentByCode.getIsActive()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("An active abonent with the provided abonent code already exists.");
            } else {
                // The existing abonent is inactive, update its details and set isActive to true
                Abonent abonent = modelMapper.map(abonentDTO, Abonent.class);
                abonent.setId(existingAbonentByCode.getId()); // Ensure the same ID is retained
                abonent.setIsActive(true);

                Region region = regionService.getRegionByName(abonentDTO.getRegionName());
                abonent.setRegionID(region);

                // Find tariff by name and set the tariff_id
                Tariff tariff = tariffService.getTariffByName(abonentDTO.getTariffName());
                abonent.setTariffID(tariff);

                abonentRepository.save(abonent);

                AbonentInputDTO savedAbonentDTO = modelMapper.map(abonent, AbonentInputDTO.class);
                return ResponseEntity.ok(savedAbonentDTO);
            }
        } else {
            // No existing abonent found with the provided abonentCode, proceed with saving
            Abonent abonent = modelMapper.map(abonentDTO, Abonent.class);
            abonent.setIsActive(true); // Set isActive to true for new abonent

            Region region = regionService.getRegionByName(abonentDTO.getRegionName());
            abonent.setRegionID(region);

            // Find tariff by name and set the tariff_id
            Tariff tariff = tariffService.getTariffByName(abonentDTO.getTariffName());
            abonent.setTariffID(tariff);

            abonentRepository.save(abonent);

            AbonentInputDTO savedAbonentDTO = modelMapper.map(abonent, AbonentInputDTO.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAbonentDTO);
        }
    }


    @Override
    public ResponseEntity<?> getAbonents() {
        List<Abonent> abonents = abonentRepository.findAll();
        List<AbonentInputDTO> abonentDTOs = abonents.stream()
                .map(abonent -> modelMapper.map(abonent, AbonentInputDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(abonentDTOs);
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
    public ResponseEntity<?> updateAbonent(AbonentInputDTO abonentDTO, long id) {
        Optional<Abonent> optionalAbonent = abonentRepository.findById(id);
        if (optionalAbonent.isPresent()) {
            Abonent existingAbonent = optionalAbonent.get();

            // Check if the existing abonent is active
            if (!existingAbonent.getIsActive()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Cannot update an inactive abonent.");
            }

            existingAbonent.setName(abonentDTO.getName());
            existingAbonent.setSurname(abonentDTO.getSurname());
            existingAbonent.setPatronymic(abonentDTO.getPatronymic());
            existingAbonent.setAddress(abonentDTO.getAddress());
            existingAbonent.setTelephone(abonentDTO.getTelephone());
            existingAbonent.setMeterNumber(abonentDTO.getMeterNumber());
            existingAbonent.setAbonentCode(abonentDTO.getAbonentCode());
            
            // Update region
            Region region = regionService.getRegionByName(abonentDTO.getRegionName());
            existingAbonent.setRegionID(region);

            // Update tariff
            Tariff tariff = tariffService.getTariffByName(abonentDTO.getTariffName());
            existingAbonent.setTariffID(tariff);
            
            abonentRepository.save(existingAbonent);
            return ResponseEntity.ok(existingAbonent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




    @Override
    public ResponseEntity<?> deleteAbonent(long Id) {
        try {
            Abonent abonent = abonentRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Abonent", "id", Id));

            if (abonent.getIsActive()) {
                // Delete associated entries from UserAbonent table
                userAbonentRepository.deleteByAbonentID(abonent);

                // Set isActive to false and save
                abonent.setIsActive(false);
                abonentRepository.save(abonent);

                return ResponseEntity.ok().build();
            } else {
                // Abonent is already inactive
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Abonent with id " + Id + " is already inactive.");
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @Override
    public ResponseEntity<?> getAbonentByAbonentCode(String abonentCode) {
        Abonent abonent = abonentRepository.findByAbonentCode(abonentCode);
        if (abonent == null || !abonent.getIsActive()) {
            return ResponseEntity.notFound().build();
        }

        List<Depts> deptHistory = deptsService.getDeptsByAbonentCode(abonentCode);
        
        // Sort deptHistory by date in descending order
        deptHistory.sort(Comparator.comparing(Depts::getDate).reversed());

        // Get the latest remaining dept
        Integer latestRemainingDept = deptHistory.isEmpty() ? null : deptHistory.get(0).getRemainingDept();

        AbonentOutputDTO abonentOutputDTO = modelMapper.map(abonent, AbonentOutputDTO.class);
        abonentOutputDTO.setLatestRemainingDept(latestRemainingDept);
        abonentOutputDTO.setRegionName(abonent.getRegionID().getRegionName());
        abonentOutputDTO.setTariffName(abonent.getTariffID().getTariffName());

        return ResponseEntity.ok(abonentOutputDTO);
    }


}
