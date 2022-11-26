package com.safetycar.services.factories;

import com.safetycar.models.Offer;
import com.safetycar.models.Policy;
import com.safetycar.models.users.User;
import com.safetycar.repositories.filter.base.MapBasedSpecification;
import com.safetycar.repositories.filter.PolicySpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static com.safetycar.SafetyCarTestObjectsFactory.*;
import static com.safetycar.util.Constants.Views.QueryConstants.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class PolicySpecFactoryTest {

    @InjectMocks
    private PolicySpecFactoryImpl specFactory;

    private Policy testPolicy;
    private Offer testOffer;
    private User testUser;
    private List<Policy> testPolicies;

    @BeforeEach
    void init() {
        testUser = getUser();
        testOffer = getOffer();
        testPolicy = getPolicy(testUser, testOffer);
        testPolicies = Collections.singletonList(testPolicy);
    }

    @Test
    public void getSpec_NoParameters_ShouldGetSpec() {
        //arrange
        MapBasedSpecification<Policy> expected = new PolicySpec();
        //act
        MapBasedSpecification<Policy> actual = specFactory.getPolicySpec();
        //assert
        Assertions.assertEquals(expected.getSort(), actual.getSort());
        Assertions.assertEquals(expected.getSpecs(), actual.getSpecs());
    }

    @Test
    public void getSpec_WithCollection_ShouldGetSpec() {
        //arrange
        testUser.addPolicy(testPolicy);
        final Collection<Policy> collection = testUser.getPolicies();
        MapBasedSpecification<Policy> expected = new PolicySpec(collection);
        //act
        MapBasedSpecification<Policy> actual = specFactory.getPolicySpec(collection);
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
        final Collection<Policy> offers = testUser.getPolicies();
        MapBasedSpecification<Policy> expected = new PolicySpec(offers, filter);
        //act
        MapBasedSpecification<Policy> actual = specFactory.getPolicySpec(offers, filter);
        //assert
        Assertions.assertEquals(expected.getSort(), actual.getSort());
        Assertions.assertEquals(expected.getSpecs(), actual.getSpecs());
    }

}
