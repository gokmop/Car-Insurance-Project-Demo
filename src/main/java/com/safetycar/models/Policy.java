package com.safetycar.models;

import com.safetycar.models.users.User;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "policies")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "username")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "offer_id", referencedColumnName = "offer_id")
    private Offer offer;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "submission_date")
    private Date submissionDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "registration_certificate_img_id")
    private Image image;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "policy_history_join_table",
            joinColumns = @JoinColumn(name = "policy_id"),
            inverseJoinColumns = @JoinColumn(name = "history_id"))
    private Set<History> histories;

    public Policy() {
        submissionDate = new Date(System.currentTimeMillis());
    }

    public Status getStatus() {
        return status;
    }

    public void addHistory(History history) {
        if (histories == null) histories = new HashSet<>();
        histories.add(history);
    }

    public Set<History> getHistories() {
        if (histories == null) return new HashSet<>();
        return new HashSet<>(histories);
    }

    public int getId() {
        return id;
    }

    public User getOwner() {

        return owner;
    }

    public Car getCar() {
        return car;
    }

    public Offer getOffer() {
        return offer;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Image getImage() {
        return image;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOwner(User owner) {
        submissionDate = new Date(System.currentTimeMillis());
        this.owner = owner;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public void setStartDate(Date startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.YEAR, 1);
        this.endDate = calendar.getTime();
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setHistories(Set<History> histories) {
        this.histories = histories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Policy policy = (Policy) o;
        return id == policy.id &&
                Objects.equals(owner, policy.owner) &&
                Objects.equals(car, policy.car) &&
                Objects.equals(offer, policy.offer) &&
                Objects.equals(startDate.toString(), policy.startDate.toString()) &&
                Objects.equals(endDate.toString(), policy.endDate.toString()) &&
                Objects.equals(submissionDate.toString(), policy.submissionDate.toString()) &&
                Objects.equals(image, policy.image) &&
                Objects.equals(status, policy.status) &&
                Objects.equals(histories, policy.histories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, car, offer, startDate, endDate, submissionDate, image, status, histories);
    }

    @Override
    public String toString() {
        return "Policy{" +
                "id=" + id +
                ", owner=" + owner +
                ", car=" + car +
                ", offer=" + offer +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", submissionDate=" + submissionDate +
                ", image=" + image +
                ", histories=" + histories +
                ", status=" + status +
                '}';
    }
}
