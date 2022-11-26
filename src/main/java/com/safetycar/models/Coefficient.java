package com.safetycar.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "coefficient")
public class Coefficient {

    @Column(name = "coefficient_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "accident_risk")
    private double accidentRisk;

    @Column(name = "age_risk")
    private double ageRisk;

    @Column(name = "tax")
    private double tax;

    public Coefficient() {
    }

    public int getId() {
        return id;
    }

    public double getAccidentRisk() {
        return accidentRisk;
    }

    public double getAgeRisk() {
        return ageRisk;
    }

    public double getTax() {
        return tax;
    }

    public void setAccidentRisk(double accidentRisk) {
        this.accidentRisk = accidentRisk;
    }

    public void setAgeRisk(double ageRisk) {
        this.ageRisk = ageRisk;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coefficient that = (Coefficient) o;
        return id == that.id &&
                Double.compare(that.accidentRisk, accidentRisk) == 0 &&
                Double.compare(that.ageRisk, ageRisk) == 0 &&
                Double.compare(that.tax, tax) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accidentRisk, ageRisk, tax);
    }

    @Override
    public String toString() {
        return "Coefficient{" +
                "id=" + id +
                ", accidentRisk=" + accidentRisk +
                ", ageRisk=" + ageRisk +
                ", tax=" + tax +
                '}';
    }
}
