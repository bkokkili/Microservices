package com.bkokkili.microservice.VaccinationCenter.repositories;

import com.bkokkili.microservice.VaccinationCenter.entity.VaccinationCenter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VaccinationCenterRepo extends JpaRepository<VaccinationCenter, String> {

}
