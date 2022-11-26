package com.safetycar.repositories;


import com.safetycar.models.VerificationToken;
import com.safetycar.models.users.User;
import com.safetycar.repositories.base.SafetyCarRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository
        extends SafetyCarRepository<VerificationToken,Integer> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

}
