package com.safetycar.repositories;

import com.safetycar.models.Role;
import com.safetycar.models.RoleId;
import com.safetycar.repositories.base.SafetyCarRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RoleRepository extends SafetyCarRepository<Role, RoleId> {

    @Modifying
    @Transactional
    @Query(value = "delete from Role where userName = :userName")
    void deleteAuthority(@Param(value = "userName") String userName);
}
