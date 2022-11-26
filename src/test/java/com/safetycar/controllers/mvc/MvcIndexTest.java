package com.safetycar.controllers.mvc;

import com.safetycar.services.contracts.BrandService;
import com.safetycar.services.contracts.ModelService;
import com.safetycar.web.dto.offer.OfferDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

import static com.safetycar.web.controllers.mvc.MvcOfferController.OFFER_ERROR;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MvcIndexTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ModelService modelService;

    @MockBean
    private BrandService brandService;

    @MockBean
    private HttpServletRequest request;

    @MockBean
    private MockHttpSession session;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getIndex_ShouldLoadPageWithCollections_AndRemoveSessionAttr_WhenSessionNotNull() throws Exception {
        //arrange
        Mockito.when(brandService.getAll()).thenReturn(Collections.emptyList());
        Mockito.when(modelService.getAll()).thenReturn(Collections.emptyList());
        Mockito.when(request.getSession(false)).thenReturn(session);

        //act
        //assert
        mockMvc.perform(MockMvcRequestBuilders.get("/index").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(session, Mockito.times(1)).removeAttribute(OFFER_ERROR);
    }

    @Test
    public void getIndex_ShouldLoadPageWithCollections_SessionNull() throws Exception {
        //arrange
        Mockito.when(brandService.getAll()).thenReturn(Collections.emptyList());
        Mockito.when(modelService.getAll()).thenReturn(Collections.emptyList());
        Mockito.when(request.getSession(false)).thenReturn(null);
        //act
        //assert
        mockMvc.perform(MockMvcRequestBuilders.get("/index").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
