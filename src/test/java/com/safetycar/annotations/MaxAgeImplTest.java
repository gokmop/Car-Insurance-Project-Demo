package com.safetycar.annotations;

import com.safetycar.enums.LegalAges;
import com.safetycar.validation.MaxAge;
import com.safetycar.validation.impl.ValidMaxAgeLocalDateImpl;
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
import java.time.LocalDate;

import static org.mockito.Mockito.doCallRealMethod;


@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class MaxAgeImplTest {

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;
    @Mock
    private ValidMaxAgeLocalDateImpl maxAge;

    @BeforeEach
    public void init() {

        doCallRealMethod().when(maxAge).initialize(Mockito.any());
        Mockito.when(maxAge.isValid(Mockito.any(), Mockito.any())).thenCallRealMethod();

        ValidMaxAgeTestClass testClass = new ValidMaxAgeTestClass();

        maxAge.initialize(testClass);

    }

    @Test
    public void isValid_ShouldReturnTrue_WhenAgeBelowValid() {
        //arrange
        LocalDate date = LocalDate.now().plusYears(LegalAges.MIN.getAge());
        //act
        boolean actual = maxAge.isValid(date, constraintValidatorContext);
        //assert
        Assertions.assertTrue(actual);
    }

    @Test
    public void isValid_ShouldReturnFalse_WhenAgeAboveValid() {
        //arrange
        LocalDate date = LocalDate.now().plusYears(LegalAges.MAX.getAge());
        //act
        boolean actual = maxAge.isValid(date, constraintValidatorContext);
        //assert
        Assertions.assertTrue(actual);
    }

    private static class ValidMaxAgeTestClass implements MaxAge {


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
            return LegalAges.MAX;
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
