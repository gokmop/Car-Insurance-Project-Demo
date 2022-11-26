package com.safetycar.repositories.filter;

import com.safetycar.models.Offer;
import com.safetycar.repositories.filter.base.BaseMapSpecWithCollection;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.*;

import static com.safetycar.util.Constants.BaseAmountConstants.CAPACITY;
import static com.safetycar.util.Constants.Views.QueryConstants.*;

public class OfferSpec extends BaseMapSpecWithCollection<Offer> {

//    public static final OfferSpecFactory SPEC_FACTORY = new OfferSpecFactoryImpl();

    public OfferSpec() {
        super();
    }

    public OfferSpec(Map<String, String> specs) {
        super(specs);
    }

    public OfferSpec(Collection<Offer> userOffers, Map<String, String> specs) {
        super(userOffers, specs);
    }

    public OfferSpec(Collection<Offer> userOffers) {
        super(userOffers);
    }

    @Override
    public Predicate toPredicate(Root<Offer> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        query.distinct(true);
        return getPredicate(criteriaBuilder, root);
    }

    private Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root<Offer> root) {
        List<Predicate> predicates = new LinkedList<>();
        Collection<Offer> userOffers = getCollection();
        predicates.add(root.in(userOffers));
        Map<String, String> thisSpecs = getSpecs();

        for (Map.Entry<String, String> entry : thisSpecs.entrySet()) {
            switch (entry.getKey()) {
                case SUBMISSION_DATE: {
                    predicates.add(criteriaBuilder.equal(root.get(SUBMISSION_DATE), entry.getValue()));
                    break;
                }
                case EXPIRATION_DATE: {
                    predicates.add(criteriaBuilder.equal(root.get(EXPIRATION_DATE), entry.getValue()));
                    break;
                }
                case BRAND: {
                    predicates.add(criteriaBuilder.equal(root.get(CAR).get(BRAND).get(NAME), entry.getValue()));
                    break;
                }
                case FIRST_REGISTRATION: {
                    predicates.add(criteriaBuilder.equal(root.get(CAR).get(FIRST_REGISTRATION), entry.getValue()));
                    break;
                }
            }
        }
        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    public String getSortParam(String sortParameter) {
        switch (sortParameter) {
            case SUBMISSION_DATE:
            case EXPIRATION_DATE:
            case BRAND:
            case PREMIUM:
                break;
            case CAPACITY:
                sortParameter = CAR_ + CAPACITY;
                break;
            case FIRST_REGISTRATION: {
                sortParameter = CAR_ + FIRST_REGISTRATION;
                break;
            }
            default: {
                sortParameter = ID;
            }
        }
        return sortParameter;
    }

}
