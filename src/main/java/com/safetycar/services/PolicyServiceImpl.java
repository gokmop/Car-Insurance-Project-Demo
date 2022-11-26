package com.safetycar.services;

import com.safetycar.models.Policy;
import com.safetycar.repositories.PolicyRepository;
import com.safetycar.repositories.filter.base.MapBasedSpecification;
import com.safetycar.services.contracts.PolicyService;
import com.safetycar.services.contracts.base.GetService;
import com.safetycar.services.contracts.base.GetServiceBase;
import com.safetycar.services.factories.PolicySpecFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.safetycar.util.Constants.Views.QueryConstants.ID;
import static com.safetycar.util.Constants.Views.QueryConstants.SORT_PARAMETER;

@Service
public class PolicyServiceImpl extends GetServiceBase implements PolicyService, GetService {


    private final PolicyRepository policyRepository;
    private final PolicySpecFactory specFactory;


    @Autowired
    public PolicyServiceImpl(PolicyRepository policyRepository,
                             PolicySpecFactory specFactory) {
        this.policyRepository = policyRepository;
        this.specFactory = specFactory;
    }

    @Override
    public void create(Policy policy) {
        policyRepository.save(policy);
    }

    @Override
    public List<Policy> getAll() {
        MapBasedSpecification<Policy> spec = specFactory.getPolicySpec();
        spec.addSpec(SORT_PARAMETER, ID);
        Sort sort = spec.getSort();
        return policyRepository.findAll(spec, sort);
    }

    @Override
    public Policy getOne(Integer id) {
        return get(policyRepository, id, Policy.class);
    }

    @Override
    public void update(Policy policy) {
        policyRepository.save(policy);
    }

    @Override
    public List<Policy> searchMyPolicies(Collection<Policy> collection, Map<String, String> search) {
        MapBasedSpecification<Policy> spec = specFactory.getPolicySpec(collection, search);
        Sort sort = spec.getSort();
        return policyRepository.findAll(spec, sort);
    }
}
