package com.safetycar.services.contracts;

import com.safetycar.models.Car;
import com.safetycar.services.contracts.base.GetAll;
import com.safetycar.services.contracts.base.GetOne;

public interface CarService extends GetAll<Car>, GetOne<Car,Integer> {

}
