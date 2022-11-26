package com.safetycar.services.factories;

import com.safetycar.models.BaseAmount;
import com.safetycar.repositories.filter.BaseAmountSpec;
import com.safetycar.repositories.filter.base.MapBasedSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.safetycar.SafetyCarTestObjectsFactory.getOffer;
import static com.safetycar.SafetyCarTestObjectsFactory.getUser;
import static com.safetycar.util.Constants.Views.QueryConstants.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class BaseAmountSpecFactoryTest {

    @InjectMocks
    private BaseAmountSpecFactoryImpl specFactory;

    @Test
    public void getSpec_NoParam_ShouldGetSpec() {
        //arrange
        MapBasedSpecification<BaseAmount> expected = new BaseAmountSpec();
        //act
        MapBasedSpecification<BaseAmount> actual = specFactory.getBaseAmountSpec();
        //assert
        Assertions.assertEquals(expected.getSort(), actual.getSort());
        Assertions.assertEquals(expected.getSpecs(), actual.getSpecs());
    }

    @Test
    public void getSpec_WithMap_ShouldGetSpec() {
        //arrange
        Map<String, String> filter = new HashMap<>() {
            {
                put(SORT_PARAMETER, ID);
                put(SUBMISSION_DATE, LocalDate.now().toString());
            }
        };
        MapBasedSpecification<BaseAmount> expected = new BaseAmountSpec(filter);
        //act
        MapBasedSpecification<BaseAmount> actual = specFactory.getBaseAmountSpec(filter);
        //assert
        Assertions.assertEquals(expected.getSort(), actual.getSort());
        Assertions.assertEquals(expected.getSpecs(), actual.getSpecs());
    }

}
