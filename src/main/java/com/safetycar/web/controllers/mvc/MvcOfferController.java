package com.safetycar.web.controllers.mvc;

import com.safetycar.exceptions.DuplicateEntityException;
import com.safetycar.models.*;
import com.safetycar.models.users.User;
import com.safetycar.services.contracts.*;
import com.safetycar.web.dto.mappers.OfferMapper;
import com.safetycar.exceptions.EntityNotFoundException;
import com.safetycar.exceptions.TooManyOffersException;
import com.safetycar.web.dto.offer.OfferDto;
import com.safetycar.web.dto.offer.SearchOffersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

import static com.safetycar.util.Constants.BaseAmountConstants.CAPACITY;
import static com.safetycar.util.Constants.OfferConstants.*;
import static com.safetycar.util.Constants.ValidationConstants.ERROR_;
import static com.safetycar.util.Constants.Views.*;

@Controller
@RequestMapping(MvcOfferController.HOME_ENDPOINT)
public class MvcOfferController {

    public static final String HOME_ENDPOINT = "/";
    public static final String PROCEED_ENDPOINT = "proceed";
    public static final String MY_OFFERS_ENDPOINT = "my/offers";
    public static final String MY_SEARCH_OFFERS_ENDPOINT = "my/offers/search";
    public static final String REDIRECT_DETAILS_ENDPOINT = "redirect:/users/profile/edit";
    public static final String REDIRECT_LOGIN_ENDPOINT = "redirect:/login";
    public static final String REDIRECT_INDEX_ENDPOINT = "redirect:/index";
    public static final String REGISTER_FIRST = "registerFirst";
    public static final String CONTINUE_WITH_YOUR_OFFER = "Please register to continue with your offer.";
    public static final String OFFER_ERROR = "offerError";

    private final OfferService offerService;
    private final BrandService brandService;
    private final ModelService modelService;
    private final OfferMapper offerMapper;
    private final UserService userService;
    private final OfferAppendingService offerAppendingService;

    @Autowired
    public MvcOfferController(OfferService offerService,
                              BrandService brandService,
                              ModelService modelService,
                              OfferMapper offerMapper,
                              UserService userService,
                              OfferAppendingService offerAppendingService) {
        this.offerService = offerService;
        this.brandService = brandService;
        this.modelService = modelService;
        this.offerMapper = offerMapper;
        this.userService = userService;
        this.offerAppendingService = offerAppendingService;
    }

    @ModelAttribute(name = BRANDS)
    public Iterable<Brand> getMakes() {
        return brandService.getAll();
    }

    @ModelAttribute(name = MODELS)
    public Iterable<com.safetycar.models.Model> getModels() {
        return modelService.getAll();
    }

    @PostMapping(PROCEED_ENDPOINT)
    public String proceedOffer(Model model,
                               @SessionAttribute(TRANSFER_OFFER) Offer offer,
                               Principal principal,
                               HttpServletRequest request) {
        try {
            //registered user
            if (principal != null && principal.getName() != null) {
                User user = userService.userByEmail(principal.getName());
                offerAppendingService.appendOffer(offer, user);
                userService.update(user);
                model.addAttribute(OFFER_DTO, new OfferDto());
                return REDIRECT_INDEX_ENDPOINT;
            }
            //unregistered user
            request.getSession().setAttribute(REGISTER_FIRST, CONTINUE_WITH_YOUR_OFFER);
            return REDIRECT_LOGIN_ENDPOINT;
        } catch (TooManyOffersException | DuplicateEntityException e) {
            return handleOfferError(model, INDEX_VIEW, e.getMessage());
        }
    }

    @GetMapping(MY_OFFERS_ENDPOINT)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String handleGetMyOffers(Model model, Principal principal) {
        User user = userService.userByEmail(principal.getName());
        Collection<Offer> userOffers = offerService.getMyOffers(user);
        prepareOfferResults(model, new SearchOffersDto(), userOffers);
        return OFFER_RESULTS_VIEW;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(MY_SEARCH_OFFERS_ENDPOINT)
    public String handleSearchMyOffers(Model model,
                                       Principal principal,
                                       SearchOffersDto dto) {
        User user = userService.userByEmail(principal.getName());
        Collection<Offer> userOffers = offerService
                .searchMyOffers(user, dto.getSearchParams());

        prepareOfferResults(model, dto, userOffers);
        return OFFER_RESULTS_VIEW;
    }

    @GetMapping
    public String calculatePremium(Model model) {
        model.addAttribute(OFFER_DTO, new OfferDto());
        return INDEX_VIEW;
    }

    @GetMapping("/dedicated/simulation")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String calculatePremiumOfferPage(Model model) {
        OfferDto dto = new OfferDto();
        dto.setView(OFFER_VIEW);
        model.addAttribute(OFFER_DTO, dto);
        return OFFER_VIEW;
    }

    @PostMapping("/dedicated/simulation")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String calculatePremiumOfferPage(Model model, @Valid OfferDto dto,
                                            BindingResult bindingResult,
                                            HttpServletRequest request) {
        return handlePostCalculatePremium(model, dto, bindingResult, request, OFFER_VIEW);
    }

    @PostMapping("/dedicated/simulation/append")
    public String appendOfferFromOfferPage(HttpServletRequest request,
                                           Principal principal,
                                           Model model) {
        try {
            User user = userService.userByEmail(principal.getName());
            Offer offer = (Offer) request.getSession().getAttribute(TRANSFER_OFFER);

            offerAppendingService.appendOffer(offer, user);
            userService.update(user);
            return "redirect:/dedicated/simulation";
        } catch (TooManyOffersException | DuplicateEntityException e) {
            return handleOfferError(model, OFFER_VIEW, e.getMessage());
        }
    }

    @PostMapping
    public String calculatePremium(Model model, @Valid OfferDto dto,
                                   BindingResult bindingResult,
                                   HttpServletRequest request) {
        return handlePostCalculatePremium(model, dto, bindingResult, request, INDEX_VIEW);
    }

    private void prepareOfferResults(Model model, SearchOffersDto dto, Collection<Offer> userOffers) {
        model.addAttribute(MY_OFFERS, userOffers);
        model.addAttribute(SEARCH_OFFERS_DTO, dto);
    }

    private String handlePostCalculatePremium(Model model,
                                              @Valid OfferDto dto,
                                              BindingResult bindingResult,
                                              HttpServletRequest request,
                                              String view) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(OFFER_DTO, dto);
            return view;
        }
        try {

            Offer offer = offerMapper.fromDto(dto);
            String result = offer.getPremium().toString();

            request.getSession().setAttribute(TRANSFER_OFFER, offer);
            model.addAttribute(OFFER_DTO, dto);
            model.addAttribute(RESULT, result);
            return view;
        } catch (EntityNotFoundException e) {
            bindingResult.rejectValue(CAPACITY, ERROR_ + OFFER_DTO, e.getMessage());
            model.addAttribute(OFFER_DTO, dto);
            return view;
        }
    }

    private String handleOfferError(Model model, String view, String errorMessage) {
        model.addAttribute(OFFER_ERROR, errorMessage);
        model.addAttribute(OFFER_DTO, new OfferDto());
        return view;
    }
}
