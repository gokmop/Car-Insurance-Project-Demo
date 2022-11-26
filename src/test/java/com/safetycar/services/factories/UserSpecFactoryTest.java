package com.safetycar.services.factories;

import com.safetycar.models.users.User;
import com.safetycar.models.users.UserDetails;
import com.safetycar.repositories.filter.base.MapBasedSpecification;
import com.safetycar.repositories.filter.UserSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static com.safetycar.SafetyCarTestObjectsFactory.getUser;
import static com.safetycar.repositories.filter.UserSpec.*;
import static com.safetycar.util.Constants.Views.QueryConstants.SORT_PARAMETER;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class UserSpecFactoryTest {

    @InjectMocks
    private UserSpecFactoryImpl specFactory;
    private User testUser;

    @BeforeEach
    void init() {
        testUser = getUser();
    }

    @Test
    public void getNoParameters_ShouldGet() {
        //arrange
        UserSpec expected = new UserSpec();
        //act
        MapBasedSpecification<User> actual = specFactory.getUserSpec();
        //assert
        Assertions.assertEquals(expected.getSort(), actual.getSort());
        Assertions.assertEquals(expected.getSpecs(), actual.getSpecs());
    }

    @Test
    public void longTestNameExpected() {
        //arrange
        Map<String, String> filter = new HashMap<>() {
            {
                put(SORT_PARAMETER, ID);
                put(POLICY_STATUS, ACTIVE);
            }
        };
        UserSpec expected = new UserSpec(filter);
        //act
        MapBasedSpecification<User> actual = specFactory.getUserSpec(filter);
        //assert
        Assertions.assertEquals(expected.getSort(), actual.getSort());
        Assertions.assertEquals(expected.getSpecs(), actual.getSpecs());
    }


}
