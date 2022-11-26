package com.safetycar.repositories;

import com.safetycar.models.Offer;
import com.safetycar.models.users.User;

public interface OfferExists {

    boolean exists(Offer offer, User user);

}
