package com.safetycar.web.controllers.mvc;

import com.safetycar.exceptions.DuplicateEntityException;
import com.safetycar.models.Image;
import com.safetycar.models.Policy;
import com.safetycar.models.users.User;
import com.safetycar.models.users.UserDetails;
import com.safetycar.services.contracts.*;
import com.safetycar.web.dto.mappers.ImageMapper;
import com.safetycar.web.dto.policy.CreatePolicyDto;
import com.safetycar.web.dto.user.CreateDetailsDto;
import com.safetycar.web.dto.user.ShowUserDto;
import com.safetycar.web.dto.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.sql.Date;
import java.util.Optional;

import static com.safetycar.util.Constants.UserConstants.*;
import static com.safetycar.util.Constants.ValidationConstants.BINDING_RESULT_ERRORS;
import static com.safetycar.util.Constants.ValidationConstants.ERROR_;
import static com.safetycar.util.Constants.Views.QueryConstants.TELEPHONE;
import static com.safetycar.web.controllers.mvc.MvcPolicyController.*;

@Controller
@RequestMapping("/users")
public class MvcUserController {

    public static final String PROFILE_ID = "/profile/{id}";
    public static final String PROFILE_EDIT_ENDPOINT = "/profile/edit";
    public static final String SEARCH_USERS_DTO = "searchUsersDto";
    public static final String PROFILE_EDIT_VIEW = "profile-edit";
    public static final String PROFILE_VIEW = "profile";
    public static final String USER_DETAILS = "userDetails";

    private final AccountManager accountManager;
    private final UserService userService;
    private final UserMapper userMapper;
    private final PolicyService policyService;
    private final AuthorisationService authorisationService;
    private final ImageMapper imageMapper;
    private final PolicyManager policyManager;

    @Autowired
    public MvcUserController(AccountManager accountManager,
                             UserService userService,
                             UserMapper userMapper,
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(PROFILE_EDIT_ENDPOINT)
    public String getDetailsUpdateForm(org.springframework.ui.Model model,
                                       Principal principal,
                                       HttpServletRequest request) {
        CreateDetailsDto dto = accountManager.getDetailsToEdit(principal.getName());
        HttpSession session = request.getSession(false);
        String message = null;
        if (session != null) {
            message = (String) session.getAttribute(DETAILS);
        }
        model.addAttribute(CREATE_USER_DTO, dto);
        model.addAttribute(DETAILS, message);
        return PROFILE_EDIT_VIEW;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(PROFILE_EDIT_ENDPOINT)
    public String handleEditDetailsForm(
            @Valid @ModelAttribute(CREATE_USER_DTO) CreateDetailsDto dto,
            BindingResult bindingResult,
            Model model,
            Principal principal,
            HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(CREATE_USER_DTO, dto);
            return PROFILE_EDIT_VIEW;
        }
        try {
            HttpSession session = request.getSession(false);
            Optional<Object> redirect;
            if (session != null) {
                redirect = Optional.ofNullable(session.getAttribute("redirect"));
                session.removeAttribute(DETAILS);
                if (redirect.isPresent()) {
                    accountManager.updateDetails(dto, principal.getName());
                    return (String) redirect.orElse(PROFILE_EDIT_VIEW);
                }
            }
            accountManager.updateDetails(dto, principal.getName());
            model.addAttribute(CREATE_USER_DTO, dto);
            return PROFILE_EDIT_VIEW;
        } catch (DuplicateEntityException e) {
            bindingResult.rejectValue(TELEPHONE, ERROR_ +
                    CREATE_USER_DTO, e.getMessage());
            model.addAttribute(CREATE_USER_DTO, dto);
            model.addAttribute(BINDING_RESULT_ERRORS, e.getMessage());
            return PROFILE_EDIT_VIEW;
        }
    }

    @GetMapping(PROFILE_ID)
    public String showUserProfilePage(@PathVariable int id, Model model, Principal principal) {
        if (principal != null && id != 0) {
            User user = userService.userByEmail(principal.getName());
            return getView(model, user.getUserDetails());
        }
        UserDetails details = userService.getUserDetails(id);
        return getView(model, details);
    }

    @GetMapping("/profiles/{id}")
    public String showUserProfilePageNew(@PathVariable Integer id,
                                         Model model,
                                         Principal principal) {
        User user = userService.userByEmail(principal.getName());
        UserDetails details = userService.getUserDetails(id);
        authorisationService.authorise(details, user);
        return getView(model, details);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/profile")
    public String showUserProfile(Model model, Principal principal) {
        User user = userService.userByEmail(principal.getName());
        UserDetails details = user.getUserDetails();
        return getView(model, details);
    }

    @GetMapping("/manage/policy/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getManagePolicyPage(Model model, Principal principal,
                                      @PathVariable int id) {
        User user = userService.userByEmail(principal.getName());
        Policy policy = policyService.getOne(id);
        authorisationService.authorise(user, policy);

        CreatePolicyDto dto = getCreatePolicyDto(policy);

        preparePolicyUpdate(model, policy, dto);
        return "policy-update";
    }

    @PostMapping("/manage/policy")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String handleManageUserPolicy(Model model,
                                         Principal principal,
                                         @Valid @ModelAttribute(POLICY_DTO) CreatePolicyDto dto,
                                         BindingResult bindingResult) {
        Policy policy = policyService.getOne(dto.getUpdatePolicyId());
        User owner = userService.userByEmail(principal.getName());
        authorisationService.authorise(owner, policy);
        if (bindingResult.hasErrors()) {
            model.addAttribute("imageId", policy.getImage().getId());
            preparePolicyUpdate(model, policy, dto);
            return "policy-update";
        }
        Image image = imageMapper.updateFromMultipart(policy.getImage(), dto.getFile());
        policy.setImage(image);
        policyManager.userUpdatePolicy(policy, owner);
        preparePolicyUpdate(model, policy, dto);
        return "policy-update";
    }


    private String getView(Model model, UserDetails details) {
        ShowUserDto dto = userMapper.toDto(details);
        model.addAttribute(USER, dto);
        model.addAttribute(USER_DETAILS, details);
        return "user-profile";
    }

    private void preparePolicyUpdate(Model model, Policy policy,
                                     CreatePolicyDto policyDto) {
        model.addAttribute(OFFER, policy.getOffer());
        model.addAttribute(USER, policy.getOwner().getUserDetails());
        model.addAttribute(POLICY_DTO, policyDto);
        model.addAttribute("imageId", policy.getImage().getId());

    }

    private CreatePolicyDto getCreatePolicyDto(Policy policy) {
        java.util.Date submissionDate = policy.getSubmissionDate();
        long bumpAhead = System.currentTimeMillis() - submissionDate.getTime();
        java.util.Date oldStartDate = policy.getStartDate();
        Date newStartDate = new Date(oldStartDate.getTime() + bumpAhead);
        CreatePolicyDto dto = new CreatePolicyDto();
        Date convertToLocal = new Date(newStartDate.getTime());
        dto.setStartDate(convertToLocal.toLocalDate());
        dto.setUpdatePolicyId(policy.getId());
        return dto;
    }
}
