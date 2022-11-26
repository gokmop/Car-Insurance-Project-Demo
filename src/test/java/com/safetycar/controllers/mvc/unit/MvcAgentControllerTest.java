package com.safetycar.controllers.mvc.unit;

import com.safetycar.enums.PolicyStatuses;
import com.safetycar.models.Offer;
import com.safetycar.models.Policy;
import com.safetycar.models.Status;
import com.safetycar.models.users.User;
import com.safetycar.models.users.UserDetails;
import com.safetycar.services.contracts.PolicyManager;
import com.safetycar.services.contracts.PolicyService;
import com.safetycar.services.contracts.StatusService;
import com.safetycar.services.contracts.UserService;
import com.safetycar.web.controllers.mvc.MvcAgentController;
import com.safetycar.web.dto.mappers.PolicyMapper;
import com.safetycar.web.dto.mappers.UserMapper;
import com.safetycar.web.dto.policy.ManagePolicyDto;
import com.safetycar.web.dto.policy.SearchPolicyDto;
import com.safetycar.web.dto.policy.ShowPolicyDto;
import com.safetycar.web.dto.user.SearchUsersDto;
import com.safetycar.web.dto.user.ShowUserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.safetycar.SafetyCarTestObjectsFactory.*;
import static com.safetycar.repositories.filter.UserSpec.POLICIES;
import static com.safetycar.web.controllers.mvc.MvcAgentController.*;
import static com.safetycar.web.controllers.mvc.MvcPolicyController.SEARCH_POLICY_DTO;
import static com.safetycar.web.controllers.mvc.MvcPolicyController.USER;
import static com.safetycar.web.controllers.mvc.MvcUserController.SEARCH_USERS_DTO;

@ExtendWith(MockitoExtension.class)
public class MvcAgentControllerTest {

    @InjectMocks
    private MvcAgentController controller;

    @Mock
    private UserService userService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PolicyMapper policyMapper;
    @Mock
    private PolicyService policyService;
    @Mock
    private PolicyManager policyManager;
    @Mock
    private StatusService statusService;
    @Mock
    private Model model;
    @Mock
    private Principal principal;
    @Mock
    private BindingResult bindingResult;

    private User user;
    private List<UserDetails> detailsList;
    private Offer offer;
    private Policy policy;
    private List<Policy> policyList;
    private Collection<ShowUserDto> showUserDtos;
    private List<ShowPolicyDto> showPolicyDtos;
    private SearchUsersDto searchUsersDto = new SearchUsersDto();
    private SearchPolicyDto searchPolicyDto = new SearchPolicyDto();

    @BeforeEach
    void init() {
        user = getUser();
        offer = getOffer();
        policy = getPolicy(user, offer);
        policyList = Collections.singletonList(policy);
        detailsList = Collections.singletonList(user.getUserDetails());
        showUserDtos = Collections.singletonList(getShowUserDto(user));
        showPolicyDtos = Collections.singletonList(getShowPolicyDto(policy));
    }

    @Test
    public void handleShowRequest_ShouldPerformTasks() {
        //arrange
        Mockito.when(userService.getAllDetails()).thenReturn(detailsList);
        Mockito.when(userMapper.toDto(detailsList)).thenReturn(showUserDtos);
        //act
        String actual = controller.handleShowRequest(model);
        //assert
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(SEARCH_USERS_DTO, searchUsersDto);
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(USERS, showUserDtos);
        Assertions.assertEquals(USERS_RESULTS_VIEW, actual);
    }

    @Test
    public void handleSearchRequest_ShouldPerformTasks() {
        //arrange
        Mockito.when(userService.searchDetails(searchUsersDto.getSearchParams()))
                .thenReturn(detailsList);
        Mockito.when(userMapper.toDto(detailsList)).thenReturn(showUserDtos);
        //act
        String actual = controller.handleSearchRequest(model, searchUsersDto);
        //assert
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(SEARCH_USERS_DTO, searchUsersDto);
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(USERS, showUserDtos);
        Assertions.assertEquals(USERS_RESULTS_VIEW, actual);
    }

    @Test
    public void showThisUsersPolicies_ShouldPerformTasks() {
        //arrange
        Mockito.when(userService.getUserDetails(Mockito.anyInt()))
                .thenReturn(user.getUserDetails());
        Mockito.when(policyService.searchMyPolicies(Mockito.anyCollection(), Mockito.anyMap()))
                .thenReturn(policyList);
        Mockito.when(policyMapper.toDto(policyList)).thenReturn(showPolicyDtos);
        //act
        String actual = controller.showThisUsersPolicies(Mockito.anyInt(), model);
        //assert
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(POLICIES, showPolicyDtos);
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(SEARCH_POLICY_DTO, searchPolicyDto);
        Assertions.assertEquals(POLICIES_USER_RESULTS_VIEW, actual);

    }

