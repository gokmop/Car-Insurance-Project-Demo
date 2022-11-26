package com.safetycar.repositories;

import com.safetycar.models.Coefficient;
import com.safetycar.repositories.base.SafetyCarRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoefficientRepository extends SafetyCarRepository<Coefficient, Integer> {
}
