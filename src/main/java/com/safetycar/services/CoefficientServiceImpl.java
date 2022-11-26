package com.safetycar.services;

import com.safetycar.models.Coefficient;
import com.safetycar.repositories.CoefficientRepository;
import com.safetycar.services.contracts.CoefficientService;
import com.safetycar.services.contracts.base.GetService;
import com.safetycar.services.contracts.base.GetServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoefficientServiceImpl extends GetServiceBase implements CoefficientService, GetService {

    public static final int COEFFICIENT_ID = 1;

    private final CoefficientRepository coefficientRepository;


    @Autowired
    public CoefficientServiceImpl(CoefficientRepository coefficientRepository) {
        this.coefficientRepository = coefficientRepository;
    }

    @Override
    public Coefficient getCoefficient() {
        return get(coefficientRepository, COEFFICIENT_ID, Coefficient.class);
    }
}
