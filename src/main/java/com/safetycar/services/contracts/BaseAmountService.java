package com.safetycar.services.contracts;

import com.safetycar.models.BaseAmount;

import java.util.Map;

public interface BaseAmountService {

    BaseAmount getBaseAmount(Map<String, String> spec);

}
