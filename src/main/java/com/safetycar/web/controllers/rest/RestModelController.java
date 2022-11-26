package com.safetycar.web.controllers.rest;

import com.safetycar.models.Model;
import com.safetycar.services.contracts.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RequestMapping("/api/models")
@RestController
public class RestModelController {

    private final ModelService modelService;

    @Autowired
    public RestModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping
    Iterable<Model> showAllModels() {
        return modelService.getAll();
    }

    @GetMapping("{id}")
    Model showOne(@PathVariable int id) {
        return modelService.getOne(id);
    }

    @GetMapping("/search/{brandId}")
    public Collection<Model> searchAllModelsByBrand(@PathVariable int brandId){
        return modelService.filter(brandId);
    }


    @GetMapping("/list/")
    public Collection<Model> showAllModelsByBrandId(@RequestParam int brandId){
        return modelService.filter(brandId);
    }
}

