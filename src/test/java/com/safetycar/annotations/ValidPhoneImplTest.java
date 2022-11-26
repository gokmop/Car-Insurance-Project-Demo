package com.safetycar.annotations;

import com.safetycar.validation.ValidTelephone;
import com.safetycar.validation.impl.ValidTelephoneImpl;
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

import static com.safetycar.util.Constants.ValidationConstants.TELEPHONE_REGEX;
import static org.mockito.Mockito.doCallRealMethod;


@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ValidPhoneImplTest {

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;
    @Mock
    private ValidTelephoneImpl telephone;

    @BeforeEach
    public void init() {

        doCallRealMethod().when(telephone).initialize(Mockito.any());
        Mockito.when(telephone.isValid(Mockito.any(), Mockito.any())).thenCallRealMethod();

        ValidTelephoneTestClass testClass = new ValidTelephoneTestClass();

        telephone.initialize(testClass);

    }

    @Test
    public void isValid_ShouldReturnFalse_WhenPhoneNoSeparatorsContainsLetter() {
        //arrange
        String phone = "33455554d";
        //act
        boolean actual = telephone.isValid(phone, constraintValidatorContext);
        //assert
        Assertions.assertFalse(actual);
    }

    @Test
    public void isValid_ShouldReturnTrue_WhenPhoneWithDashSeparatorsValid() {
        //arrange
        String phone = "334-55-55-43";
        //act
        boolean actual = telephone.isValid(phone, constraintValidatorContext);
        //assert
        Assertions.assertTrue(actual);
    }

    @Test
    public void isValid_ShouldReturnFalse_WhenPhoneWithDashSeparatorsContainsLetter() {
        //arrange
        String phone = "334-55-55-43d";
        //act
        boolean actual = telephone.isValid(phone, constraintValidatorContext);
        //assert
        Assertions.assertFalse(actual);
    }

    @Test
    public void isValid_ShouldReturnTrue_WhenPhoneWithSpaceSeparatorsValid() {
        //arrange
        String phone = "334 55 55 43";
        //act
        boolean actual = telephone.isValid(phone, constraintValidatorContext);
        //assert
        Assertions.assertTrue(actual);
    }

    @Test
    public void isValid_ShouldReturnFalse_WhenPhoneWithSpaceSeparatorsContainsLetter() {
        //arrange
        String phone = "334 55 55 43d";
        //act
        boolean actual = telephone.isValid(phone, constraintValidatorContext);
        //assert
        Assertions.assertFalse(actual);
    }

    @Test
    public void isValid_ShouldReturnTrue_WhenPhoneWithoutSeparatorsValid() {
        //arrange
        String phone = "334555543";
        //act
        boolean actual = telephone.isValid(phone, constraintValidatorContext);
        //assert
        Assertions.assertTrue(actual);
    }

    private static class ValidTelephoneTestClass implements ValidTelephone {


        @Override
        public String message() {
            return "testMessage";
        }

        @Override
        public Class<?>[] groups() {
            return new Class[0];
        }

        @Override
        public Class<? extends Payload>[] payload() {
            return new Class[0];
        }

        @Override
        public String regexp() {
            return TELEPHONE_REGEX;
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return ValidTelephone.class;
        }
    }
}
