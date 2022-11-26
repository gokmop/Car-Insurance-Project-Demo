package com.safetycar.web.controllers.rest;

import com.safetycar.models.BaseAmount;
import com.safetycar.models.Coefficient;
import com.safetycar.services.contracts.BaseAmountService;
import com.safetycar.services.contracts.CoefficientService;
import com.safetycar.services.contracts.OfferService;
import com.safetycar.web.dto.offer.OfferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import static com.safetycar.util.Constants.BaseAmountConstants.CAPACITY;
import static com.safetycar.util.Constants.BaseAmountConstants.CAR_AGE;
import static com.safetycar.util.Helper.getCorrectCarAge;

@RestController
@RequestMapping("api/amount")
public class RestSimulationController {

    private final BaseAmountService baseAmountService;
    private final CoefficientService coefficientService;
    private final OfferService offerService;

    @Autowired
    public RestSimulationController(BaseAmountService baseAmountService,
                                    CoefficientService coefficientService,
                                    OfferService offerService) {
        this.baseAmountService = baseAmountService;
        this.coefficientService = coefficientService;
        this.offerService = offerService;
    }

    @PostMapping
    BigDecimal getAmount(@RequestBody @Valid OfferDto offerDto) {
        Coefficient coefficient = coefficientService.getCoefficient();
        boolean hadAccidents = offerDto.getHadAccidents().equals("true");
        boolean aboveTwentyFive = offerDto.getAboveTwentyFive().equals("true");
        Map<String, String> filter = new HashMap<>();
        int capacity = Integer.parseInt(offerDto.getCapacity());
        int carAge = getCorrectCarAge(offerDto.getDateRegistered());
        filter.put(CAPACITY, String.valueOf(capacity));
        filter.put(CAR_AGE, String.valueOf(carAge));
        BaseAmount baseAmount = baseAmountService.getBaseAmount(filter);
        BigDecimal result = offerService.calculatePremium(hadAccidents, aboveTwentyFive,
                coefficient, baseAmount.getBaseAmount());

        return result.setScale(2, RoundingMode.HALF_UP);
    }
}
