package com.safetycar.services;

import com.safetycar.exceptions.ExpiredException;
import com.safetycar.models.Coefficient;
import com.safetycar.models.Offer;
import com.safetycar.models.users.User;
import com.safetycar.repositories.OfferRepository;
import com.safetycar.exceptions.EntityNotFoundException;
import com.safetycar.repositories.filter.OfferSpec;
import com.safetycar.services.factories.OfferSpecFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.*;

import static com.safetycar.SafetyCarTestObjectsFactory.*;
import static com.safetycar.util.Constants.Views.QueryConstants.ID;
import static com.safetycar.util.Constants.Views.QueryConstants.SORT_PARAMETER;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @InjectMocks
    private OfferServiceImpl offerService;

    @Mock
    private OfferRepository mockOfferRepository;

    @Mock
    private OfferSpecFactory mockOfferSpecFactory;

    private Offer testOffer;
    private Coefficient testCoeff;
    private User testUser;
    private List<Offer> testOffers;

    @BeforeEach
    public void init() {
        testOffer = getOffer();
        testOffers = Collections.singletonList(testOffer);
        testCoeff = getCoefficient();
        testUser = getUser();
    }

    @Test
    public void get_ShouldReturn_WhenEntityExists() {
        //arrange
        Mockito.when(mockOfferRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(testOffer));
        //act
        Offer actual = offerService.getOne(Mockito.anyInt());
        //assert
        Assertions.assertEquals(actual, testOffer);
    }

    @Test
    public void getAll_ShouldReturnCollection() {
        //arrange
        OfferSpec spec = new OfferSpec(new HashMap<>());
        spec.addSpec(SORT_PARAMETER,ID);
        Sort sort = spec.getSort();

        Mockito.when(mockOfferSpecFactory.getOfferSpec()).thenReturn(spec);
        Mockito.when(mockOfferRepository.findAll(spec,sort))
                .thenReturn(testOffers);
        //act
        Collection<Offer> actual = offerService.getAll();
        //assert
        Assertions.assertEquals(testOffers, actual);
    }

    @Test
    public void getMyOffers_ShouldReturnCollection() {
        //arrange
        testUser.addOffer(testOffer);
        Collection<Offer> offers = testUser.getOffers();
        OfferSpec spec = new OfferSpec(offers);
        Mockito.when(mockOfferSpecFactory.getOfferSpec(offers)).thenReturn(spec);
        Mockito.when(mockOfferRepository.findAll(spec))
                .thenReturn(testOffers);
        //act
        List<Offer> actual = new LinkedList<>(offerService.getMyOffers(testUser));
        //assert
        Assertions.assertEquals(testOffers, actual);
    }

    @Test
    public void searchMyOffers_ShouldReturnCollection()  {
        //arrange
        testUser.addOffer(testOffer);
        Map<String, String> filter = new HashMap<>();
        OfferSpec spec = new OfferSpec(testUser.getOffers(), filter);
        Sort sort = spec.getSort();
        Mockito.when(mockOfferSpecFactory.getOfferSpec(testUser.getOffers(), filter)).thenReturn(spec);
        Mockito.when(mockOfferRepository.findAll(spec, sort))
                .thenReturn(testOffers);
        //act
        List<Offer> actual = new LinkedList<>(offerService.searchMyOffers(testUser, filter));
        //assert
        Assertions.assertEquals(testOffers, actual);
    }


    @Test
    public void get_ShouldThrow_WhenEntityDoesNotExist() {
        //arrange
        Mockito.when(mockOfferRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());
        //act, assert
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> offerService.getOne(Mockito.anyInt()));
    }

    @Test
    public void calculatePremium_ShouldCalculateCorrectly() {
        //arrange
        BigDecimal baseAmount = BigDecimal.TEN;
        BigDecimal samePremium = BigDecimal.TEN;

        Offer allTrue = getOffer(true);
        allTrue.setPremium(samePremium);

        Offer allFalse = getOffer(false);
        allFalse.setPremium(samePremium);

        Offer noAccidentsAboveTwentyFive = getOffer();
        noAccidentsAboveTwentyFive.setPremium(samePremium);

        Offer accidentsBelowTwentyFive = getOffer(true);
        accidentsBelowTwentyFive.setPremium(samePremium);
        accidentsBelowTwentyFive.setAboveTwentyFive(false);
        //act
        BigDecimal allTruePremiumActual = actual(baseAmount, testCoeff, allTrue);
        BigDecimal allFalsePremiumActual = actual(baseAmount, testCoeff, allFalse);
        BigDecimal noAccidentsAboveTwentyFivePremiumActual = actual(baseAmount, testCoeff, noAccidentsAboveTwentyFive);
        BigDecimal accidentsBelowTwentyFivePremiumActual = actual(baseAmount, testCoeff, accidentsBelowTwentyFive);

        BigDecimal allTruePremiumExpected = expected(baseAmount, testCoeff, allTrue);
        BigDecimal allFalsePremiumExpected = expected(baseAmount, testCoeff, allFalse);
        BigDecimal noAccidentsAboveTwentyFivePremiumExpected = expected(baseAmount, testCoeff, noAccidentsAboveTwentyFive);
        BigDecimal accidentsBelowTwentyFivePremiumExpected = expected(baseAmount, testCoeff, accidentsBelowTwentyFive);
        //assert

        Assertions.assertEquals(allTruePremiumExpected, allTruePremiumActual);
        Assertions.assertEquals(allFalsePremiumExpected, allFalsePremiumActual);
        Assertions.assertEquals(noAccidentsAboveTwentyFivePremiumExpected, noAccidentsAboveTwentyFivePremiumActual);
        Assertions.assertEquals(accidentsBelowTwentyFivePremiumExpected, accidentsBelowTwentyFivePremiumActual);

    }

    private BigDecimal actual(BigDecimal baseAmount, Coefficient testCoeff, Offer offer) {
        return offerService.calculatePremium(offer.hadAccidents(),
                offer.isAboveTwentyFive(), testCoeff, baseAmount);
    }

    private BigDecimal expected(BigDecimal baseAmount, Coefficient testCoeff, Offer offer) {
        if (baseAmount == null) return BigDecimal.ZERO;

        BigDecimal ageRisk = BigDecimal.valueOf(testCoeff.getAgeRisk());
        BigDecimal accidentRisk = BigDecimal.valueOf(testCoeff.getAccidentRisk());

        BigDecimal taxCoeff = BigDecimal.valueOf(testCoeff.getTax());
        BigDecimal ageCoeff = baseAmount.multiply(ageRisk);
        BigDecimal accidentCoeff = baseAmount.multiply(accidentRisk);


        if (offer.hadAccidents()) {
            baseAmount = baseAmount.add(accidentCoeff);
        }
        if (offer.isAboveTwentyFive()) {
            baseAmount = baseAmount.add(ageCoeff);
        }

        baseAmount = baseAmount.add(taxCoeff);
        return baseAmount;
    }

}
