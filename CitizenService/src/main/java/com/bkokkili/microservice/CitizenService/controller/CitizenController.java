package com.bkokkili.microservice.CitizenService.controller;

import com.bkokkili.microservice.CitizenService.entity.Citizen;
import com.bkokkili.microservice.CitizenService.repositories.CitizenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citizen")
public class CitizenController {
    @Autowired
    private CitizenRepo citizenRepo;

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Citizen>> getByVaccinationId(@PathVariable int id){
        List<Citizen> citizenList = citizenRepo.findByVaccinationCenterId(id);
        return new ResponseEntity<>(citizenList, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Citizen> addCitizen(@RequestBody Citizen newCitizen){
        Citizen citizen = citizenRepo.save(newCitizen);
        return  new ResponseEntity<>(citizen,HttpStatus.OK);
    }
}
