package com.bkokkili.microservice.VaccinationCenter.controller;

import com.bkokkili.microservice.VaccinationCenter.entity.Citizen;
import com.bkokkili.microservice.VaccinationCenter.entity.Response;
import com.bkokkili.microservice.VaccinationCenter.entity.VaccinationCenter;
import com.bkokkili.microservice.VaccinationCenter.repositories.VaccinationCenterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/vaccination")
public class VaccinationCenterController {

    @Autowired
    private VaccinationCenterRepo centerRepo;

    @Autowired
    private  RestTemplate restTemplate;


    @PostMapping("/add")
    public ResponseEntity<VaccinationCenter> addCitizen(@RequestBody VaccinationCenter center){
        VaccinationCenter vaccinationCenter = centerRepo.save(center);
        return  new ResponseEntity<>(vaccinationCenter, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getAllDetailsBasedOnCenterId(@PathVariable String id){
        Response response = new Response();
        VaccinationCenter center =  centerRepo.findById(id).get();
        response.setCenter(center);

        List<Citizen> citizens = restTemplate.getForObject("http://CITIZEN-SERVICE/citizen/"+id , List.class);
        response.setCitizens(citizens);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
