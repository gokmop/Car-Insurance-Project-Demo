package com.safetycar.services;

import com.safetycar.models.Offer;
import com.safetycar.models.Policy;
import com.safetycar.models.users.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import static com.safetycar.SafetyCarTestObjectsFactory.*;
import static com.safetycar.util.Helper.getAdminRoles;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class AuthorisationServiceTest {

    @InjectMocks
    private AuthorisationServiceImpl authorisationService;
    private User testUser;
    private User testAgent;
    private Policy testPolicy;
    private Offer testOffer;

    @BeforeEach
    void init() {
        testAgent = getUser();
        testAgent.setUsername("agent");
        testAgent.getUserDetails().setUserName("agent");
        testAgent.setRoles(getAdminRoles(testAgent));
        testUser = getUser();
        testOffer = getOffer();
        testUser.addOffer(testOffer);
        testPolicy = getPolicy(testUser, testOffer);
        testUser.addPolicy(testPolicy);
    }

    @Test
    public void authorise_ShouldThrow_WhenNotAdminAndNotOfferOwner() {
        //arrange
        User actual = getUser("actual");
        //act, assert
        Assertions.assertThrows(AccessDeniedException.class,
                () -> authorisationService.authorise(actual, testOffer));
    }

    @Test
    public void authorise_ShouldNotThrow_WhenNotAdminButOfferOwner() {
        //arrange
        User actual = getUser("actual");
        //act, assert
        Assertions.assertDoesNotThrow(
                () -> authorisationService.authorise(testUser, testOffer));
    }

    @Test
    public void authorise_ShouldNotThrow_WhenAdminButNotOfferOwner() {
        //arrange, act, assert
        Assertions.assertDoesNotThrow(
                () -> authorisationService.authorise(testAgent, testOffer));
    }

    @Test
    public void authorise_ShouldThrow_WhenNotAdminAndNotPolicyOwner() {
        //arrange
        User actual = getUser("actual");
        //act, assert
        Assertions.assertThrows(AccessDeniedException.class,
                () -> authorisationService.authorise(actual, testPolicy));
    }
    @Test
    public void authorise_ShouldNotThrow_WhenAdminButNotPolicyOwner() {
        //arrange, act, assert
        Assertions.assertDoesNotThrow(
                () -> authorisationService.authorise(testAgent, testPolicy));
    }

    @Test
    public void authorise_ShouldNotThrow_WhenNotAdminButPolicyOwner() {
        //arrange, act, assert
        Assertions.assertDoesNotThrow(
                () -> authorisationService.authorise(testUser, testPolicy));
    }

    @Test
    public void authorise_ShouldNotThrow_WhenAdmin() {
        //arrange, act, assert
        Assertions.assertDoesNotThrow(
                () -> authorisationService.authorise(testUser.getUserDetails(), testAgent));
    }
    @Test
    public void authorise_ShouldThrow_WhenNotAdmin() {
        //arrange
        User actual = getUser();
        actual.setUsername("actual");
        //act, assert
        Assertions.assertThrows(AccessDeniedException.class,
                () -> authorisationService.authorise(testUser.getUserDetails(), actual));
    }

}