    @Test
    public void searchThisUsersPolicies_ShouldPerformTasks() {
        //arrange
        Mockito.when(userService.getUserDetails(Mockito.anyInt()))
                .thenReturn(user.getUserDetails());
        Map<String, String> searchParams = searchPolicyDto.getSearchParams();
        searchParams.put(USER,USER);
        Mockito.when(policyService.searchMyPolicies(user.getUserDetails().getPolicies(), searchParams))
                .thenReturn(policyList);
        Mockito.when(policyMapper.toDto(policyList)).thenReturn(showPolicyDtos);
        //act
        String actual = controller.searchThisUsersPolicies(model, searchPolicyDto);
        //assert
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(POLICIES, showPolicyDtos);
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(SEARCH_POLICY_DTO, searchPolicyDto);
        Assertions.assertEquals(POLICIES_USER_RESULTS_VIEW, actual);

    }

    @Test
    public void showManageUserPolicy_ShouldPerformTasks() {
        //arrange
        Mockito.when(policyService.getOne(Mockito.anyInt())).thenReturn(policy);
        ManagePolicyDto status = new ManagePolicyDto();
        status.setStatus(policy.getStatus().getStatus());
        //act
        String actual = controller.showManageUserPolicy(Mockito.anyInt(), model);
        //assert
        Assertions.assertEquals(POLICY_MANAGE_VIEW, actual);
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(USER, user.getUserDetails());
        Mockito.verify(model, Mockito.times(1))
                .addAttribute("policy", policy);
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(MANAGE_STATUS, status);
    }

    @Test
    public void handleManageUserPolicy_ShouldPerformTasks() {
        //arrange
        int id = 1;
        Mockito.when(policyService.getOne(id)).thenReturn(policy);
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        Mockito.when(principal.getName()).thenReturn(user.getUserName());
        Mockito.when(userService.userByEmail(user.getUserName())).thenReturn(user);

        Status newStatus = policy.getStatus();
        newStatus.setStatus(PolicyStatuses.APPROVED.toString());
        Mockito.when(statusService.getOne(Mockito.anyInt()))
                .thenReturn(newStatus);
        ManagePolicyDto statusUpdate = new ManagePolicyDto();
        int notStatusId = -1;
        statusUpdate.setStatusId(notStatusId);
        String expected = REDIRECT_POLICY_MANAGE + id;
        //act
        String actual = controller.handleManageUserPolicy(id, statusUpdate, bindingResult, model, principal);
        //assert

        Mockito.verify(policyManager, Mockito.times(1))
                .adminUpdatePolicy(policy, newStatus, user, statusUpdate.getMessage());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void handleManageUserPolicy_ShouldReturnView_WithBindingResultErrors() {
        //arrange
        int id = 1;
        Mockito.when(policyService.getOne(id)).thenReturn(policy);
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);

        ManagePolicyDto statusUpdate = new ManagePolicyDto();
        //act
        String actual = controller.handleManageUserPolicy(id, statusUpdate, bindingResult, model, principal);
        //assert

        Mockito.verify(model, Mockito.times(1))
                .addAttribute(USER, user.getUserDetails());
        Mockito.verify(model, Mockito.times(1))
                .addAttribute("policy", policy);
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(MANAGE_STATUS, statusUpdate);
        Assertions.assertEquals(POLICY_MANAGE_VIEW, actual);
    }

    @Test
    public void handleManageUserPolicy_ShouldReturnView_WithBindingResultErrors_OnInvalidStatus() {
        //arrange
        int id = 1;
        Mockito.when(policyService.getOne(id)).thenReturn(policy);
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);

        ManagePolicyDto statusUpdate = new ManagePolicyDto();
        statusUpdate.setStatusId(policy.getStatus().getId());
        //act
        String actual = controller.handleManageUserPolicy(id, statusUpdate, bindingResult, model, principal);
        //assert

        Mockito.verify(model, Mockito.times(1))
                .addAttribute(USER, user.getUserDetails());
        Mockito.verify(model, Mockito.times(1))
                .addAttribute("policy", policy);
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(MANAGE_STATUS, statusUpdate);
        Assertions.assertEquals(POLICY_MANAGE_VIEW, actual);
    }

}
