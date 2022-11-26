package com.safetycar.web.controllers.mvc;

import com.safetycar.exceptions.EntityNotFoundException;
import com.safetycar.models.Offer;
import com.safetycar.models.Policy;
import com.safetycar.models.users.User;
import com.safetycar.models.users.UserDetails;
import com.safetycar.services.contracts.*;

import com.safetycar.services.contracts.PolicyService;

import com.safetycar.web.dto.mappers.PolicyMapper;
import com.safetycar.web.dto.policy.SearchPolicyDto;
import com.safetycar.web.dto.policy.ShowDetailedPolicyDto;
import com.safetycar.web.dto.policy.ShowPolicyDto;
import com.safetycar.web.dto.policy.CreatePolicyDto;
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
import java.util.*;

import static com.safetycar.repositories.filter.UserSpec.POLICIES;
import static com.safetycar.util.Constants.OfferConstants.TRANSFER_OFFER;
import static com.safetycar.util.Constants.UserConstants.DETAILS;
import static com.safetycar.util.Constants.UserConstants.FILL_DETAILS;
import static com.safetycar.util.Constants.Views.QueryConstants.ID;
import static com.safetycar.util.Constants.Views.QueryConstants.SORT_PARAMETER;
import static com.safetycar.web.controllers.mvc.MvcOfferController.REDIRECT_DETAILS_ENDPOINT;


@Controller
public class MvcPolicyController {

    public static final String POLICY_DTO = "policyDto";
    public static final String USER = "user";
    public static final String REDIRECT_POLICY_CREATE = "redirect:/policy/create/";
    public static final String PENDING = "pending";
    public static final String OFFER = "offer";
    public static final String SEARCH_POLICY_DTO = "searchPolicyDto";
    public static final String REDIRECT = "redirect";
    public static final String OFFER_NOT_FOUND = "Offer not found!";

    private final PolicyService policyService;
    private final PolicyMapper policyMapper;
    private final UserService userService;
    private final OfferService offerService;
    private final AuthorisationService authorisationService;
    private final PolicyManager policyManager;

    @Autowired
    public MvcPolicyController(PolicyService policyService,
                               PolicyMapper policyMapper,
                               UserService userService,
                               OfferService offerService,
                               AuthorisationService authorisationService,
                               PolicyManager policyManager) {
        this.policyService = policyService;
        this.policyMapper = policyMapper;
        this.userService = userService;
        this.offerService = offerService;
        this.authorisationService = authorisationService;
        this.policyManager = policyManager;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/policy/create/{offerId}")
    public String getPolicyCreatePage(Model model, Principal principal,
                                      @PathVariable int offerId,
                                      HttpServletRequest request) {
        User user = userService.userByEmail(principal.getName());
        HttpSession session = request.getSession(false);
        Offer offer = offerService.getOne(offerId);
        if (session != null) {
            Optional<Object> redirectParam =
                    Optional.ofNullable(session.getAttribute(REDIRECT));
            if (!user.getUserDetails().isActive() && redirectParam.isEmpty()) {
                session.setAttribute(DETAILS, FILL_DETAILS);
                session.setAttribute(REDIRECT, REDIRECT_POLICY_CREATE + offerId);
                return REDIRECT_DETAILS_ENDPOINT;
            }
            session.removeAttribute(REDIRECT);
            session.setAttribute(TRANSFER_OFFER, offer);
        }
        authorisationService.authorise(user, offer);
        UserDetails userDetails = user.getUserDetails();

        preparePolicyCreate(model, offer, userDetails, new CreatePolicyDto());
        return "policy-create";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/policy/create")
    public String createPolicy(Model model,
                               HttpServletRequest request,
                               Principal principal,
                               @Valid @ModelAttribute(POLICY_DTO) CreatePolicyDto dto,
                               BindingResult bindingResult) {
        HttpSession session = request.getSession(false);
        User owner = userService.userByEmail(principal.getName());
        Optional<Offer> optionalOffer = Optional.empty();
        if (session != null) {
            optionalOffer = Optional.ofNullable((Offer) session.getAttribute(TRANSFER_OFFER));
        }
        Offer offer = optionalOffer
                .orElseThrow(() -> new EntityNotFoundException(OFFER_NOT_FOUND));

        if (!owner.getUserDetails().isActive()) {
            return REDIRECT_POLICY_CREATE + offer.getId();
        }
        if (bindingResult.hasErrors()) {
            preparePolicyCreate(model, offer, owner.getUserDetails(), dto);
            return "policy-create";
        }
        Policy policy = policyMapper.assemble(offer, dto, owner);
        session.removeAttribute(TRANSFER_OFFER);

        policyManager.createPolicy(policy, offer, owner);
        return "redirect:/users/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/policy/{id}")
    public String getPolicyById(@PathVariable int id, Model model, Principal principal) {
        User user = userService.userByEmail(principal.getName());
        Policy policy = policyService.getOne(id);

        authorisationService.authorise(user, policy);
        ShowDetailedPolicyDto dto = policyMapper.toDetailedDto(policy);

        model.addAttribute("policyDetails", dto);

        return "policy-details";
    }

    @PreAuthorize("hasRole('ROLE_AGENT')")
    @GetMapping("/policies/agent")
    public String getAllPolicies(Model model) {
        Iterable<Policy> allPoliciesList = policyService.getAll();

        preparePolicyList(model, allPoliciesList, new SearchPolicyDto());
        return "policies-list";
    }

    @PreAuthorize("hasRole('ROLE_AGENT')")
    @GetMapping("/policies/search/agent")
    public String searchAllPolicies(Model model, SearchPolicyDto dto) {
        Iterable<Policy> allPoliciesList = policyService
                .searchMyPolicies(new LinkedList<>(), dto.getSearchParams());

        preparePolicyList(model, allPoliciesList, dto);
        return "policies-list";
    }

    @GetMapping("/policies/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String showMyPolicies(Model model, Principal principal) {
        User owner = userService.userByEmail(principal.getName());
        Map<String, String> criteria = new HashMap<>();
        criteria.put(SORT_PARAMETER, ID);
        criteria.put(USER, USER);

        Collection<Policy> policies = policyService
                .searchMyPolicies(owner.getPolicies(), criteria);
        preparePolicyList(model, policies, new SearchPolicyDto());
        return "policies-list";
    }

    @GetMapping("/policies/search/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String handleSearchMyPolicies(Model model, Principal principal,
                                         SearchPolicyDto dto) {
        User owner = userService.userByEmail(principal.getName());

        LinkedList<Policy> policies = new LinkedList<>(policyService
                .searchMyPolicies(owner.getPolicies(), dto.getSearchParams(USER)));
        preparePolicyList(model, policies, dto);
        return "policies-list";
    }

    private void preparePolicyCreate(Model model,
                                     Offer offer,
                                     UserDetails userDetails,
                                     CreatePolicyDto policyDto) {
        model.addAttribute(OFFER, offer);
        model.addAttribute(USER, userDetails);
        model.addAttribute(POLICY_DTO, policyDto);
    }

    private void preparePolicyList(Model model,
                                   Iterable<Policy> allPoliciesList,
                                   SearchPolicyDto dto) {
        List<ShowPolicyDto> policiesDtoList = policyMapper.toDto(allPoliciesList);
        model.addAttribute(POLICIES, policiesDtoList);
        model.addAttribute(SEARCH_POLICY_DTO, dto);
    }
}
