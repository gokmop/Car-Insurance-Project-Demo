package com.safetycar.services.factories;

import com.safetycar.models.BaseAmount;
import com.safetycar.repositories.filter.BaseAmountSpec;
import com.safetycar.repositories.filter.base.MapBasedSpecification;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BaseAmountSpecFactoryImpl implements BaseAmountSpecFactory {

    public MapBasedSpecification<BaseAmount> getBaseAmountSpec(Map<String, String> map) {
        return new BaseAmountSpec(map);
    }

    public MapBasedSpecification<BaseAmount> getBaseAmountSpec() {
        return new BaseAmountSpec();
    }
}
