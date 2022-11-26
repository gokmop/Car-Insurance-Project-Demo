package com.safetycar.services.factories;

import com.safetycar.models.Offer;
import com.safetycar.repositories.filter.base.MapBasedSpecification;
import com.safetycar.repositories.filter.OfferSpec;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
public class OfferSpecFactoryImpl implements OfferSpecFactory {

    public MapBasedSpecification<Offer> getOfferSpec(Collection<Offer> collection) {
        return new OfferSpec(collection);
    }

    public MapBasedSpecification<Offer> getOfferSpec(Collection<Offer> collection, Map<String, String> map) {
        return new OfferSpec(collection, map);
    }

    public MapBasedSpecification<Offer> getOfferSpec() {
        return new OfferSpec();
    }
}
