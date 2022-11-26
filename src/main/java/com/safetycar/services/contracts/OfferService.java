package com.safetycar.services.contracts;

import com.safetycar.models.Coefficient;
import com.safetycar.models.Offer;
import com.safetycar.models.users.User;
import com.safetycar.services.contracts.base.GetAll;
import com.safetycar.services.contracts.base.GetOne;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

public interface OfferService extends GetOne<Offer, Integer>, GetAll<Offer> {

    BigDecimal calculatePremium(boolean hadAccidents, boolean isAboveTwentyFive,
                                Coefficient coefficient, BigDecimal baseAmount);

    Collection<Offer> getMyOffers(User user);

    Collection<Offer> searchMyOffers(User user, Map<String, String> map);

}
