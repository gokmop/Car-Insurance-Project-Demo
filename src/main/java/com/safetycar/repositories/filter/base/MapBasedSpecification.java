package com.safetycar.repositories.filter.base;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public interface MapBasedSpecification<T> extends Specification<T> {

    Map<String, String> getSpecs();

    void addSpec(String attribute, String value);

    void removeSpec(String attribute);

    Sort getSort();

}
