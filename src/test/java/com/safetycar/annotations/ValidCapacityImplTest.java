package com.safetycar.annotations;

import com.safetycar.validation.MaxAge;
import com.safetycar.validation.ValidCapacity;
import com.safetycar.validation.impl.ValidCapacityImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Annotation;

import static org.mockito.Mockito.doCallRealMethod;


@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ValidCapacityImplTest {

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;
    @Mock
    private ValidCapacityImpl validCapacity;

    @BeforeEach
    public void init() {

        doCallRealMethod().when(validCapacity).initialize(Mockito.any());
        Mockito.when(validCapacity.isValid(Mockito.any(), Mockito.any())).thenCallRealMethod();

        ValidMaxAgeTestClass testClass = new ValidMaxAgeTestClass();

        validCapacity.initialize(testClass);

    }

    @Test
    public void isValid_ShouldReturnTrue_WhenCapacityValidIntegerAboveZero() {
        //arrange
        String capacity = String.valueOf((int) (Math.random() * Integer.MAX_VALUE));
        //act
        boolean actual = validCapacity.isValid(capacity, constraintValidatorContext);
        //assert
        Assertions.assertTrue(actual);
    }

    @Test
    public void isValid_ShouldReturnFalse_CapacityNotANumber() {
        //arrange
        String capacity = "invalid";
        //act
        boolean actual = validCapacity.isValid(capacity, constraintValidatorContext);
        //assert
        Assertions.assertFalse(actual);
    }

    @Test
    public void isValid_ShouldReturnFalse_CapacityNotAnInteger() {
        //arrange
        String capacity = "220.443";
        //act
        boolean actual = validCapacity.isValid(capacity, constraintValidatorContext);
        //assert
        Assertions.assertFalse(actual);
    }

    @Test
    public void isValid_ShouldReturnFalse_CapacityNegative() {
        //arrange
        String capacity = String.valueOf(-1);
        //act
        boolean actual = validCapacity.isValid(capacity, constraintValidatorContext);
        //assert
        Assertions.assertFalse(actual);
    }

    private static class ValidMaxAgeTestClass implements ValidCapacity {


        @Override
        public String message() {
            return "Test Message";
        }

        @Override
        public Class<?>[] groups() {
            return new Class[]{};
        }

        @Override
        public Class<? extends Payload>[] payload() {
            return new Class[0];
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return MaxAge.class;
        }

    }
}
