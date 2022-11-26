package com.safetycar.services;

import com.safetycar.repositories.BrandRepository;
import com.safetycar.models.Brand;
import com.safetycar.services.contracts.BrandService;
import com.safetycar.services.contracts.base.GetService;
import com.safetycar.services.contracts.base.GetServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BrandServiceImpl extends GetServiceBase implements BrandService, GetService {

    private final BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<Brand> getAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getOne(Integer id) {
        return get(brandRepository, id, Brand.class);
    }

}
