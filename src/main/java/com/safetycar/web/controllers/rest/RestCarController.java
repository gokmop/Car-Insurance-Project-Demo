package com.safetycar.web.controllers.rest;


import com.safetycar.models.Car;
import com.safetycar.services.contracts.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RequestMapping("/api/cars")
@RestController
public class RestCarController {

    private final CarService carService;

    @Autowired
    public RestCarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    Iterable<Car> showAllCars() {
        return carService.getAll();
    }

    @GetMapping("/{id}")
    Car showOne(@PathVariable int id) {
        Car car = carService.getOne(id);
        return car;
    }


}
