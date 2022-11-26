package com.safetycar.repositories.filter;

import com.safetycar.models.Policy;
import com.safetycar.models.Role;
import com.safetycar.models.users.User;
import com.safetycar.models.users.UserDetails;
import com.safetycar.repositories.filter.base.BaseMapSpec;

import javax.persistence.criteria.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.safetycar.models.users.User.USERNAME;
import static com.safetycar.util.Constants.Views.QueryConstants.*;
import static com.safetycar.web.controllers.mvc.MvcUserController.USER_DETAILS;

public class UserSpec extends BaseMapSpec<User> {

    public static final String PENDING_POLICIES_NET = "pendingPoliciesNet";
    public static final String APPROVED_POLICIES_NET = "approvedPoliciesNet";
    public static final String PENDING_POLICIES_COUNT = "pendingPoliciesCount";
    public static final String APPROVED_POLICIES_COUNT = "approvedPoliciesCount";
    public static final String POLICY_STATUS = "policyStatus";
    public static final String ID = "integerId";
    public static final String ACTIVE = "active";
    public static final String INACTIVE = "inactive";
    public static final String PENDING = "pending";
    public static final String STATUS = "status";
    public static final String POLICIES = "policies";
    public static final String OFFERS_COUNT = "offersCount";
    public static final String REJECTED = "rejected";
    public static final String FULL_NAME = "fullName";
    public static final String TRUE = "true";
    public static final String USER_DETAILS_ = "userDetails.";
    public static final String OWNER = "owner";
    public static final String ROLES = "roles";
    public static final String AUTHORITY = "authority";
    public static final String ROLE_USER = "ROLE_USER";


    private boolean isAdmin;

    public UserSpec() {
    }

    public UserSpec(Map<String, String> specs) {
        super(specs);
    }

    @Override
    public Predicate toPredicate(Root<User> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        query.distinct(true);
        return getPredicate(criteriaBuilder, root, query);
    }

    private Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root<User> root, CriteriaQuery<?> query) {
        List<Predicate> predicates = new LinkedList<>();
        Map<String, String> thisSpecs = getSpecs();
        Join<User, UserDetails> detailsJoin = root.join(USER_DETAILS, JoinType.INNER);

        if (!isAdmin) {
            Join<User, Role> roleJoin = root.join(ROLES);
            predicates.add(criteriaBuilder.equal(roleJoin.get(AUTHORITY), ROLE_USER));
        }
        for (Map.Entry<String, String> entry : thisSpecs.entrySet()) {
            switch (entry.getKey()) {
                case FULL_NAME: {
                    Expression<String> concatSingleSpace = criteriaBuilder
                            .concat(detailsJoin.get(FIRST_NAME), " ");
                    Expression<String> fullNameConcat = criteriaBuilder
                            .concat(concatSingleSpace, detailsJoin.get(LAST_NAME));

                    predicates.add(criteriaBuilder.like(fullNameConcat, "%" + entry.getValue() + "%"));
                    break;
                }
                case ACTIVE: {
                    boolean value = entry.getValue().equals(TRUE);
                    predicates.add(criteriaBuilder.equal(detailsJoin.get(ACTIVE), value));
                    break;
                }
                case POLICY_STATUS: {
                    String status;
                    switch (entry.getValue()) {
                        case PENDING: {
                            status = PENDING;
                            break;
                        }
                        case INACTIVE: {
                            status = INACTIVE;
                            break;
                        }
                        case REJECTED: {
                            status = REJECTED;
                            break;
                        }
                        default: {
                            status = ACTIVE;
                            break;
                        }
                    }
                    Root<UserDetails> detailsRoot = query.from(UserDetails.class);
                    Join<UserDetails, Policy> detailsPolicyJoin = detailsRoot.join(POLICIES);
                    predicates.add(criteriaBuilder
                            .like(detailsPolicyJoin.get(STATUS).get(STATUS), "%" + status + "%"));
                    predicates.add(criteriaBuilder
                            .equal(detailsPolicyJoin.get(OWNER).get(USERNAME), detailsJoin.get(USERNAME)));
                }
            }
        }
        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    public String getSortParam(String sortParameter) {
        switch (sortParameter) {
            case PENDING_POLICIES_NET:
            case APPROVED_POLICIES_NET:
            case PENDING_POLICIES_COUNT:
            case APPROVED_POLICIES_COUNT:
            case OFFERS_COUNT:
                break;
            default: {
                sortParameter = ID;
            }
        }
        return USER_DETAILS_ + sortParameter;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
