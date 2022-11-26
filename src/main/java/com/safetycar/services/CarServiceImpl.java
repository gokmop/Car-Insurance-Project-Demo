package com.safetycar.services;

import com.safetycar.models.Car;
import com.safetycar.repositories.CarRepository;
import com.safetycar.services.contracts.CarService;
import com.safetycar.services.contracts.base.GetService;
import com.safetycar.services.contracts.base.GetServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CarServiceImpl extends GetServiceBase implements CarService, GetService {

    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    @Override
    public Car getOne(Integer id) {
        return get(carRepository, id, Car.class);
    }

}
