package com.safetycar.models;


import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "offers")
public class Offer {

    private static final int EXPIRATION = 60 * 48;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    private int id;

    @Column(name = "driver_age")
    private boolean aboveTwentyFive;

    @Column(name = "had_accidents")
    private boolean hadAccidents;

    @Column(name = "submission_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date submissionDate;

    @Column(name = "expiration_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date expirationDate;

    @Column(name = "premium")
    private BigDecimal premium;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private Car car;

    public Offer() {
        submissionDate = new Date(System.currentTimeMillis());
        expirationDate = calculateExpiryDate();
    }

    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));
        cal.add(Calendar.MINUTE, EXPIRATION);
        return cal.getTime();
    }

    public Offer(boolean aboveTwentyFive, boolean hadAccidents, BigDecimal premium) {
        super();
        this.aboveTwentyFive = aboveTwentyFive;
        this.hadAccidents = hadAccidents;
        this.premium = premium;

    }

    public String getExpirationDate() {
        return expirationDate.toString();
    }

    public Car getCar() {
        return car;
    }

    public int getId() {
        return id;
    }

    public boolean isAboveTwentyFive() {
        return aboveTwentyFive;
    }

    public boolean hadAccidents() {
        return hadAccidents;
    }

    public String getSubmissionDate() {
        return submissionDate.toString();
    }

    public BigDecimal getPremium() {
        return premium.setScale(2, RoundingMode.HALF_UP);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAboveTwentyFive(boolean aboveTwentyFive) {
        this.aboveTwentyFive = aboveTwentyFive;
    }

    public void setHadAccidents(boolean hadAccidents) {
        this.hadAccidents = hadAccidents;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return id == offer.id &&
                aboveTwentyFive == offer.aboveTwentyFive &&
                hadAccidents == offer.hadAccidents &&
                Objects.equals(submissionDate, offer.submissionDate) &&
                Objects.equals(premium, offer.premium);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, aboveTwentyFive, hadAccidents, submissionDate, premium);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", aboveTwentyFive=" + aboveTwentyFive +
                ", hadAccidents=" + hadAccidents +
                ", submissionDate=" + submissionDate +
                ", premium=" + premium +
                '}';
    }

}
