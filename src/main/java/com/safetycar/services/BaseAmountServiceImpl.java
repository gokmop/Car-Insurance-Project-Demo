package com.safetycar.services;

import com.safetycar.repositories.BaseAmountRepository;
import com.safetycar.repositories.filter.base.MapBasedSpecification;
import com.safetycar.models.BaseAmount;
import com.safetycar.services.contracts.BaseAmountService;
import com.safetycar.services.contracts.base.FindService;
import com.safetycar.services.contracts.base.FindServiceBase;
import com.safetycar.services.contracts.base.GetServiceBase;
import com.safetycar.services.factories.BaseAmountSpecFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.safetycar.util.Constants.BaseAmountConstants.*;

@Service
public class BaseAmountServiceImpl extends FindServiceBase implements BaseAmountService, FindService {

    private final BaseAmountRepository baseAmountRepository;
    private final BaseAmountSpecFactory specFactory;

    @Autowired
    public BaseAmountServiceImpl(BaseAmountRepository baseAmountRepository,
                                 BaseAmountSpecFactory specFactory) {
        this.baseAmountRepository = baseAmountRepository;
        this.specFactory = specFactory;
    }

    @Override
    public BaseAmount getBaseAmount(Map<String, String> filter) {
        MapBasedSpecification<BaseAmount> spec = specFactory.getBaseAmountSpec(filter);
        String message = String.format(NO_CALCULATOR_FOUND_FOR,
                filter.get(CAPACITY));
        return find(baseAmountRepository, spec, message);
    }

}
