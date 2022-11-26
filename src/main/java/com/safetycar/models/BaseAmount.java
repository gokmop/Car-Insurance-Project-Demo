package com.safetycar.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "base_amount_brackets")
public class BaseAmount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "base_amount_id")
    private int id;

    @Column(name = "cc_min")
    private int ccMin;

    @Column(name = "cc_max")
    private int ccMax;

    @Column(name = "car_age_min")
    private int carAgeMin;

    @Column(name = "car_age_max")
    private int carAgeMax;

    @Column(name = "base_amount")
    private BigDecimal baseAmount;

    public BaseAmount() {
    }

    public int getId() {
        return id;
    }

    public int getCcMin() {
        return ccMin;
    }

    public int getCcMax() {
        return ccMax;
    }

    public int getCarAgeMin() {
        return carAgeMin;
    }

    public int getCarAgeMax() {
        return carAgeMax;
    }

    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCcMin(int ccMin) {
        this.ccMin = ccMin;
    }

    public void setCcMax(int ccMax) {
        this.ccMax = ccMax;
    }

    public void setCarAgeMin(int carAgeMin) {
        this.carAgeMin = carAgeMin;
    }

    public void setCarAgeMax(int carAgeMax) {
        this.carAgeMax = carAgeMax;
    }

    public void setBaseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseAmount that = (BaseAmount) o;
        return id == that.id &&
                ccMin == that.ccMin &&
                ccMax == that.ccMax &&
                carAgeMin == that.carAgeMin &&
                carAgeMax == that.carAgeMax &&
                Objects.equals(baseAmount, that.baseAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ccMin, ccMax, carAgeMin, carAgeMax, baseAmount);
    }

    @Override
    public String toString() {
        return "BaseAmount{" +
                "id=" + id +
                ", ccMin=" + ccMin +
                ", ccMax=" + ccMax +
                ", carAgeMin=" + carAgeMin +
                ", carAgeMax=" + carAgeMax +
                ", baseAmount=" + baseAmount +
                '}';
    }

}
