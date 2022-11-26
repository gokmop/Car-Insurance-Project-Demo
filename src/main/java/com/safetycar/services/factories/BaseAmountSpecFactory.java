package com.safetycar.services.factories;

import com.safetycar.models.BaseAmount;
import com.safetycar.repositories.filter.BaseAmountSpec;
import com.safetycar.repositories.filter.base.MapBasedSpecification;

import java.util.Map;

public interface BaseAmountSpecFactory {

    MapBasedSpecification<BaseAmount> getBaseAmountSpec(Map<String, String> filter);

    MapBasedSpecification<BaseAmount> getBaseAmountSpec();
}
