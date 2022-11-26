package com.safetycar.web.controllers.rest;

import com.safetycar.models.*;
import com.safetycar.models.users.User;
import com.safetycar.models.users.UserDetails;
import com.safetycar.web.dto.offer.CalculateOfferDtoForRest;
import com.safetycar.web.dto.offer.CreateOfferDtoForRest;
import com.safetycar.services.contracts.*;
import com.safetycar.web.dto.mappers.OfferMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequestMapping("/api/offers")
@RestController
public class RestOfferController {

    private final OfferService offerService;
    private final OfferMapper offerMapper;
    private final OfferAppendingService offerAppendingService;
    private final UserService userService;
    private final AccountManager accountManager;

    @Autowired
    public RestOfferController(OfferService offerService, OfferMapper offerMapper, OfferAppendingService offerAppendingService, UserService userService, AccountManager accountManager) {
        this.offerService = offerService;
        this.offerMapper = offerMapper;
        this.offerAppendingService = offerAppendingService;
        this.userService = userService;
        this.accountManager = accountManager;
    }

    @GetMapping
    @ApiOperation(value = "List All offers sorted by Id", notes = "Provides Id of offer to continue with policy creation",
            response = Offer.class)
    Iterable<Offer> showAllOffers() {
        return offerService.getAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Show Offer by Id", notes = "Returns detailed information for offer by Id.",
            response = Offer.class)
    Offer showOne(@ApiParam(value = "Valid ID for the Offer you want to retrieve", example = "1")@PathVariable int id) {
        return offerService.getOne(id);
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create offer for UserId", notes = "Creates offer from dto and save it to DB",
            response = Offer.class)
    public Offer createOffer(@Valid @RequestBody CreateOfferDtoForRest dto) {
        Offer offer = offerMapper.fromDtoCreateOfferRest(dto);
        UserDetails userDetails = userService.getUserDetails(dto.getOwnerId());
        User user = userService.userByEmail(userDetails.getUserName());
        offerAppendingService.appendOffer(offer, user);
        userService.update(user);
        return offer;
    }


    @PostMapping("/calculate")
    @ApiOperation(value = "Calculate price for insurance offer",
            notes = "Calculate the price for offer by entering: Is driver above 25 y.o (true/false), " +
                    "Cubic Capacity of the Car(number), " +
                    "Date for first registration of vehicle(yyyy-mm-dd), " +
                    "Driver had accidents in previous year(true/false), " +
                    "Car model Id",
            response = Offer.class)
    public Offer calculatePriceForOffer(@Valid @RequestBody CalculateOfferDtoForRest dto){

        return offerMapper.fromCalculateOfferDtoForRest(dto);
    }



}
