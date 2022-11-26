package com.safetycar.services.mappers;

import com.safetycar.exceptions.FileStorageException;
import com.safetycar.models.Image;
import com.safetycar.web.dto.mappers.ImageMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.safetycar.SafetyCarTestObjectsFactory.getFile;
import static com.safetycar.SafetyCarTestObjectsFactory.getImage;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class ImageMapperTest {
    @InjectMocks
    private ImageMapper imageMapper;

    @Mock
    private MultipartFile mockFile;

    private MultipartFile testFile;
    private Image testImage;

    @BeforeEach
    void init() {
        testImage = getImage();
        testFile = getFile(testImage);
    }

    @Test
    public void fromMultipart_Should_ReturnImage() {
        //arrange
        //act
        Image actual = imageMapper.fromMultipart(testFile);
        //assert
        Assertions.assertEquals(testImage, actual);

    }

    @Test
    public void fromMultipart_ShouldThrowAppropriate_OnIOError() throws IOException {
        //arrange
        Mockito.when(mockFile.getBytes()).thenThrow(IOException.class);
        //act
        //assert
        Assertions.assertThrows(FileStorageException.class,
                () -> imageMapper.fromMultipart(mockFile));

    }

    @Test
    public void fromMultipart_ShouldThrowAppropriate_OnNullError() throws IOException {
        //arrange
        Mockito.when(mockFile.getOriginalFilename()).thenThrow(NullPointerException.class);
        //act
        //assert
        Assertions.assertThrows(FileStorageException.class,
                () -> imageMapper.fromMultipart(mockFile));

    }

    @Test
    public void updateFromMultipart_Should_ReturnImage() {
        //arrange
        //act
        Image actual = imageMapper.updateFromMultipart(new Image(), testFile);
        //assert
        Assertions.assertEquals(testImage, actual);
    }

    @Test
    public void updateFromMultipart_ShouldThrowAppropriate_OnIOError() throws IOException {
        //arrange
        Mockito.when(mockFile.getBytes()).thenThrow(IOException.class);
        //act
        //assert
        Assertions.assertThrows(FileStorageException.class,
                () -> imageMapper.updateFromMultipart(new Image(), mockFile));

    }

    @Test
    public void updateFromMultipart_ShouldThrowAppropriate_OnNullError() throws IOException {
        //arrange
        Mockito.when(mockFile.getOriginalFilename()).thenThrow(NullPointerException.class);
        //act
        //assert
        Assertions.assertThrows(FileStorageException.class,
                () -> imageMapper.updateFromMultipart(new Image(), mockFile));

    }
}
