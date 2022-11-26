package com.safetycar.services;

import com.safetycar.models.History;
import com.safetycar.models.Offer;
import com.safetycar.models.Policy;
import com.safetycar.models.users.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.safetycar.SafetyCarTestObjectsFactory.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class PolicyHistoryService {

    @InjectMocks
    private PolicyHistoryServiceImpl historyService;

    @Test
    public void appendHistory_ShouldAppend() {
        //arrange
        User testUser = getUser();
        Offer testOffer = getOffer();
        String testAction = "testAction";
        String testHistory = "testHistory";

        History actual = new History();
        actual.setActor(testUser.getUserDetails());
        actual.setHistory(testHistory);
        actual.setAction(testAction);
        Policy policy = getPolicy(testUser, testOffer);
        //act
        historyService.appendHistory(testUser, policy, testAction, testHistory);
        //assert
        Assertions.assertEquals(1, policy.getHistories().size());
        Assertions.assertEquals(actual, policy.getHistories().stream().findAny().get());

    }

}
