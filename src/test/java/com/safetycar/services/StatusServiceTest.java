package com.safetycar.services;

import com.safetycar.exceptions.EntityNotFoundException;
import com.safetycar.models.Status;
import com.safetycar.repositories.StatusRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StatusServiceTest {

    @InjectMocks
    private StatusServiceImpl statusService;

    @Mock
    private StatusRepository statusRepository;

    private Status status;
    private List<Status> statuses;

    @BeforeEach
    void init() {
        status = new Status();
        status.setStatus("testStatus");
        statuses = Collections.singletonList(status);
    }

    @Test
    public void get_ShouldGet_WhenValidId() {
        //arrange
        Mockito.when(statusRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(status));
        //act
        Status actual = statusService.getOne(Mockito.anyInt());
        //assert
        Assertions.assertEquals(status, actual);
    }

    @Test
    public void get_ShouldThrow_WhenInvalidId() {
        //arrange
        Mockito.when(statusRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());
        //act, assert
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> statusService.getOne(Mockito.anyInt()));
    }

    @Test
    public void getAll_ShouldReturnCollection() {
        //arrange
        Mockito.when(statusRepository.findAll()).thenReturn(statuses);
        //act
        List<Status> actual = statusService.getAll();
        //assert
        Assertions.assertEquals(statuses,actual);
    }

}
