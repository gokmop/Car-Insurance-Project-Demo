package com.safetycar.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "car_model_id")
    private Model modelYearBrand;

    @Column(name = "cubic_capacity")
    private int capacity;

    @Column(name = "first_registration")
    private LocalDate firstRegistration;

    public Car() {
    }

    public int getId() {
        return id;
    }

    public Model getModelYearBrand() {
        return modelYearBrand;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getBrandName() {
        return modelYearBrand.getBrand().getName();
    }

    public String getModelName() {
        return modelYearBrand.getName();
    }

    public LocalDate getFirstRegistration() {
        return firstRegistration;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setModelYearAndBrand(Model modelYearAndBrand) {
        this.modelYearBrand = modelYearAndBrand;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setFirstRegistration(LocalDate firstRegistration) {
        this.firstRegistration = firstRegistration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return capacity == car.capacity &&
                Objects.equals(modelYearBrand, car.modelYearBrand) &&
                Objects.equals(firstRegistration, car.firstRegistration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modelYearBrand, capacity, firstRegistration);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", modelYearBrand=" + modelYearBrand +
                ", capacity=" + capacity +
                ", firstRegistration=" + firstRegistration +
                '}';
    }
}
