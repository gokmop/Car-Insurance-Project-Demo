package com.safetycar.services.contracts.base;

import com.safetycar.exceptions.EntityNotFoundException;
import com.safetycar.repositories.base.SafetyCarRepository;
import org.springframework.data.jpa.domain.Specification;

public interface FindService {

    /**
     * @param repository repository implementing JpaSpecificationExecutor<T> interface
     * @param spec       custom MapBasedSpecification<T> extending Specification<T>
     * @param message    custom message
     * @param <T>        type of return entity
     * @return returns an entity found by @spec criteria
     * or throws EntityNotFoundException with @message
     */
    <T, ID> T find(SafetyCarRepository<T,ID> repository,
               Specification<T> spec,
               String message) throws EntityNotFoundException;
}
