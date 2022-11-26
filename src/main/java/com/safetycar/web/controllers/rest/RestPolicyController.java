package com.safetycar.web.controllers.rest;

import com.safetycar.models.Image;
import com.safetycar.models.Offer;
import com.safetycar.models.Policy;
import com.safetycar.models.Status;
import com.safetycar.models.users.User;
import com.safetycar.repositories.UserDetailsRepository;
import com.safetycar.services.ImageServiceImpl;
import com.safetycar.services.contracts.*;
import com.safetycar.web.dto.policy.CreatePolicyDto;
import com.safetycar.web.dto.policy.RestCreatePolicyDto;
import com.safetycar.web.dto.policy.ShowDetailedPolicyDto;
import com.safetycar.web.dto.policy.ShowPolicyDto;
import com.safetycar.web.dto.mappers.PolicyMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/policies")
public class RestPolicyController {

    private final UserService userService;
    private final UserDetailsRepository detailsRepository;
    private final PolicyManager policyManager;
    private final OfferService offerService;
    private final ImageService imageService;
    private final PolicyService policyService;
    private final PolicyMapper policyMapper;

    @Autowired
    public RestPolicyController(UserService userService, UserDetailsRepository detailsRepository,
                                PolicyManager policyManager, OfferService offerService,
                                ImageService imageService, PolicyService policyService,
                                PolicyMapper policyMapper) {
        this.userService = userService;
        this.detailsRepository = detailsRepository;
        this.policyManager = policyManager;
        this.offerService = offerService;
        this.imageService = imageService;
        this.policyService = policyService;
        this.policyMapper = policyMapper;
    }

    @GetMapping
    @ApiOperation(value = "Show All policies in DB", notes = "Shows detailed info for policies with history",
            response = Policy.class)
    public Iterable<Policy> listAllPolicies() {

        return policyService.getAll();

    }


    @PostMapping("/create")
    @ApiOperation(value = "Create new Policy",
            notes = "Create new policy from existing offer and User with filled UserDetails",
            response = ShowDetailedPolicyDto.class)
    public ShowDetailedPolicyDto createPolicy(@Valid @RequestBody RestCreatePolicyDto dto, @RequestParam String username) {

       Offer offer = offerService.getOne(dto.getOfferId());
       User user = userService.userByEmail(username);
       Policy policy = policyMapper.fromRestCreatePolicyDto(offer,user, dto);

       policyManager.createPolicy(policy,offer,user);

       return policyMapper.toDetailedDto(policy);
    }



    @GetMapping("/{id}")
    @ApiOperation(value = "Get policy by Id",
            notes = "Shows information about the policy by Id",
            response = ShowDetailedPolicyDto.class)
    public ShowDetailedPolicyDto showPolicyById(@ApiParam(value = "Valid ID for the Policy you want to retrieve"
                                                    , example = "1")
                                                    @PathVariable int id) {
        Policy policy = policyService.getOne(id);
        return policyMapper.toDetailedDto(policy);
    }

    @PostMapping("/{id}/update")
    @ApiOperation(value = "Update Policy Status by Id",
            notes = "Modify the status of policy by setting the status Id and String with the value: \n" +
                    "1,approved\n" +
                    "2,pending\n" +
                    "3,active\n" +
                    "4,rejected\n" +
                    "5,canceled\n" +
                    "6,expired\n")
    public String updatePolicyStatus(@PathVariable int id, @RequestBody Status status){
        Policy policy = policyService.getOne(id);
        policy.setStatus(status);
        policyService.update(policy);

        return "You have successfully updated the status of this policy";
    }
}
