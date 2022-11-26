package com.safetycar.services.managers;

import com.safetycar.models.History;
import com.safetycar.models.Offer;
import com.safetycar.models.Policy;
import com.safetycar.models.Status;
import com.safetycar.models.users.User;
import com.safetycar.services.PolicyManagerImpl;
import com.safetycar.services.contracts.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.safetycar.SafetyCarTestObjectsFactory.*;
import static com.safetycar.services.PolicyManagerImpl.*;
import static com.safetycar.web.controllers.mvc.MvcPolicyController.PENDING;

@ExtendWith(MockitoExtension.class)
public class PolicyManagerTest {

    @InjectMocks
    private PolicyManagerImpl manager;
    @Mock
    private PolicyService mockPolicyService;
    @Mock
    private UserService mockUserService;
    @Mock
    private PolicyHistoryService mockPolicyHistoryService;
    @Mock
    private MailService mockMailService;
    @Mock
    private AuthorisationService mockAuthorisationService;
    @Mock
    private StatusService mockStatusService;

    private Policy testPolicy;
    private User testUser;
    private Offer testOffer;
    private History testHistory;

    @BeforeEach
    void init() {
        testUser = getUser();
        testOffer = getOffer();
        testPolicy = getPolicy(testUser, testOffer);
        testHistory = getHistory(testUser);
    }

    @Test
    public void adminUpdatePolicy_ShouldUpdatePolicy() {
        //arrange
        Status status = new Status();
        status.setStatus("status");
        String action = "Changed status to " + status.getStatus();
        String message = "message";
        //act
        manager.adminUpdatePolicy(testPolicy, status, testUser, message);
        //assert
        Mockito.verify(mockPolicyHistoryService, Mockito.times(1)).appendHistory(testUser, testPolicy, action, message);
        Mockito.verify(mockMailService, Mockito.times(1)).sendPolicyStatusMail(testPolicy);
        Mockito.verify(mockPolicyService, Mockito.times(1)).update(testPolicy);
        Mockito.verify(mockAuthorisationService, Mockito.times(1)).authorise(testUser, testPolicy);
    }

    @Test
    public void createPolicy_ShouldCreatePolicy() {
        //arrange
        String message = CREATED_BY + testUser.getUserDetails().getFullName();
        //act
        manager.createPolicy(testPolicy, testOffer, testUser);
        //assert
        Mockito.verify(mockPolicyHistoryService, Mockito.times(1)).appendHistory(testUser, testPolicy, CREATED_POLICY, message);
        Mockito.verify(mockPolicyService, Mockito.times(1)).create(testPolicy);
        Mockito.verify(mockUserService, Mockito.times(1)).update(testUser);
        Assertions.assertFalse(testUser.getOffers().contains(testOffer));
        Assertions.assertTrue(testUser.getPolicies().contains(testPolicy));
    }

    @Test
    public void userUpdatePolicy_ShouldUpdatePolicy() {
        //arrange
        Mockito.when(mockStatusService.getOne(Mockito.anyInt())).thenReturn(testPolicy.getStatus());
        String message = "Picture updated by" + testUser.getUserDetails().getFullName();
        //act
        manager.userUpdatePolicy(testPolicy, testUser);
        //assert
        Mockito.verify(mockPolicyHistoryService, Mockito.times(1)).appendHistory(testUser, testPolicy, UPDATED_PICTURE, message);
        Mockito.verify(mockPolicyService, Mockito.times(1)).update(testPolicy);
        Assertions.assertEquals(PENDING,testPolicy.getStatus().getStatus());

    }


}
