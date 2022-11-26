package com.safetycar.services;

import com.safetycar.models.Offer;
import com.safetycar.models.Policy;
import com.safetycar.models.users.User;
import com.safetycar.models.users.UserDetails;
import com.safetycar.services.contracts.AuthorisationService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


@Service
public class AuthorisationServiceImpl implements AuthorisationService {

    public static final String THIS_IS_NOT_YOUR_OFFER = "This is not your Offer!";
    public static final String THIS_IS_NOT_YOUR_POLICY = "This is not your Policy!";
    public static final String THIS_IS_NOT_YOUR_PROFILE = "This is not your profile!";

    @Override
    public void authorise(User user, Offer offer) {
        if (!user.isAdmin() && !user.getOffers().contains(offer)) {
            throw new AccessDeniedException(THIS_IS_NOT_YOUR_OFFER);
        }
    }

    @Override
    public void authorise(User user, Policy policy) {
        if (!user.isAdmin() && !user.getUserName().equals(policy.getOwner().getUserName())) {
            throw new AccessDeniedException(THIS_IS_NOT_YOUR_POLICY);
        }
    }

    @Override
    public void authorise(UserDetails details, User detailsOwnerOrAdmin) {
        boolean owner = details.getUserName().equals(detailsOwnerOrAdmin.getUserName());
        boolean admin = detailsOwnerOrAdmin.isAdmin();
        if (!admin && !owner) {
            throw new AccessDeniedException(THIS_IS_NOT_YOUR_PROFILE);
        }
    }
}
