package com.safetycar.annotations;

import com.safetycar.validation.ValidName;
import com.safetycar.validation.ValidTelephone;
import com.safetycar.validation.impl.ValidNameImpl;
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

import static com.safetycar.util.Constants.ValidationConstants.NAME_REGEX;
import static org.mockito.Mockito.doCallRealMethod;


@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ValidNameImplTest {

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;
    @Mock
    private ValidNameImpl validName;

    @BeforeEach
    public void init() {

        doCallRealMethod().when(validName).initialize(Mockito.any());
        Mockito.when(validName.isValid(Mockito.any(), Mockito.any())).thenCallRealMethod();

        ValidNameTestClass testClass = new ValidNameTestClass();

        validName.initialize(testClass);

    }

    @Test
    public void isValid_ShouldReturnFalse_WhenNameInvalid() {
        //arrange
        //act
        //assert
        Assertions.assertFalse(validName.isValid("Ivan1", constraintValidatorContext));
        Assertions.assertFalse(validName.isValid("ivan", constraintValidatorContext));
        Assertions.assertFalse(validName.isValid("Ivan.", constraintValidatorContext));
        Assertions.assertFalse(validName.isValid(".Ivan", constraintValidatorContext));
        Assertions.assertFalse(validName.isValid("'Ivan", constraintValidatorContext));
        Assertions.assertFalse(validName.isValid("Ivan:", constraintValidatorContext));
        Assertions.assertFalse(validName.isValid("Iv;an", constraintValidatorContext));
        Assertions.assertFalse(validName.isValid("Iv!an", constraintValidatorContext));
        Assertions.assertFalse(validName.isValid("Iv=an", constraintValidatorContext));
        Assertions.assertFalse(validName.isValid("Iva+n", constraintValidatorContext));
        Assertions.assertFalse(validName.isValid("-Ivan", constraintValidatorContext));
        Assertions.assertFalse(validName.isValid("Iva/n", constraintValidatorContext));
        Assertions.assertFalse(validName.isValid("Iva\\n", constraintValidatorContext));
        Assertions.assertFalse(validName.isValid("Iva|n", constraintValidatorContext));
        Assertions.assertFalse(validName.isValid("Iva+n", constraintValidatorContext));
        Assertions.assertFalse(validName.isValid("I'van", constraintValidatorContext));
        Assertions.assertFalse(validName.isValid("Iv 'an", constraintValidatorContext));
        Assertions.assertFalse(validName.isValid("Ivan  ovich evsky", constraintValidatorContext));
    }

    @Test
    public void isValid_ShouldReturnTrue_WhenNameValid() {
        //arrange
        //act
        //assert
        Assertions.assertTrue(validName.isValid("Ivan", constraintValidatorContext));
        Assertions.assertTrue(validName.isValid("IVAN", constraintValidatorContext));
        Assertions.assertTrue(validName.isValid("Ivanov", constraintValidatorContext));
        Assertions.assertTrue(validName.isValid("Ivanovich", constraintValidatorContext));
        Assertions.assertTrue(validName.isValid("Ivanovichevsky", constraintValidatorContext));
        Assertions.assertTrue(validName.isValid("Ivan-ovich-evsky", constraintValidatorContext));
        Assertions.assertTrue(validName.isValid("Ivan'ovich'evsky", constraintValidatorContext));
        Assertions.assertTrue(validName.isValid("Ivan ovich evsky", constraintValidatorContext));
        Assertions.assertTrue(validName.isValid("Ivan OVich Evsky", constraintValidatorContext));
        Assertions.assertTrue(validName.isValid("IVAN OVICH EVSKY", constraintValidatorContext));
        Assertions.assertTrue(validName.isValid("Iva'n", constraintValidatorContext));
        Assertions.assertTrue(validName.isValid("Iva-n", constraintValidatorContext));
        Assertions.assertTrue(validName.isValid("Iv'an", constraintValidatorContext));
        Assertions.assertTrue(validName.isValid("Iv an", constraintValidatorContext));
    }

    private static class ValidNameTestClass implements ValidName {


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
            return NAME_REGEX;
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return ValidTelephone.class;
        }
    }
}
