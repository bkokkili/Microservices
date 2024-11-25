package com.bkokkili.microservice.CitizenService.repositories;

import com.bkokkili.microservice.CitizenService.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitizenRepo  extends JpaRepository<Citizen, Integer> {

    public List<Citizen> findByVaccinationCenterId(int id);
}
