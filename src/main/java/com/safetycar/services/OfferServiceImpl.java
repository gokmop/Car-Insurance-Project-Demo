package com.safetycar.services;


import com.safetycar.models.Coefficient;
import com.safetycar.models.Offer;
import com.safetycar.models.users.User;
import com.safetycar.repositories.OfferRepository;

import com.safetycar.repositories.filter.base.MapBasedSpecification;
import com.safetycar.services.contracts.OfferService;
import com.safetycar.services.contracts.base.GetServiceBase;
import com.safetycar.services.factories.OfferSpecFactory;
import com.safetycar.services.contracts.base.GetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.safetycar.util.Constants.Views.QueryConstants.ID;
import static com.safetycar.util.Constants.Views.QueryConstants.SORT_PARAMETER;

@Service
public class OfferServiceImpl extends GetServiceBase implements OfferService, GetService {

    private final OfferRepository offerRepository;
    private final OfferSpecFactory specService;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository,
                            OfferSpecFactory specService) {
        this.offerRepository = offerRepository;
        this.specService = specService;
    }

    @Override
    public List<Offer> getAll() {
        MapBasedSpecification<Offer> spec = specService.getOfferSpec();
        spec.addSpec(SORT_PARAMETER, ID);
        Sort sort = spec.getSort();
        return offerRepository.findAll(spec, sort);
    }

    @Override
    public Offer getOne(Integer id) {
        return get(offerRepository, id, Offer.class);
    }

    @Override
    public BigDecimal calculatePremium(boolean hadAccidents, boolean isAboveTwentyFive,
                                       Coefficient coefficient, BigDecimal baseAmount) {

        if (baseAmount == null) return BigDecimal.ZERO;

        BigDecimal ageRisk = BigDecimal.valueOf(coefficient.getAgeRisk());
        BigDecimal accidentRisk = BigDecimal.valueOf(coefficient.getAccidentRisk());

        BigDecimal taxCoeff = BigDecimal.valueOf(coefficient.getTax());
        BigDecimal ageCoeff = baseAmount.multiply(ageRisk);
        BigDecimal accidentCoeff = baseAmount.multiply(accidentRisk);


        if (hadAccidents) {
            baseAmount = baseAmount.add(accidentCoeff);
        }
        if (isAboveTwentyFive) {
            baseAmount = baseAmount.add(ageCoeff);
        }

        baseAmount = baseAmount.add(taxCoeff);
        return baseAmount;
    }

    @Override
    public Collection<Offer> getMyOffers(User user) {
        MapBasedSpecification<Offer> spec = specService.getOfferSpec(user.getOffers());
        return offerRepository.findAll(spec);
    }

    @Override
    public Collection<Offer> searchMyOffers(User user, Map<String, String> map) {
        MapBasedSpecification<Offer> spec = specService.getOfferSpec(user.getOffers(), map);
        Sort sort = spec.getSort();
        return offerRepository.findAll(spec, sort);
    }

    public void saveOfferFromRest(Offer offer){
        offerRepository.save(offer);
    }

}
