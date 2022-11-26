package com.safetycar.repositories;

import com.safetycar.models.Policy;
import com.safetycar.repositories.base.SafetyCarRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PolicyRepository extends SafetyCarRepository<Policy, Integer>{

}
