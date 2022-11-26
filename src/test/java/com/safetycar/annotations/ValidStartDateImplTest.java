package com.safetycar.annotations;

import com.safetycar.validation.ValidStartDate;
import com.safetycar.validation.impl.ValidStartDateImpl;
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

import static com.safetycar.web.dto.policy.CreatePolicyDto.DAYS_AHEAD;
import static org.mockito.Mockito.doCallRealMethod;


@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ValidStartDateImplTest {

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;
    @Mock
    private ValidStartDateImpl validStartDate;

    @BeforeEach
    public void init() {

        doCallRealMethod().when(validStartDate).initialize(Mockito.any());
        Mockito.when(validStartDate.isValid(Mockito.any(), Mockito.any())).thenCallRealMethod();

        ValidStartDateTestClass testClass = new ValidStartDateTestClass();

        validStartDate.initialize(testClass);

    }

    @Test
    public void isValid_ShouldReturnFalse_WhenLocalDateNow() {
        //arrange
        LocalDate date = LocalDate.now();
        //act
        boolean actual = validStartDate.isValid(date, constraintValidatorContext);
        //assert
        Assertions.assertFalse(actual);
    }
    @Test
    public void isValid_ShouldReturnTrue_WhenNull() {
        //arrange
        LocalDate date = null;
        //act
        boolean actual = validStartDate.isValid(date, constraintValidatorContext);
        //assert
        Assertions.assertTrue(actual);
    }

    @Test
    public void isValid_ShouldReturnTrue_WhenLocalDateFutureMoreThan_NowAndLessThan_ConstraintValue() {
        //arrange
        LocalDate date = LocalDate.now().plusDays(1);
        //act
        boolean actual = validStartDate.isValid(date, constraintValidatorContext);
        //assert
        Assertions.assertTrue(actual);
    }

    @Test
    public void isValid_ShouldReturnFalse_WhenLocalDateFutureMoreThan_ConstraintValue() {
        //arrange
        LocalDate date = LocalDate.now().plusDays(DAYS_AHEAD).plusDays(1);
        //act
        boolean actual = validStartDate.isValid(date, constraintValidatorContext);
        //assert
        Assertions.assertFalse(actual);
    }

    private static class ValidStartDateTestClass implements ValidStartDate {


        @Override
        public long value() {
            return DAYS_AHEAD;
        }

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
            return ValidStartDate.class;
        }

    }
}
