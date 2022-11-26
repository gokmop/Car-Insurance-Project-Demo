package com.safetycar.services.contracts.base;

import com.safetycar.exceptions.EntityNotFoundException;
import com.safetycar.repositories.base.SafetyCarRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public class GetServiceBase implements GetService {

    protected GetServiceBase() {
    }

    private static final String ENTITY_WITH_ID_D_EXIST = "%s with id %s doesn't exist!";

    @Override
    public final <T, ID> T get(SafetyCarRepository<T, ID> repository, ID id, Class<T> entityClass) {
        Optional<T> optional = repository.findById(id);
        String message = String.format(ENTITY_WITH_ID_D_EXIST, entityClass.getSimpleName(), id);
        return optional.orElseThrow(() -> new EntityNotFoundException(message));
    }

    @Override
    public final <T, ID> T get(SafetyCarRepository<T, ID> repository, ID id, String message) {
        Optional<T> optional = repository.findById(id);
        return optional.orElseThrow(() -> new EntityNotFoundException(message));
    }
}
