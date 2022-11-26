package com.safetycar.repositories;

import com.safetycar.models.Model;
import com.safetycar.repositories.base.SafetyCarRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelRepository extends SafetyCarRepository<Model, Integer> {


    List<Model> findAllDistinctByBrand_IdOrderByNameAscYearAsc(int brandId);

}
