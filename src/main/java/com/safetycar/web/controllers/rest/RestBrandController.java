package com.safetycar.web.controllers.rest;

import com.safetycar.models.Brand;
import com.safetycar.services.contracts.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequestMapping("/api/brands")
@RestController
public class RestBrandController {

    private final BrandService brandService;

    @Autowired
    public RestBrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    Iterable<Brand> showAllBrands() {
        return brandService.getAll();
    }

    @GetMapping("{id}")
    Brand showOne(@PathVariable int id) {
        return brandService.getOne(id);
    }
}

