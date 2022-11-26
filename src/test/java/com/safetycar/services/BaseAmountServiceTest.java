package com.safetycar.services;

import com.safetycar.models.BaseAmount;
import com.safetycar.repositories.BaseAmountRepository;
import com.safetycar.repositories.filter.BaseAmountSpec;
import com.safetycar.exceptions.EntityNotFoundException;
import com.safetycar.services.factories.BaseAmountSpecFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.safetycar.SafetyCarTestObjectsFactory.getBaseAmount;

@ExtendWith(MockitoExtension.class)
public class BaseAmountServiceTest {

    @InjectMocks
    private BaseAmountServiceImpl baseAmountService;

    @Mock
    private BaseAmountRepository mockBaseAmountRepository;
    @Mock
    private BaseAmountSpecFactory mockFactory;

    private BaseAmount testBaseAmount;
    private Map<String, String> testFilter;
    private BaseAmountSpec testSpec;

    @BeforeEach
    void init() {
        testBaseAmount = getBaseAmount();
        testFilter = new HashMap<>();
        testSpec = new BaseAmountSpec(testFilter);
    }

    @Test
    public void get_ShouldReturn_CriteriaMatch() {
        //arrange
        Mockito.when(mockFactory.getBaseAmountSpec(testFilter)).thenReturn(testSpec);
        Mockito.when(mockBaseAmountRepository.findOne(testSpec))
                .thenReturn(Optional.ofNullable(testBaseAmount));
        //act
        BaseAmount actual = baseAmountService.getBaseAmount(testFilter);
        //assert
        Assertions.assertEquals(actual, testBaseAmount);
    }

    @Test
    public void get_ShouldThrow_CriteriaDoNotMatch() {
        //arrange
        Mockito.when(mockFactory.getBaseAmountSpec(testFilter)).thenReturn(testSpec);
        Mockito.when(mockBaseAmountRepository.findOne(testSpec))
                .thenReturn(Optional.empty());
        //act, assert
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> baseAmountService.getBaseAmount(testFilter));
    }

}
