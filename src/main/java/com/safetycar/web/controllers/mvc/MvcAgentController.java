package com.safetycar.web.controllers.mvc;

import com.safetycar.models.Policy;
import com.safetycar.models.Status;
import com.safetycar.models.users.User;
import com.safetycar.models.users.UserDetails;
import com.safetycar.services.contracts.*;
import com.safetycar.web.dto.mappers.PolicyMapper;
import com.safetycar.web.dto.mappers.UserMapper;
import com.safetycar.web.dto.policy.ManagePolicyDto;
import com.safetycar.web.dto.policy.SearchPolicyDto;
import com.safetycar.web.dto.policy.ShowPolicyDto;
import com.safetycar.web.dto.user.SearchUsersDto;
import com.safetycar.web.dto.user.ShowUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.safetycar.repositories.filter.UserSpec.POLICIES;
import static com.safetycar.repositories.filter.UserSpec.STATUS;
import static com.safetycar.util.Constants.Views.QueryConstants.ID;
import static com.safetycar.util.Constants.Views.QueryConstants.SORT_PARAMETER;
import static com.safetycar.web.controllers.mvc.MvcPolicyController.SEARCH_POLICY_DTO;
import static com.safetycar.web.controllers.mvc.MvcPolicyController.USER;
import static com.safetycar.web.controllers.mvc.MvcUserController.SEARCH_USERS_DTO;

@Controller
@PreAuthorize("hasRole('ROLE_AGENT')")
public class MvcAgentController {

    public static final String USERS_RESULTS_VIEW = "users-results";
    public static final String USERS = "users";
    public static final String MANAGE_STATUS = "manageStatus";
    public static final String STATUS_ALREADY_SET_TO = "Status already set to ";
    public static final String POLICY_MANAGE_VIEW = "policy-manage";
    public static final String POLICIES_USER_RESULTS_VIEW = "policies-user-list";
    public static final String REDIRECT_POLICY_MANAGE = "redirect:/policy/manage/";

    private final UserService userService;
    private final UserMapper userMapper;
    private final PolicyMapper policyMapper;
    private final PolicyService policyService;
    private final PolicyManager policyManager;
    private final StatusService statusService;

    @Autowired
    public MvcAgentController(UserService userService,
                              UserMapper userMapper,
                              PolicyMapper policyMapper,
                              PolicyService policyService,
                              PolicyManager policyManager,
                              StatusService statusService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.policyMapper = policyMapper;
        this.policyService = policyService;
        this.policyManager = policyManager;
        this.statusService = statusService;
    }

    @GetMapping("/users/show")
    public String handleShowRequest(Model model) {
        Iterable<UserDetails> detailsList = userService.getAllDetails();
        prepareResultsView(model, new SearchUsersDto(), detailsList);
        return USERS_RESULTS_VIEW;
    }

    @GetMapping("/users/search")
    public String handleSearchRequest(Model model, SearchUsersDto dto) {
        List<UserDetails> detailsList = userService.searchDetails(dto.getSearchParams());
        prepareResultsView(model, dto, detailsList);
        return USERS_RESULTS_VIEW;
    }

    @GetMapping("/policies/show/{id}")
    public String showThisUsersPolicies(@PathVariable int id, Model model) {
        UserDetails details = userService.getUserDetails(id);

        HashMap<String, String> order = new HashMap<>();
        order.put(SORT_PARAMETER, ID);
        order.put(USER, USER);

        Collection<Policy> policies = policyService
                .searchMyPolicies(details.getPolicies(), order);

        SearchPolicyDto dto = new SearchPolicyDto();
        dto.setUserId(details.getIntegerId());
        preparePolicyResults(model, dto, policies);
        return POLICIES_USER_RESULTS_VIEW;
    }

    @GetMapping("/policies/search/personal")
    public String searchThisUsersPolicies(Model model, SearchPolicyDto dto) {
        UserDetails details = userService.getUserDetails(dto.getUserId());

        Collection<Policy> policies = policyService
                .searchMyPolicies(details.getPolicies(), dto.getSearchParams(USER));

        preparePolicyResults(model, dto, policies);
        return POLICIES_USER_RESULTS_VIEW;
    }

    @GetMapping("/policy/manage/{id}")
    public String showManageUserPolicy(@PathVariable int id, Model model) {
        Policy policy = policyService.getOne(id);
        UserDetails user = policy.getOwner().getUserDetails();

        ManagePolicyDto status = new ManagePolicyDto();
        status.setStatus(policy.getStatus().getStatus());

        prepareManageView(model, policy, user, status);
        return POLICY_MANAGE_VIEW;
    }

    @PostMapping("/policy/manage/{id}")
    public String handleManageUserPolicy(@PathVariable int id,
                                         @Valid @ModelAttribute(MANAGE_STATUS) ManagePolicyDto dto,
                                         BindingResult bindingResult,
                                         Model model,
                                         Principal principal) {
        Policy policy = policyService.getOne(id);
        UserDetails owner = policy.getOwner().getUserDetails();
        if (bindingResult.hasErrors()) {
            prepareManageView(model, policy, owner, dto);
            return POLICY_MANAGE_VIEW;
        }
        if (dto.getStatusId().equals(policy.getStatus().getId())) {
            bindingResult.rejectValue(
                    STATUS, "error." + MANAGE_STATUS,
                    STATUS_ALREADY_SET_TO + dto.getStatus());
            prepareManageView(model, policy, owner, dto);
            return POLICY_MANAGE_VIEW;
        }
        User agent = userService.userByEmail(principal.getName());
        Status status = statusService.getOne(dto.getStatusId());

        policyManager.adminUpdatePolicy(policy, status, agent, dto.getMessage());
        return REDIRECT_POLICY_MANAGE + id;
    }

    private void prepareManageView(Model model, Policy policy, UserDetails user, ManagePolicyDto status) {
        model.addAttribute(USER, user);
        model.addAttribute("policy", policy);
        model.addAttribute(MANAGE_STATUS, status);
    }

    private void prepareResultsView(Model model, SearchUsersDto dto, Iterable<UserDetails> detailsList) {
        Collection<ShowUserDto> dtos = userMapper.toDto(detailsList);
        model.addAttribute(SEARCH_USERS_DTO, dto);
        model.addAttribute(USERS, dtos);
    }

    private void preparePolicyResults(Model model, SearchPolicyDto dto, Collection<Policy> policies) {
        List<ShowPolicyDto> dtos = policyMapper.toDto(policies);
        model.addAttribute(POLICIES, dtos);
        model.addAttribute(SEARCH_POLICY_DTO, dto);
    }
}
