package com.safetycar.services;

import com.safetycar.exceptions.EntityNotFoundException;
import com.safetycar.models.Offer;
import com.safetycar.models.Policy;
import com.safetycar.models.users.User;
import com.safetycar.repositories.PolicyRepository;
import com.safetycar.repositories.filter.OfferSpec;
import com.safetycar.repositories.filter.PolicySpec;
import com.safetycar.services.factories.PolicySpecFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.*;

import static com.safetycar.SafetyCarTestObjectsFactory.*;
import static com.safetycar.util.Constants.Views.QueryConstants.ID;
import static com.safetycar.util.Constants.Views.QueryConstants.SORT_PARAMETER;

@ExtendWith(MockitoExtension.class)
public class PolicyServiceTest {

    @InjectMocks
    private PolicyServiceImpl policyService;

    @Mock
    private PolicyRepository policyRepository;

    @Mock
    private PolicySpecFactory specFactory;

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
    public void create_ShouldCreate() {
        //arrange, act
        policyService.create(testPolicy);
        //assert
        Mockito.verify(policyRepository, Mockito.times(1)).save(testPolicy);
    }

    @Test
    public void update_ShouldCreate() {
        //arrange, act
        policyService.update(testPolicy);
        //assert
        Mockito.verify(policyRepository, Mockito.times(1)).save(testPolicy);
    }

    @Test
    public void get_ShouldReturn_WhenEntityExists() {
        //arrange
        Mockito.when(policyRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(testPolicy));
        //act
        Policy actual = policyService.getOne(Mockito.anyInt());
        //assert
        Assertions.assertEquals(actual, testPolicy);
    }

    @Test
    public void get_ShouldThrow_WhenEntityDoesNotExist() {
        //arrange
        Mockito.when(policyRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());
        //act, assert
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> policyService.getOne(Mockito.anyInt()));
    }

    @Test
    public void getAll_ShouldReturnCollection() {
        //arrange
        PolicySpec spec = new PolicySpec(new HashMap<>());
        spec.addSpec(SORT_PARAMETER, ID);
        Sort sort = spec.getSort();

        Mockito.when(specFactory.getPolicySpec()).thenReturn(spec);
        Mockito.when(policyRepository.findAll(spec, sort))
                .thenReturn(testPolicies);
        //act
        Collection<Policy> actual = policyService.getAll();
        //assert
        Assertions.assertEquals(testPolicies, actual);
    }
    @Test
    public void searchMyOffers_ShouldReturnCollection() {
        //arrange
        testUser.addPolicy(testPolicy);
        Map<String, String> filter = new HashMap<>();
        PolicySpec spec = new PolicySpec(testUser.getPolicies(), filter);
        Sort sort = spec.getSort();
        Mockito.when(specFactory.getPolicySpec(testUser.getPolicies(), filter)).thenReturn(spec);
        Mockito.when(policyRepository.findAll(spec, sort))
                .thenReturn(testPolicies);
        //act
        List<Policy> actual = policyService.searchMyPolicies(testUser.getPolicies(), filter);
        //assert
        Assertions.assertEquals(testPolicies, actual);
    }
}
