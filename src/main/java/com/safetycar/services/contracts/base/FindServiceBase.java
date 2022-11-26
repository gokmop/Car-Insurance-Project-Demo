package com.safetycar.services.contracts.base;

import com.safetycar.exceptions.EntityNotFoundException;
import com.safetycar.repositories.base.SafetyCarRepository;
import com.safetycar.repositories.filter.base.MapBasedSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public class FindServiceBase implements FindService {

    protected FindServiceBase() {
    }

    @Override
    public final <T, ID> T find(SafetyCarRepository<T, ID> repository, Specification<T> spec, String message) {
        Optional<T> optional = repository.findOne(spec);
        return optional.orElseThrow(() -> new EntityNotFoundException(message));
    }

}
