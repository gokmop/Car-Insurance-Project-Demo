package com.safetycar.repositories;

import com.safetycar.models.users.UserDetails;
import com.safetycar.repositories.base.SafetyCarRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UserDetailsRepository extends SafetyCarRepository<UserDetails, Integer> {

    @Override
    default Optional<UserDetails> findById(Integer id) {
        return Optional.ofNullable(findByIntegerId(id));
    }

    UserDetails findByIntegerId(Integer id);

    default List<UserDetails> findAll() {
        return findAllByActiveTrue();
    }

    List<UserDetails> findAllByActiveTrue();
}
