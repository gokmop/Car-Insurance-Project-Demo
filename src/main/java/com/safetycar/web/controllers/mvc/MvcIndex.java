package com.safetycar.web.controllers.mvc;

import com.safetycar.services.contracts.BrandService;
import com.safetycar.services.contracts.ModelService;
import com.safetycar.web.dto.offer.OfferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.safetycar.util.Constants.OfferConstants.*;
import static com.safetycar.util.Constants.Views.INDEX_VIEW;
import static com.safetycar.web.controllers.mvc.MvcOfferController.OFFER_ERROR;

@Controller
public class MvcIndex {

    public static final String INDEX_ENDPOINT = "/index";

    private final ModelService modelService;
    private final BrandService brandService;

    @Autowired
    public MvcIndex(ModelService modelService,
                    BrandService brandService) {
        this.modelService = modelService;
        this.brandService = brandService;
    }

    @GetMapping(INDEX_ENDPOINT)
    public String getIndex(Model model, HttpServletRequest request) {
        model.addAttribute(OFFER_DTO, new OfferDto());
        model.addAttribute(MODELS, modelService.getAll());
        model.addAttribute(BRANDS, brandService.getAll());
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(OFFER_ERROR);
        }
        return INDEX_VIEW;
    }
}
