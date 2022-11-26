package com.safetycar.web.dto.mappers;

import com.safetycar.models.Car;
import com.safetycar.models.Model;
import com.safetycar.services.contracts.ModelService;
import com.safetycar.web.dto.offer.CalculateOfferDtoForRest;
import com.safetycar.web.dto.offer.CreateOfferDtoForRest;
import com.safetycar.web.dto.offer.OfferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CarMapper {

    private final ModelService modelService;


    @Autowired
    public CarMapper(ModelService modelService) {
        this.modelService = modelService;
    }

    public Car fromDto(OfferDto dto) {
        int capacity = Integer.parseInt(dto.getCapacity());
        Model model = modelService.getOne(dto.getModelId());
        LocalDate firstRegistration = dto.getDateRegistered();
        Car car = new Car();
        car.setCapacity(capacity);
        car.setFirstRegistration(firstRegistration);
        car.setModelYearAndBrand(model);
        return car;
    }

    public Car fromCalculateOfferDto(CalculateOfferDtoForRest dto){
        int capacity = dto.getCapacity();
        Model model = modelService.getOne(dto.getModelId());
        LocalDate firstRegistration = dto.getDateRegistered();
        Car car = new Car();
        car.setCapacity(capacity);
        car.setFirstRegistration(firstRegistration);
        car.setModelYearAndBrand(model);
        return car;
    }

    public Car fromDto(CreateOfferDtoForRest dto) {
        int capacity = dto.getCapacity();
        Model model = modelService.getOne(dto.getModelId());
        LocalDate firstRegistration = dto.getDateRegistered();
        Car car = new Car();
        car.setCapacity(capacity);
        car.setFirstRegistration(firstRegistration);
        car.setModelYearAndBrand(model);
        return car;
    }
}
