package com.safetycar.services.contracts;

import com.safetycar.models.Model;
import com.safetycar.services.contracts.base.GetAll;
import com.safetycar.services.contracts.base.GetOne;

import java.util.Collection;
import java.util.List;

public interface ModelService extends GetAll<Model>, GetOne<Model, Integer> {
    Collection<Model> filter(int brandId);

    List<String> getModelNamesByBrandId(int id);
}
