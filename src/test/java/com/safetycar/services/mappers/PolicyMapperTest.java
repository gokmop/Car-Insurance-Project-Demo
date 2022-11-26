package com.safetycar.services.mappers;

import com.safetycar.models.Image;
import com.safetycar.models.Offer;
import com.safetycar.models.Policy;
import com.safetycar.models.Status;
import com.safetycar.models.users.User;
import com.safetycar.services.contracts.StatusService;
import com.safetycar.web.dto.mappers.ImageMapper;
import com.safetycar.web.dto.mappers.PolicyMapper;
import com.safetycar.web.dto.policy.CreatePolicyDto;
import com.safetycar.web.dto.policy.ShowDetailedPolicyDto;
import com.safetycar.web.dto.policy.ShowPolicyDto;
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

import java.util.Collections;
import java.util.List;

import static com.safetycar.SafetyCarTestObjectsFactory.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class PolicyMapperTest {

    @InjectMocks
    private PolicyMapper policyMapper;
    @Mock
    private ImageMapper imageMapper;
    @Mock
    private StatusService statusService;

    private User user;
    private Offer offer;
    private Policy policy;
    private ShowPolicyDto showPolicyDto;
    private ShowDetailedPolicyDto showDetailedPolicyDto;
    private CreatePolicyDto createPolicyDto;
    private Status status;
    private Image image;

    @BeforeEach
    void init() {
        user = getUser();
        offer = getOffer();
        policy = getPolicy(user, offer);
        showPolicyDto = getShowPolicyDto(policy);
        showDetailedPolicyDto = getShowDetailedPolicyDto(policy);
        createPolicyDto = getCreatePolicyDto(policy);
        status = getStatus();
        image = getImage();

    }

    @Test
    public void assemble_ShouldReturnPolicy() {
        //arrange
        Mockito.when(imageMapper.fromMultipart(createPolicyDto.getFile())).thenReturn(image);
        Mockito.when(statusService.getOne(Mockito.anyInt())).thenReturn(status);
        policy.setStartDate(java.sql.Date.valueOf(createPolicyDto.getStartDate()));
        policy.setEndDate(java.sql.Date.valueOf(createPolicyDto.getStartDate().plusYears(1)));
        //act
        Policy actual = policyMapper.assemble(offer, createPolicyDto, user);
        //assert
        Assertions.assertEquals(policy, actual);
    }

    @Test
    public void toDto_ShouldReturnShowPolicyDto() {
        //arrange
        //act
        ShowPolicyDto actual = policyMapper.toDto(policy);
        //assert
        Assertions.assertEquals(showPolicyDto, actual);
    }

    @Test
    public void toDetailedDto_ShouldReturnShowDetailedPolicyDto() {
        //arrange
        //act
        ShowDetailedPolicyDto actual = policyMapper.toDetailedDto(policy);
        //assert
        Assertions.assertEquals(showDetailedPolicyDto, actual);
    }

    @Test
    public void toDtoWithIterable_ShouldReturn_CollectionShowPolicyDto() {
        //arrange
        List<ShowPolicyDto> expected = Collections.singletonList(showPolicyDto);
        List<Policy> policies = Collections.singletonList(policy);
        //act
        List<ShowPolicyDto> actual = policyMapper.toDto(policies);
        //assert
        Assertions.assertEquals(expected, actual);

    }

}
