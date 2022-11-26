package com.safetycar.repositories.filter;

import com.safetycar.models.BaseAmount;
import com.safetycar.repositories.filter.base.BaseMapSpec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.safetycar.util.Constants.BaseAmountConstants.*;
import static com.safetycar.util.Constants.Views.QueryConstants.ID;

public class BaseAmountSpec extends BaseMapSpec<BaseAmount> {

//    public static final BaseAmountSpecFactory SPEC_FACTORY = new BaseAmountSpecFactoryImpl();

    public BaseAmountSpec() {
        super();
    }

    public BaseAmountSpec(Map<String, String> specs) {
        super(specs);
    }

    @Override
    public Predicate toPredicate(
            Root<BaseAmount> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder
    ) {
        query.distinct(true);
        return getPredicate(criteriaBuilder, root);
    }

    @Override
    public String getSortParam(String param) {
        return ID;
    }

    private Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root<BaseAmount> root) {
        List<Predicate> predicates = new LinkedList<>();
        Map<String, String> thisSpecs = getSpecs();
        for (Map.Entry<String, String> entry : thisSpecs.entrySet()) {
            switch (entry.getKey()) {
                case CAPACITY: {
                    predicates.add(criteriaBuilder
                            .lessThanOrEqualTo(root.get(CC_MIN), entry.getValue()));
                    predicates.add(criteriaBuilder
                            .greaterThanOrEqualTo(root.get(CC_MAX), entry.getValue()));
                    break;
                }
                case CAR_AGE: {
                    predicates.add(criteriaBuilder
                            .lessThanOrEqualTo(root.get(CAR_AGE_MIN), entry.getValue()));
                    predicates.add(criteriaBuilder
                            .greaterThanOrEqualTo(root.get(CAR_AGE_MAX), entry.getValue()));
                    break;
                }
            }
        }
        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

}
