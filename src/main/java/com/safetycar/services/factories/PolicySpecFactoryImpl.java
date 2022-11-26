package com.safetycar.services.factories;

import com.safetycar.models.Policy;
import com.safetycar.repositories.filter.base.MapBasedSpecification;
import com.safetycar.repositories.filter.PolicySpec;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;


@Component
public class PolicySpecFactoryImpl implements PolicySpecFactory {

    @Override
    public MapBasedSpecification<Policy> getPolicySpec(Collection<Policy> collection) {
        return new PolicySpec(collection);
    }

    @Override
    public MapBasedSpecification<Policy> getPolicySpec(Collection<Policy> collection, Map<String, String> filter) {
        return new PolicySpec(collection, filter);
    }

    public MapBasedSpecification<Policy> getPolicySpec() {
        return new PolicySpec();
    }
}
