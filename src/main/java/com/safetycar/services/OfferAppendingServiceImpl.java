package com.safetycar.services;

import com.safetycar.exceptions.DuplicateEntityException;
import com.safetycar.models.Offer;
import com.safetycar.models.users.User;
import com.safetycar.repositories.OfferRepository;
import com.safetycar.services.contracts.OfferAppendingService;
import com.safetycar.services.contracts.OfferService;
import com.safetycar.exceptions.TooManyOffersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferAppendingServiceImpl implements OfferAppendingService {

    public static final int MAXIMUM_OFFERS_ALLOWED = 5;
    public static final String OFFER_FOR_THIS_CAR_ALREADY_EXISTS = "An offer for this car already exists!";

    private final OfferRepository offerRepository;

    @Autowired
    public OfferAppendingServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public void appendOffer(Offer offer, User user) {
        if (user.getOffers().size() == MAXIMUM_OFFERS_ALLOWED) {
            throw new TooManyOffersException();
        }
        if (offerRepository.exists(offer, user)) {
            throw new DuplicateEntityException(OFFER_FOR_THIS_CAR_ALREADY_EXISTS);
        }
        if (offer != null && !user.isAdmin()) {
            user.addOffer(offer);
        }
    }
}
