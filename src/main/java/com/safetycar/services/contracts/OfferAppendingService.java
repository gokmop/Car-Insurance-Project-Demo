package com.safetycar.services.contracts;

import com.safetycar.models.Offer;
import com.safetycar.models.users.User;

public interface OfferAppendingService {

    void appendOffer(Offer offer, User user);

}
