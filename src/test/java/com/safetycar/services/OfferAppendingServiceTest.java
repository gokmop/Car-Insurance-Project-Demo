package com.safetycar.services;

import com.safetycar.exceptions.DuplicateEntityException;
import com.safetycar.exceptions.TooManyOffersException;
import com.safetycar.models.Car;
import com.safetycar.models.Offer;
import com.safetycar.models.users.User;
import com.safetycar.repositories.OfferRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static com.safetycar.SafetyCarTestObjectsFactory.*;
import static com.safetycar.services.OfferAppendingServiceImpl.MAXIMUM_OFFERS_ALLOWED;

@ExtendWith(MockitoExtension.class)
public class OfferAppendingServiceTest {

    @InjectMocks
    private OfferAppendingServiceImpl offerAppendingService;

    @Mock
    private OfferRepository mockOfferRepository;

    private User testUser;
    private Offer testOffer;

    @BeforeEach
    void init() {
        testUser = getUser();
        testOffer = getOffer();
    }

    @Test
    public void append_ShouldThrow_WhenExceedingMaximumOffers() {
        //arrange
        for (int i = 0; i < MAXIMUM_OFFERS_ALLOWED; i++) {
            Offer offer = getOffer();
            offer.setPremium(BigDecimal.valueOf(Integer.MAX_VALUE + i));
            testUser.addOffer(offer);
        }
        //act
        // assert
        Assertions.assertThrows(TooManyOffersException.class,
                () -> offerAppendingService.appendOffer(testOffer, testUser));
    }

    @Test
    public void append_ShouldNotAppend_WhenOfferNull() {
        //arrange
        Offer offer = null;
        //act
        offerAppendingService.appendOffer(offer, testUser);
        //assert
        int expected = 0;
        int actual = testUser.getOffers().size();
        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void append_ShouldThrow_WhenDuplicate() {
        //arrange
        Mockito.when(mockOfferRepository.exists(testOffer, testUser)).thenReturn(true);
        //act, assert
        Assertions.assertThrows(DuplicateEntityException.class, () ->
                offerAppendingService.appendOffer(testOffer, testUser));
    }

    @Test
    public void append_ShouldAppend_WhenAllIsWell() {
        //arrange, act
        offerAppendingService.appendOffer(testOffer, testUser);
        //assert
        int expected = 1;
        int actual = testUser.getOffers().size();
        Assertions.assertEquals(expected, actual);
    }

}

