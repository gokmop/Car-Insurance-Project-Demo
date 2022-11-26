package com.safetycar.repositories;

import com.safetycar.models.Car;
import com.safetycar.models.Model;
import com.safetycar.repositories.base.SafetyCarRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;

public interface CarRepository extends SafetyCarRepository<Car, Integer> {
}
