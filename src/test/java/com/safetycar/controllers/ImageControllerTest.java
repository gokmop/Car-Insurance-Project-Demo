package com.safetycar.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetycar.enums.AllowedContent;
import com.safetycar.exceptions.EntityNotFoundException;
import com.safetycar.models.Image;
import com.safetycar.services.contracts.ImageService;
import com.safetycar.web.errors.util.ApiPayload;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.safetycar.TestHelper.WHY_JSON;
import static com.safetycar.TestHelper.getJsonPropertyValueAsString;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ImageService imageService;

    private Image image;

    @Test
    @WithMockUser
    public void downloadFile_InvalidId_ShouldThrow() throws Exception {
        String expected = "Image with id 1 doesn't exist!";
        EntityNotFoundException e = new EntityNotFoundException(expected);
        //arrange
        int fileId = 1;
        Mockito.doThrow(e).when(imageService).getFile(fileId);
        //act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/downloadFile/{fileId}", 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        String actualBody = mvcResult.getResponse().getContentAsString();

        String actual = getJsonPropertyValueAsString(objectMapper, actualBody, "message");
        //assert
        Assertions.assertEquals(WHY_JSON + expected + WHY_JSON, actual);

    }

    @Test
    @WithMockUser
    public void downloadFile_ShouldReturn() throws Exception {
        //arrange
        int fileId = 1;
        image = new Image();
        image.setImageType(AllowedContent.JPEG.toString());
        image.setImageName("someImage");
        image.setData(new byte[]{1});

        //act
        Mockito.when(imageService.getFile(fileId)).thenReturn(image);

        //assert
        mockMvc.perform(MockMvcRequestBuilders
                .get("/downloadFile/{fileId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

}
