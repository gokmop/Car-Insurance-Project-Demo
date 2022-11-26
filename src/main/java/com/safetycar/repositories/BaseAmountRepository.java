package com.safetycar.repositories;

import com.safetycar.models.BaseAmount;
import com.safetycar.repositories.base.SafetyCarRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BaseAmountRepository extends SafetyCarRepository<BaseAmount, Integer> {

}
