package com.safetycar.services;

import com.safetycar.enums.PolicyStatuses;
import com.safetycar.models.Offer;
import com.safetycar.models.Policy;
import com.safetycar.models.Status;
import com.safetycar.models.users.User;
import com.safetycar.services.contracts.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PolicyManagerImpl implements PolicyManager {
    public static final String CREATED_POLICY = "Created policy";
    public static final String CREATED_BY = "Created by ";
    public static final String ACTIVE = "active";
    public static final String UPDATED_PICTURE = "Updated Picture";

    private final PolicyService policyService;
    private final UserService userService;
    private final PolicyHistoryService historyService;
    private final MailService mailService;
    private final AuthorisationService authorisationService;
    private final StatusService statusService;

    @Autowired
    public PolicyManagerImpl(PolicyService policyService,
                             UserService userService,
                             PolicyHistoryService historyService,
                             MailService mailService,
                             AuthorisationService authorisationService,
                             StatusService statusService) {
        this.policyService = policyService;
        this.userService = userService;
        this.historyService = historyService;
        this.mailService = mailService;
        this.authorisationService = authorisationService;
        this.statusService = statusService;
    }

    @Override
    @Transactional
    public void adminUpdatePolicy(Policy policy, Status status, User agent, String message) {
        authorisationService.authorise(agent, policy);
        policy.setStatus(status);
        String action = "Changed status to " + status.getStatus();
        historyService.appendHistory(agent, policy, action, message);
        policyService.update(policy);
        mailService.sendPolicyStatusMail(policy);
    }

    @Override
    @Transactional
    public void createPolicy(Policy policy, Offer offer, User owner) {
        String message = CREATED_BY + owner.getUserDetails().getFullName();
        historyService.appendHistory(owner, policy, CREATED_POLICY, message);
        policyService.create(policy);
        owner.removeOffer(offer);
        owner.addPolicy(policy);
        userService.update(owner);
    }

    @Override
    @Transactional
    public void userUpdatePolicy(Policy policy, User owner) {
        String message = "Picture updated by" + owner.getUserDetails().getFullName();
        policy.setStatus(statusService.getOne(PolicyStatuses.PENDING.getId()));
        historyService.appendHistory(owner, policy, UPDATED_PICTURE, message);
        policyService.update(policy);
    }
}
