package com.safetycar.services.contracts;

import com.safetycar.models.Policy;
import com.safetycar.services.contracts.base.CreateService;
import com.safetycar.services.contracts.base.GetAll;
import com.safetycar.services.contracts.base.GetOne;
import com.safetycar.services.contracts.base.UpdateService;

import java.util.Collection;
import java.util.Map;

public interface PolicyService extends GetOne<Policy,
        Integer>, GetAll<Policy>,
        CreateService<Policy>,
        UpdateService<Policy> {

    Collection<Policy> searchMyPolicies(Collection<Policy> collection, Map<String, String> search);
}
