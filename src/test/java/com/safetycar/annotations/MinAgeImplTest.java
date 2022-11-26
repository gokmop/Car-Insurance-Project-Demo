package com.safetycar.annotations;

import com.safetycar.enums.LegalAges;
import com.safetycar.validation.MinAge;
import com.safetycar.validation.impl.ValidMinAgeLocalDateImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.*;
import java.lang.annotation.Annotation;
import java.time.LocalDate;

import static org.mockito.Mockito.doCallRealMethod;


@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class MinAgeImplTest {

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;
    @Mock
    private ValidMinAgeLocalDateImpl minAge;

    @BeforeEach
    public void init() {

        doCallRealMethod().when(minAge).initialize(Mockito.any());
        Mockito.when(minAge.isValid(Mockito.any(), Mockito.any())).thenCallRealMethod();

        ValidMinAgeTestClass testClass = new ValidMinAgeTestClass();

        minAge.initialize(testClass);

    }

    @Test
    public void isValid_ShouldReturnFalse_WhenAgeBelowValid() {
        //arrange
        LocalDate date = LocalDate.now();
        //act
        boolean actual = minAge.isValid(date, constraintValidatorContext);
        //assert
        Assertions.assertFalse(actual);
    }

    @Test
    public void isValid_ShouldReturnTrue_WhenAgeAboveValid() {
        //arrange
        LocalDate date = LocalDate.now().minusYears(LegalAges.MIN.getAge());
        //act
        boolean actual = minAge.isValid(date, constraintValidatorContext);
        //assert
        Assertions.assertTrue(actual);
    }

    private static class ValidMinAgeTestClass implements MinAge {


        @Override
        public String message() {
            return "Test Message";
        }

        @Override
        public Class<?>[] groups() {
            return new Class[]{};
        }

        @Override
        public LegalAges value() {
            return LegalAges.MIN;
        }

        @Override
        public Class<? extends Payload>[] payload() {
            return new Class[0];
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return MinAge.class;
        }

    }
}
