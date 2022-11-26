package com.safetycar.services.factories;

import com.safetycar.models.Policy;
import com.safetycar.repositories.filter.base.MapBasedSpecification;
import com.safetycar.repositories.filter.PolicySpec;

import java.util.Collection;
import java.util.Map;

public interface PolicySpecFactory {

    MapBasedSpecification<Policy> getPolicySpec(Collection<Policy> collection);

    MapBasedSpecification<Policy> getPolicySpec(Collection<Policy> collection,
                                               Map<String, String> filter);

    MapBasedSpecification<Policy> getPolicySpec();

}
