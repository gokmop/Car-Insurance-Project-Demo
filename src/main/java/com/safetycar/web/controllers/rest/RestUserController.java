package com.safetycar.web.controllers.rest;

import com.safetycar.exceptions.DuplicateEntityException;
import com.safetycar.models.Offer;
import com.safetycar.models.users.User;
import com.safetycar.models.users.UserDetails;
import com.safetycar.services.contracts.*;
import com.safetycar.web.dto.mappers.ImageMapper;
import com.safetycar.web.dto.mappers.UserMapper;
import com.safetycar.web.dto.user.CreateDetailsDto;
import com.safetycar.web.dto.user.CreateUserDto;
import com.safetycar.web.dto.user.RestShowUserDto;
import com.safetycar.web.dto.user.ShowUserDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.safetycar.util.Constants.UserConstants.CREATE_USER_DTO;
import static com.safetycar.util.Constants.UserConstants.DETAILS;
import static com.safetycar.util.Constants.ValidationConstants.BINDING_RESULT_ERRORS;
import static com.safetycar.util.Constants.ValidationConstants.ERROR_;
import static com.safetycar.util.Constants.Views.EMAIL_CONFIRMATION_VIEW;
import static com.safetycar.util.Constants.Views.QueryConstants.EMAIL;
import static com.safetycar.util.Constants.Views.REGISTER_VIEW;
import static com.safetycar.web.controllers.mvc.MvcOfferController.REGISTER_FIRST;

@RestController
@RequestMapping("api/users")
public class RestUserController {
    private final AccountManager accountManager;
    private final UserService userService;
    private final UserMapper userMapper;
    private final PolicyService policyService;
    private final AuthorisationService authorisationService;
    private final ImageMapper imageMapper;
    private final PolicyManager policyManager;

    @Autowired
    public RestUserController(AccountManager accountManager,
                              UserService userService, UserMapper userMapper,
                              PolicyService policyService,
                              AuthorisationService authorisationService,
                              ImageMapper imageMapper,
                              PolicyManager policyManager) {
        this.accountManager = accountManager;
        this.userService = userService;
        this.userMapper = userMapper;
        this.policyService = policyService;
        this.authorisationService = authorisationService;
        this.imageMapper = imageMapper;
        this.policyManager = policyManager;
    }
    //Get All User Details - Shows too much information - TODO rework
    @GetMapping
    @ApiOperation(value = "Show All User Details", notes = "Returns User Details for every User in the DB",
            response = UserDetails.class)
    public List<UserDetails> getAllDetails() {
        return userService.getAllDetails();
    }

    //Get User by ID - returns RestShowUserDto with the basic information for user.
    @GetMapping("/{id}")
    @ApiOperation(value = "Show User by Id", notes = "Returns information for user by Id.",
            response = RestShowUserDto.class)
    public RestShowUserDto getDetails(@ApiParam(value = "Valid ID for the User you want to retrieve", example = "1")
                                          @PathVariable Integer id) {
        return userMapper.restShowUserDto(userService.getUserDetails(id));
    }

    //Create User
    @PostMapping("/register")
    @ApiOperation(value = "Register new User without UserDetails", notes = "Create new user without details with status active",
            response = User.class)
    public User registerUser(@Valid CreateUserDto dto) {
        User user = userMapper.fromDto(dto);
        userService.create(user);
        user.setEnabled(true);
        userService.update(user);

        return user;
    }


    @PostMapping("/create-details")
    @ApiOperation(value = "Update Details of User after creation",
            notes = "Update user details of created user by userId. " +
                    "Enter First & Last name, valid UserId, Address, and Phone Number",
            response = RestShowUserDto.class)
    public RestShowUserDto createDetailsForUser(@Valid CreateDetailsDto dto){
        UserDetails userDetails = userMapper.fromDto(dto, userService.getUserDetails(dto.getId()));
        String username = userDetails.getUserName();
        accountManager.updateDetails(dto,username);
        return userMapper.restShowUserDto(userDetails);
    }





}
