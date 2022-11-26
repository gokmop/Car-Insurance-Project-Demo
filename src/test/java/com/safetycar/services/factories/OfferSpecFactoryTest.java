package com.safetycar.services.factories;

import com.safetycar.models.Offer;
import com.safetycar.models.users.User;
import com.safetycar.repositories.filter.base.MapBasedSpecification;
import com.safetycar.repositories.filter.OfferSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.safetycar.SafetyCarTestObjectsFactory.getOffer;
import static com.safetycar.SafetyCarTestObjectsFactory.getUser;
import static com.safetycar.util.Constants.Views.QueryConstants.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class OfferSpecFactoryTest {

    @InjectMocks
    private OfferSpecFactoryImpl specFactory;
    private User testUser;

    @BeforeEach
    void init() {
        testUser = getUser();
    }

    @Test
    public void getSpec_NoParameters_ShouldGetSpec() {
        //arrange
        MapBasedSpecification<Offer> expected = new OfferSpec();
        //act
        MapBasedSpecification<Offer> actual = specFactory.getOfferSpec();
        //assert
        Assertions.assertEquals(expected.getSort(), actual.getSort());
        Assertions.assertEquals(expected.getSpecs(), actual.getSpecs());
    }

    @Test
    public void getSpec_WithCollection_ShouldGetSpec() {
        //arrange
        Offer offer = getOffer();
        testUser.addOffer(offer);
        final Collection<Offer> collection = testUser.getOffers();
        MapBasedSpecification<Offer> expected = new OfferSpec(collection);
        //act
        MapBasedSpecification<Offer> actual = specFactory.getOfferSpec(collection);
        //assert
        Assertions.assertEquals(expected.getSort(), actual.getSort());
        Assertions.assertEquals(expected.getSpecs(), actual.getSpecs());
    }

    @Test
    public void getSpec_WithCollectionAndMap_ShouldGetSpec() {
        //arrange
        Map<String, String> filter = new HashMap<>() {
            {
                put(SORT_PARAMETER, ID);
                put(SUBMISSION_DATE, LocalDate.now().toString());
            }
        };
        Offer offer = getOffer();
        testUser.addOffer(offer);
        final Collection<Offer> offers = testUser.getOffers();
        MapBasedSpecification<Offer> expected = new OfferSpec(offers, filter);
        //act
        MapBasedSpecification<Offer> actual = specFactory.getOfferSpec(offers, filter);
        //assert
        Assertions.assertEquals(expected.getSort(), actual.getSort());
        Assertions.assertEquals(expected.getSpecs(), actual.getSpecs());
    }

}
