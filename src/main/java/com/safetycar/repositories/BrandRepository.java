package com.safetycar.repositories;

import com.safetycar.models.Brand;
import com.safetycar.repositories.base.SafetyCarRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends SafetyCarRepository<Brand, Integer> {
}
