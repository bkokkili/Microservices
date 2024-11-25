package com.bkokkili.microservice.VaccinationCenter.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private  VaccinationCenter center;
    private List<Citizen> citizens;
}
