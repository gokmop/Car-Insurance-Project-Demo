package com.safetycar.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cars_models")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    private int id;

    @Column(name = "model_year")
    private int year;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(name = "model_name")
    private String name;

    public Model() {
    }

    public int getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public String modelAndYear() {
        return name + " " + year;
    }

    public String brandModelYear() {
        return brand + " " + name + " " + year;
    }

    public Brand getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return id == model.id &&
                year == model.year &&
                Objects.equals(brand, model.brand) &&
                Objects.equals(name, model.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, year, brand, name) * 13;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", year=" + year +
                ", brand=" + brand +
                ", name='" + name + '\'' +
                '}';
    }
}
