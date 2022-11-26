package com.safetycar.repositories;

import com.safetycar.models.Offer;
import com.safetycar.models.users.User;
import com.safetycar.repositories.base.SafetyCarRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OfferRepository extends SafetyCarRepository<Offer,
        Integer>, OfferExists {

    @Override
    boolean exists(Offer offer, User user);

}
