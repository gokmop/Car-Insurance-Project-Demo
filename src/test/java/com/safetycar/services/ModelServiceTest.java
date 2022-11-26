package com.safetycar.services;

import com.safetycar.models.Model;
import com.safetycar.repositories.ModelRepository;
import com.safetycar.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.safetycar.SafetyCarTestObjectsFactory.getModel;

@ExtendWith(MockitoExtension.class)
public class ModelServiceTest {

    @InjectMocks
    private ModelServiceImpl modelService;

    @Mock
    private ModelRepository mockModelRepository;

    private Model testModel;
    private List<Model> testModels;

    @BeforeEach
    public void init() {
        testModel = getModel();
        testModels = Collections.singletonList(testModel);
    }

    @Test
    public void get_ShouldReturn_WhenEntityExists() {
        //arrange
        Mockito.when(mockModelRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(testModel));
        //act
        Model actual = modelService.getOne(Mockito.anyInt());
        //assert
        Assertions.assertEquals(actual, testModel);

    }

    @Test
    public void get_ShouldThrow_WhenEntityDoesNotExist() {
        //arrange
        Mockito.when(mockModelRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());
        //act, assert
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> modelService.getOne(Mockito.anyInt()));
    }

    @Test
    public void getAll_ShouldReturnCollection() {
        //arrange
        Mockito.when(mockModelRepository.findAll()).thenReturn(testModels);
        //act
        List<Model> actual = new ArrayList<>(modelService.getAll());
        //assert
        Assertions.assertEquals(actual, testModels);
    }

    @Test
    public void filter_ShouldReturnCollection() {
        //arrange
        Mockito.when(mockModelRepository.findAllDistinctByBrand_IdOrderByNameAscYearAsc(Mockito.anyInt()))
                .thenReturn(testModels);
        //act
        List<Model> actual = new ArrayList<>(modelService.filter(Mockito.anyInt()));
        //assert
        Assertions.assertEquals(actual, testModels);
    }

    @Test
    public void getModelNamesByBrandId_ShouldReturnList() {
        //arrange
        Mockito.when(mockModelRepository.findAllDistinctByBrand_IdOrderByNameAscYearAsc(Mockito.anyInt()))
                .thenReturn(testModels);
        List<String> expected = testModels.stream()
                .map(Model::getName).collect(Collectors.toList());
        //act
        List<String> actual = modelService.getModelNamesByBrandId(Mockito.anyInt());
        //assert
        Assertions.assertEquals(expected, actual);
    }


}
