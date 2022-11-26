package com.safetycar.repositories.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Base Repository for SafetyCar app
 *
 * @param <T>  Type of entity
 * @param <ID> field annotated with @Id from entity
 */
@NoRepositoryBean
public interface SafetyCarRepository<T, ID> extends JpaRepository<T, ID>,
        JpaSpecificationExecutor<T> {
}
