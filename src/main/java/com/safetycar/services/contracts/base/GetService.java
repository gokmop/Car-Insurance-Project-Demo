package com.safetycar.services.contracts.base;

import com.safetycar.exceptions.EntityNotFoundException;
import com.safetycar.repositories.base.SafetyCarRepository;
import org.springframework.data.repository.CrudRepository;

public interface GetService {

    /**
     * @param repository  SafetyCarRepository<T,ID>
     * @param id          ID entity attribute mapped with @Id
     * @param entityClass Class<T>
     * @param <T>         type of Entity
     * @return an entity of type T with id @id
     * throws EntityNotFoundException if id doesn't exist
     */
    <T, ID> T get(SafetyCarRepository<T, ID> repository, ID id, Class<T> entityClass) throws EntityNotFoundException;

    /**
     * @param repository SafetyCarRepository<T,ID>
     * @param id         ID entity attribute mapped with @Id
     * @param message    String
     * @param <T>        type of Entity
     * @return an entity of type T with id @id
     * throws EntityNotFoundException with @message
     */
    <T, ID> T get(SafetyCarRepository<T, ID> repository, ID id, String message) throws EntityNotFoundException;

}
