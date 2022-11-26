package com.safetycar.repositories;

import com.safetycar.models.Status;
import com.safetycar.repositories.base.SafetyCarRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends SafetyCarRepository<Status, Integer> {
}
