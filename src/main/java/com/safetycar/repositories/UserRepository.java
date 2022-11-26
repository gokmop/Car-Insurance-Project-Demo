package com.safetycar.repositories;

import com.safetycar.models.users.User;
import com.safetycar.models.users.UserDetails;
import com.safetycar.repositories.base.SafetyCarRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends SafetyCarRepository<User, String>, UserExists {

    boolean existsByUserDetails(UserDetails userDetails);

    boolean existsByUserName(String username);

}
