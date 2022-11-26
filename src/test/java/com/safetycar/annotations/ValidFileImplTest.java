package com.safetycar.annotations;

import com.safetycar.enums.AllowedContent;
import com.safetycar.validation.ValidFile;
import com.safetycar.validation.impl.ValidFileImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Annotation;

import static com.safetycar.enums.AllowedContent.*;
import static org.mockito.Mockito.doCallRealMethod;


@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ValidFileImplTest {

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;
    @Mock
    private ValidFileImpl validFile;

    @BeforeEach
    public void init() {

        doCallRealMethod().when(validFile).initialize(Mockito.any());
        Mockito.when(validFile.isValid(Mockito.any(), Mockito.any())).thenCallRealMethod();

        ValidFileTestClass testClass = new ValidFileTestClass();

        validFile.initialize(testClass);

    }

    @Test
    public void isValid_ShouldReturnTrue_WhenFileValid() {
        //arrange
        MultipartFile file = new MockMultipartFile("testName", "originalTestName", GIF.toString(), new byte[10000]);
        //act
        boolean actual = validFile.isValid(file, constraintValidatorContext);
        //assert
        Assertions.assertTrue(actual);
    }

    @Test
    public void isValid_ShouldReturnFalse_WhenFileTypeInvalid() {
        //arrange
        MultipartFile file = new MockMultipartFile("testName", "originalTestName", "testType", new byte[10000]);
        //act
        boolean actual = validFile.isValid(file, constraintValidatorContext);
        //assert
        Assertions.assertFalse(actual);
    }

    @Test
    public void isValid_ShouldReturnFalse_WhenFileEmpty() {
        //arrange
        MultipartFile file = new MockMultipartFile("testName", "originalTestName", JPEG.name(), new byte[0]);
        //act
        boolean actual = validFile.isValid(file, constraintValidatorContext);
        //assert
        Assertions.assertFalse(actual);
    }

    private static class ValidFileTestClass implements ValidFile {


        @Override
        public AllowedContent[] value() {
            return new AllowedContent[]{GIF,JPEG,PNG};
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
            return ValidFile.class;
        }

    }
}
