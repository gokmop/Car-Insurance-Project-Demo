package com.safetycar.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetycar.web.dto.offer.CreateOfferDtoForRest;
import com.safetycar.web.errors.util.ApiSubError;
import com.safetycar.web.errors.util.ApiValidationError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.LinkedList;
import java.util.List;

import static com.safetycar.TestHelper.WHY_JSON;
import static com.safetycar.TestHelper.getJsonPropertyValueAsString;
import static com.safetycar.web.dto.offer.OfferDto.*;
import static com.safetycar.web.errors.handlers.RestHandler.MALFORMED_JSON_REQUEST;
import static com.safetycar.web.errors.util.ApiPayload.VALIDATION_FAILED;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestHandlerTest {

    public static final String CREATE_OFFER_DTO_FOR_REST = "createOfferDtoForRest";
    public static final String MODEL_ID = "modelId";
    public static final String DATE_REGISTERED = "dateRegistered";
    public static final String CAPACITY = "capacity";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateOfferDtoForRest dto;

    @Test
    @WithMockUser
    public void handleMethodArgumentNotValid_ShouldReturnCorrectResponse() throws Exception {
        //arrange
        dto = new CreateOfferDtoForRest();
        int invalid = -1;
        dto.setModelId(invalid);
        dto.setCapacity(invalid);
        List<ApiSubError> errors = new LinkedList<>();
        errors.add(new ApiValidationError(CREATE_OFFER_DTO_FOR_REST, MODEL_ID, String.valueOf(invalid), MUST_SELECT_MODEL));
        errors.add(new ApiValidationError(CREATE_OFFER_DTO_FOR_REST, DATE_REGISTERED, null, CAN_NOT_BE_NULL));
        errors.add(new ApiValidationError(CREATE_OFFER_DTO_FOR_REST, CAPACITY, String.valueOf(invalid), MUST_BE_A_POSITIVE_NUMBER));
        //act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/offers/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String actualBody = mvcResult.getResponse().getContentAsString();

        String actualSubErrors = getJsonPropertyValueAsString(objectMapper, actualBody, "subErrors");
        String actualErrorMessage = getJsonPropertyValueAsString(objectMapper, actualBody, "message");
        //assert
        String expectedSubErrors = objectMapper.writeValueAsString(errors);
        Assertions.assertEquals(expectedSubErrors, actualSubErrors);
        Assertions.assertEquals(WHY_JSON + VALIDATION_FAILED + WHY_JSON, actualErrorMessage);
    }

    @Test
    @WithMockUser
    public void handleHttpMessageNotReadable_ShouldReturnCorrectResponse() throws Exception {
        //arrange
        dto = new CreateOfferDtoForRest();
        String registeredDateValue = ":null";
        String registeredDateValueReplacement = ":invalid";
        String content = objectMapper.writeValueAsString(dto)
                .replace(registeredDateValue, registeredDateValueReplacement);
        //act, assert
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/offers/create")
                .contentType("application/json")
                .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value(MALFORMED_JSON_REQUEST));
    }


}
