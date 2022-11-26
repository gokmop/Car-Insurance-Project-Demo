package com.safetycar.controllers.mvc.unit;

import com.safetycar.models.Offer;
import com.safetycar.models.users.User;
import com.safetycar.services.contracts.*;
import com.safetycar.web.controllers.mvc.MvcOfferController;
import com.safetycar.web.dto.mappers.OfferMapper;
import com.safetycar.web.dto.offer.OfferDto;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.safetycar.SafetyCarTestObjectsFactory.*;

public class OfferControllerTest {

    @InjectMocks
    private MvcOfferController controller;
    @Mock
    private OfferService offerService;
    @Mock
    private BrandService brandService;
    @Mock
    private ModelService modelService;
    @Mock
    private OfferMapper offerMapper;
    @Mock
    private UserService userService;
    @Mock
    private OfferAppendingService offerAppendingService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private Model model;
    @Mock
    private BindingResult bindingResult;


    private Offer offer;
    private OfferDto dto;
    private User user;

    @BeforeEach
    void init() {
        offer = getOffer();
        dto = getOfferDto(offer, offer.isAboveTwentyFive(), offer.hadAccidents());
        user = getUser();
    }


}
