package com.safetycar.services.contracts;

import com.safetycar.models.Offer;
import com.safetycar.models.Policy;
import com.safetycar.models.users.User;
import com.safetycar.models.users.UserDetails;

public interface AuthorisationService {

    void authorise(User user, Offer offer);

    void authorise(User user, Policy policy);

    void authorise(UserDetails details, User detailsOwnerOrAdmin);

}
