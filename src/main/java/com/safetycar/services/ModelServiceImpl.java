package com.safetycar.services;

import com.safetycar.repositories.ModelRepository;
import com.safetycar.models.Model;
import com.safetycar.services.contracts.ModelService;
import com.safetycar.services.contracts.base.GetService;
import com.safetycar.services.contracts.base.GetServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ModelServiceImpl extends GetServiceBase implements ModelService, GetService {

    private final ModelRepository modelRepository;

    @Autowired
    public ModelServiceImpl(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public List<Model> getAll() {
        return modelRepository.findAll();
    }

    @Override
    public Model getOne(Integer id) {
        return get(modelRepository, id, Model.class);
    }

    @Override
    public List<Model> filter(int brandId) {
        return modelRepository.findAllDistinctByBrand_IdOrderByNameAscYearAsc(brandId);
    }

    @Override
    public List<String> getModelNamesByBrandId(int id) {
        List<String> modelsList = new ArrayList<>();
        Collection<Model> modelsCollection = modelRepository.findAllDistinctByBrand_IdOrderByNameAscYearAsc(id);

        for (Model model : modelsCollection) {
            modelsList.add(model.getName());
        }
        return modelsList;
    }
}
