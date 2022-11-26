package com.safetycar.services.contracts;

import com.safetycar.models.Brand;
import com.safetycar.services.contracts.base.GetAll;
import com.safetycar.services.contracts.base.GetOne;

public interface BrandService extends GetAll<Brand>, GetOne<Brand,Integer> {

}
