package com.safetycar.services;

import com.safetycar.exceptions.EntityNotFoundException;
import com.safetycar.exceptions.FileStorageException;
import com.safetycar.models.Image;
import com.safetycar.repositories.ImageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

import static com.safetycar.SafetyCarTestObjectsFactory.getFile;
import static com.safetycar.SafetyCarTestObjectsFactory.getImage;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @InjectMocks
    private ImageServiceImpl imageService;

    @Mock
    private ImageRepository mockImageRepo;

    private MultipartFile testFile;
    private Image testImage;

    @BeforeEach
    void init() {
        testImage = getImage();
        testFile = getFile();
    }

    @Test
    public void createImage_ShouldThrowAppropriateException_OnFailure() {
        //arrange
        RuntimeException e = new RuntimeException("file broke");
        Mockito.doThrow(e).when(mockImageRepo).save(testImage);
        //act, assert
        Assertions.assertThrows(FileStorageException.class,
                () -> imageService.saveFile(testFile));
    }

    @Test
    public void createImage_ShouldCreate() {
        //arrange
        //act
        imageService.saveFile(testFile);
        //assert
        Mockito.verify(mockImageRepo, Mockito.times(1))
                .save(Mockito.any());
    }

    @Test
    public void getOne_ShouldGet() {
        //arrange
        Mockito.when(mockImageRepo.findById(Mockito.anyInt()))
                .thenReturn(java.util.Optional.of(testImage));
        //act
        Image actual = imageService.getFile(Mockito.anyInt());
        //assert
        Assertions.assertEquals(testImage, actual);
    }

    @Test
    public void getOne_ShouldThrow_whenMissing() {
        //arrange
        Mockito.when(mockImageRepo.findById(Mockito.anyInt()))
                .thenReturn(java.util.Optional.empty());
        //act assert
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> imageService.getFile(Mockito.anyInt()));
    }

    @Test
    public void getAll_ShouldReturnCollection() {
        //arrange
        List<Image> expected = Collections.singletonList(testImage);
        Mockito.when(mockImageRepo.findAll()).thenReturn(expected);
        //act
        List<Image> actual = imageService.getFiles();
        //assert
        Assertions.assertEquals(expected, actual);

    }


}
