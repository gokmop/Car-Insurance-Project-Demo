package com.safetycar.repositories.filter.base;

import org.springframework.data.domain.Sort;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.Map;

import static com.safetycar.util.Constants.Views.QueryConstants.DESC_SORT;
import static com.safetycar.util.Constants.Views.QueryConstants.SORT_PARAMETER;

public abstract class BaseMapSpec<T> implements MapBasedSpecification<T> {

    private final Map<String, String> specs;

    protected BaseMapSpec() {
        specs = new HashMap<>();
    }

    protected BaseMapSpec(Map<String, String> specs) {
        this.specs = specs;
    }

    public abstract Predicate toPredicate(
            Root<T> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder
    );

    public abstract String getSortParam(String param);

    public final void addSpec(String attribute, String value) {
        specs.put(attribute, value);
    }

    public final void removeSpec(String attribute) {
        specs.remove(attribute);
    }

    public final Map<String, String> getSpecs() {
        return new HashMap<>(specs);
    }

    public final Sort.Direction getDirection() {
        if (this.specs.containsKey(DESC_SORT) && this.specs.get(DESC_SORT).equals(DESC_SORT)) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    public final Sort getSort() {
        if (!this.specs.containsKey(SORT_PARAMETER)) {
            return Sort.unsorted();
        }
        Sort.Direction direction = getDirection();
        String param = getSortParam(this.specs.get(SORT_PARAMETER));
        return Sort.by(direction, param);
    }
}
