package com.safetycar.services;

import com.safetycar.models.Coefficient;
import com.safetycar.repositories.CoefficientRepository;
import com.safetycar.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.safetycar.SafetyCarTestObjectsFactory.getCoefficient;

@ExtendWith(MockitoExtension.class)
public class CoefficientServiceTest {

    @InjectMocks
    private CoefficientServiceImpl coefficientService;

    @Mock
    private CoefficientRepository mockCoefficientRepository;

    private Coefficient testCoeff;

    @BeforeEach
    public void init() {
        testCoeff = getCoefficient();
    }

    @Test
    public void getCoefficient_ShouldReturn_WhenCoeffWithProperIdExists() {
        //arrange
        Mockito.when(mockCoefficientRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(testCoeff));
        //act
        Coefficient actual = coefficientService.getCoefficient();
        //assert
        Assertions.assertEquals(actual, testCoeff);

    }

    @Test
    public void getCoefficient_ShouldThrow_WhenImproperIdUsed() {
        //arrange
        Mockito.when(mockCoefficientRepository.findById(Mockito.any()))
                .thenReturn(Optional.empty());
        //act, assert
        Assertions.assertThrows(EntityNotFoundException.class,
                ()-> coefficientService.getCoefficient());
    }
}
