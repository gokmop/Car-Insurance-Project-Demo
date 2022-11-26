package com.safetycar.repositories.filter;

import com.safetycar.models.Policy;
import com.safetycar.repositories.filter.base.BaseMapSpecWithCollection;

import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.safetycar.repositories.filter.UserSpec.STATUS;
import static com.safetycar.util.Constants.BaseAmountConstants.CAPACITY;
import static com.safetycar.util.Constants.Views.QueryConstants.*;
import static com.safetycar.web.controllers.mvc.MvcPolicyController.OFFER;
import static com.safetycar.web.controllers.mvc.MvcPolicyController.USER;

public class PolicySpec extends BaseMapSpecWithCollection<Policy> {

    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String OFFER_ = "offer.";
    public static final String MODEL_YEAR_BRAND_ = "modelYearBrand.";
    public static final String MODEL_YEAR_BRAND = "modelYearBrand";
    public static final String BRAND_ = "brand.";
    public static final String STATUS_ = "status.";

    public PolicySpec() {
        super();
    }

    public PolicySpec(Map<String, String> specs) {
        super(specs);
    }

    public PolicySpec(Collection<Policy> userPolicies, Map<String, String> specs) {
        super(userPolicies, specs);
    }

    public PolicySpec(Collection<Policy> userPolicies) {
        super(userPolicies);
    }

    @Override
    public Predicate toPredicate(Root<Policy> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.distinct(true);
        return getPredicate(criteriaBuilder, root);
    }

    private Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root<Policy> root) {
        List<Predicate> predicates = new LinkedList<>();

        Collection<Policy> userPolicies = getCollection();


        Map<String, String> thisSpecs = getSpecs();
        if (thisSpecs.containsKey(USER)) {
            predicates.add(root.in(userPolicies));
        }

        for (Map.Entry<String, String> entry : thisSpecs.entrySet()) {
            switch (entry.getKey()) {
                case SUBMISSION_DATE: {
                    predicates.add(criteriaBuilder.equal(root.get(SUBMISSION_DATE), entry.getValue()));
                    break;
                }
                case START_DATE: {
                    predicates.add(criteriaBuilder.equal(root.get(START_DATE), entry.getValue()));
                    break;
                }
                case END_DATE: {
                    predicates.add(criteriaBuilder.equal(root.get(END_DATE), entry.getValue()));
                    break;
                }
                case STATUS: {
                    predicates.add(criteriaBuilder.like(root.get(STATUS).get(STATUS), "%" + entry.getValue() + "%"));
                    break;
                }
                case BRAND: {
                    Path<String> carBrandName = root.get(OFFER).get(CAR).get(MODEL_YEAR_BRAND).get(BRAND).get(NAME);
                    predicates.add(criteriaBuilder.like(carBrandName, "%" + entry.getValue() + "%"));
                    break;
                }
            }
        }
        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    @Override
    public String getSortParam(String sortParameter) {
        switch (sortParameter) {
            case SUBMISSION_DATE:
            case END_DATE:
            case START_DATE: {
                break;
            }
            case BRAND: {
                sortParameter = OFFER_ + CAR_ + MODEL_YEAR_BRAND_ + BRAND_ + NAME;
                break;
            }
            case PREMIUM: {
                sortParameter = OFFER_ + PREMIUM;
                break;
            }
            case CAPACITY: {
                sortParameter = OFFER_ + CAR_ + CAPACITY;
                break;
            }
            case FIRST_REGISTRATION: {
                sortParameter = OFFER_ + CAR_ + FIRST_REGISTRATION;
                break;
            }
            case STATUS: {
                sortParameter = STATUS_ + STATUS;
                break;
            }
            default: {
                sortParameter = ID;
            }
        }
        return sortParameter;
    }
}
