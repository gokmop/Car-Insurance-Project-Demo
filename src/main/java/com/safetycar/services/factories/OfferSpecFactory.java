package com.safetycar.services.factories;

import com.safetycar.models.Offer;
import com.safetycar.repositories.filter.base.MapBasedSpecification;
import com.safetycar.repositories.filter.OfferSpec;

import java.util.Collection;
import java.util.Map;

public interface OfferSpecFactory {

    MapBasedSpecification<Offer> getOfferSpec(Collection<Offer> collection);

    MapBasedSpecification<Offer> getOfferSpec(Collection<Offer> collection,
                                              Map<String, String> filter);

    MapBasedSpecification<Offer> getOfferSpec();

}
