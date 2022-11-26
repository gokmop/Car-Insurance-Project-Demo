package com.safetycar.services;

import com.safetycar.models.Brand;
import com.safetycar.repositories.BrandRepository;
import com.safetycar.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CompositeIterator;

import java.util.*;

import static com.safetycar.SafetyCarTestObjectsFactory.getBrand;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {

    @InjectMocks
    private BrandServiceImpl brandService;

    @Mock
    private BrandRepository mockBrandRepository;

    private Brand testBrand;
    private List<Brand> testBrands;

    @BeforeEach
    public void init() {
        testBrand = getBrand();
        testBrands = Collections.singletonList(testBrand);
    }

    @Test
    public void get_ShouldReturn_WhenEntityExists() {
        //arrange
        Mockito.when(mockBrandRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(testBrand));
        //act
        Brand actual = brandService.getOne(Mockito.anyInt());
        //assert
        Assertions.assertEquals(testBrand, actual);
    }

    @Test
    public void get_ShouldThrow_WhenEntityDoesNotExist() {
        //arrange
        Mockito.when(mockBrandRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());
        //act, assert
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> brandService.getOne(Mockito.anyInt()));
    }

    @Test
    public void getAll_ShouldReturnCollection() {
        //arrange
        Mockito.when(mockBrandRepository.findAll()).thenReturn(testBrands);
        //act
        List<Brand> actual = new ArrayList<>(brandService.getAll());
        //assert
        Assertions.assertEquals(actual, testBrands);
    }

}
