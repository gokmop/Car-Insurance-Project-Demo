package com.safetycar.web.dto.mappers;

import com.safetycar.models.*;
import com.safetycar.services.contracts.*;
import com.safetycar.web.dto.offer.CalculateOfferDtoForRest;
import com.safetycar.web.dto.offer.CreateOfferDtoForRest;
import com.safetycar.web.dto.offer.OfferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.safetycar.util.Constants.BaseAmountConstants.CAPACITY;
import static com.safetycar.util.Constants.BaseAmountConstants.CAR_AGE;
import static com.safetycar.util.Helper.getCorrectCarAge;

@Component
public class OfferMapper {

    private final OfferService offerService;
    private final CoefficientService coefficientService;
    private final BaseAmountService baseAmountService;
    private final CarMapper carMapper;

    @Autowired
    public OfferMapper(OfferService offerService,
                       CoefficientService coefficientService,
                       BaseAmountService baseAmountService, CarMapper carMapper) {
        this.offerService = offerService;
        this.coefficientService = coefficientService;
        this.baseAmountService = baseAmountService;
        this.carMapper = carMapper;
    }

    public Offer fromDto(OfferDto dto) {

        boolean aboveTwentyFive = Boolean.parseBoolean(dto.getAboveTwentyFive());
        boolean hadAccidents = Boolean.parseBoolean(dto.getHadAccidents());

        Car car = carMapper.fromDto(dto);

        int correctCarAge = getCorrectCarAge(car.getFirstRegistration());

        BaseAmount baseAmount = getBaseAmount(car.getCapacity(), correctCarAge);
        BigDecimal premium = getPremium(aboveTwentyFive, hadAccidents, baseAmount);

        Offer offer = new Offer();
        offer.setAboveTwentyFive(aboveTwentyFive);
        offer.setHadAccidents(hadAccidents);
        offer.setCar(car);
        offer.setPremium(premium);

        return offer;
    }

    public Offer fromCalculateOfferDtoForRest(CalculateOfferDtoForRest dto){
        boolean aboveTwentyFive = Boolean.parseBoolean(dto.getAboveTwentyFive());
        boolean hadAccidents = Boolean.parseBoolean(dto.getHadAccidents());
        Car car = carMapper.fromCalculateOfferDto(dto);
        int correctCarAge = getCorrectCarAge(car.getFirstRegistration());

        BaseAmount baseAmount = getBaseAmount(car.getCapacity(), correctCarAge);
        BigDecimal premium = getPremium(aboveTwentyFive, hadAccidents, baseAmount);

        Offer offer = new Offer();
        offer.setAboveTwentyFive(aboveTwentyFive);
        offer.setHadAccidents(hadAccidents);
        offer.setCar(car);
        offer.setPremium(premium);

        return offer;

    }

    public Offer fromDtoCreateOfferRest(CreateOfferDtoForRest dto){
        boolean aboveTwentyFive = Boolean.parseBoolean(dto.getAboveTwentyFive());
        boolean hadAccidents = Boolean.parseBoolean(dto.getHadAccidents());
        Car car = carMapper.fromDto(dto);
        int correctCarAge = getCorrectCarAge(car.getFirstRegistration());

        BaseAmount baseAmount = getBaseAmount(car.getCapacity(), correctCarAge);
        BigDecimal premium = getPremium(aboveTwentyFive, hadAccidents, baseAmount);

        Offer offer = new Offer();
        offer.setAboveTwentyFive(aboveTwentyFive);
        offer.setHadAccidents(hadAccidents);
        offer.setCar(car);
        offer.setPremium(premium);

        return offer;
    }

    private BigDecimal getPremium(boolean aboveTwentyFive, boolean hadAccidents, BaseAmount baseAmount) {
        Coefficient coefficient = coefficientService.getCoefficient();
        return offerService.calculatePremium(hadAccidents, aboveTwentyFive,
                coefficient, baseAmount.getBaseAmount());
    }

    private BaseAmount getBaseAmount(int capacity, int carAge) {
        Map<String, String> filter = new HashMap<>();
        filter.put(CAPACITY, String.valueOf(capacity));
        filter.put(CAR_AGE, String.valueOf(carAge));
        return baseAmountService.getBaseAmount(filter);
    }
}
