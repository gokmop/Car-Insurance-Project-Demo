package com.safetycar.annotations;

import com.safetycar.models.users.User;
import com.safetycar.validation.PasswordMatches;
import com.safetycar.validation.impl.PasswordMatchesImpl;
import com.safetycar.web.dto.user.CreateUserDto;
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

import static com.safetycar.SafetyCarTestObjectsFactory.getCreateUserDto;
import static com.safetycar.SafetyCarTestObjectsFactory.getUser;
import static org.mockito.Mockito.doCallRealMethod;


@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class PasswordMatchesTest {

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;
    @Mock
    private PasswordMatchesImpl matches;
    private CreateUserDto testDto;
    private User testUser;

    @BeforeEach
    public void init() {
        testUser = getUser();
        testDto = getCreateUserDto(testUser);
        doCallRealMethod().when(matches).initialize(Mockito.any());
        Mockito.when(matches.isValid(Mockito.any(), Mockito.any())).thenCallRealMethod();

        ValidTelephoneTestClass testClass = new ValidTelephoneTestClass();

        matches.initialize(testClass);

    }

    @Test
    public void isValid_ShouldReturnFalse_WhenPasswordDoesNotMatch() {
        //arrange
        //act
        boolean actual = matches.isValid(testDto, constraintValidatorContext);
        //assert
        Assertions.assertTrue(actual);
    }

    @Test
    public void isValid_ShouldReturnFalse_WhenPasswordMatches() {
        //arrange
        testDto.setConfirmationPassword("No Match");
        //act
        boolean actual = matches.isValid(testDto, constraintValidatorContext);
        //assert
        Assertions.assertFalse(actual);
    }

    private static class ValidTelephoneTestClass implements PasswordMatches {


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
        public Class<? extends Annotation> annotationType() {
            return PasswordMatches.class;
        }
    }
}
