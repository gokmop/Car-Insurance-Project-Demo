package com.safetycar.services.mappers;

import com.safetycar.models.BaseAmount;
import com.safetycar.models.Car;
import com.safetycar.models.Coefficient;
import com.safetycar.models.Offer;
import com.safetycar.services.contracts.BaseAmountService;
import com.safetycar.services.contracts.CoefficientService;
import com.safetycar.services.contracts.OfferService;
import com.safetycar.web.dto.mappers.CarMapper;
import com.safetycar.web.dto.mappers.OfferMapper;
import com.safetycar.web.dto.offer.OfferDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.safetycar.SafetyCarTestObjectsFactory.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class OfferMapperTest {

    @InjectMocks
    private OfferMapper offerMapper;
    @Mock
    private OfferService offerService;
    @Mock
    private CoefficientService coefficientService;
    @Mock
    private BaseAmountService baseAmountService;
    @Mock
    private CarMapper carMapper;

    private OfferDto testDto;
    private Offer testOffer;
    private Car testCar;
    private BaseAmount testBaseAmount;
    private Coefficient testCoefficient;

    @BeforeEach
    void init() {
        testOffer = getOffer(false);
        testDto = getOfferDto(testOffer,
                testOffer.isAboveTwentyFive(),
                testOffer.hadAccidents());
        testCar = testOffer.getCar();
        testBaseAmount = getBaseAmount();
        testCoefficient = getCoefficient();

    }

    @Test
    public void fromDto_ShouldReturnOffer() {
        //arrange
        Mockito.when(carMapper.fromDto(testDto)).thenReturn(testCar);
        Mockito.when(baseAmountService.getBaseAmount(Mockito.anyMap()))
                .thenReturn(testBaseAmount);
        Mockito.when(coefficientService.getCoefficient())
                .thenReturn(testCoefficient);
        Mockito.when(offerService.calculatePremium(testOffer.hadAccidents(),
                testOffer.isAboveTwentyFive(),
                testCoefficient,
                testBaseAmount.getBaseAmount()))
                .thenReturn(testBaseAmount.getBaseAmount());
        //act
        Offer actual = offerMapper.fromDto(testDto);
        //assert
        Assertions.assertEquals(testOffer.getCar(), actual.getCar());
        Assertions.assertEquals(testOffer.getExpirationDate(), actual.getExpirationDate());
        Assertions.assertEquals(testOffer.getSubmissionDate(), actual.getSubmissionDate());
        Assertions.assertEquals(testOffer.getId(), actual.getId());
        Assertions.assertEquals(testOffer.getPremium(), actual.getPremium());
        Assertions.assertEquals(testOffer.isAboveTwentyFive(), actual.isAboveTwentyFive());
        Assertions.assertEquals(testOffer.hadAccidents(), actual.hadAccidents());
    }


}
