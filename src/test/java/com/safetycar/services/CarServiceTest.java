package com.safetycar.services;

import com.safetycar.exceptions.EntityNotFoundException;
import com.safetycar.models.Car;
import com.safetycar.models.Model;
import com.safetycar.repositories.CarRepository;
import com.safetycar.repositories.ModelRepository;
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

import static com.safetycar.SafetyCarTestObjectsFactory.getCar;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepository mockCarRepository;

    private Car testCar;
    private List<Car> testCars;

    @BeforeEach
    public void init() {
        testCar = getCar();
        testCars = Collections.singletonList(testCar);
    }

    @Test
    public void get_ShouldReturn_WhenEntityExists() {
        //arrange
        Mockito.when(mockCarRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(testCar));
        //act
        Car actual = carService.getOne(Mockito.anyInt());
        //assert
        Assertions.assertEquals(actual, testCar);

    }

    @Test
    public void get_ShouldThrow_WhenEntityDoesNotExist() {
        //arrange
        Mockito.when(mockCarRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());
        //act, assert
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> carService.getOne(Mockito.anyInt()));
    }

    @Test
    public void getAll_ShouldReturnCollection() {
        //arrange
        Mockito.when(mockCarRepository.findAll()).thenReturn(testCars);
        //act
        List<Car> actual = new ArrayList<>(carService.getAll());
        //assert
        Assertions.assertEquals(actual, testCars);
    }
}
