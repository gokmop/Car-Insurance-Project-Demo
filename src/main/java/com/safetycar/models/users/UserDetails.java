package com.safetycar.models.users;

import com.safetycar.models.Offer;
import com.safetycar.models.Policy;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.safetycar.util.Constants.Views.QueryConstants.*;

@Entity
@Table(name = "user_details")
public class UserDetails {

    @Id
    @Column(name = "email")
    private String userName;

    @Column(name = "details_id")
    private Integer integerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "active")
    private boolean active;

    @Formula(value = NET_PREMIUM_APPROVED)
    private BigDecimal approvedPoliciesNet;

    @Formula(value = NET_PREMIUM_PENDING)
    private BigDecimal pendingPoliciesNet;

    @Formula(value = COUNT_PREMIUM_APPROVED)
    private int approvedPoliciesCount;

    @Formula(value = COUNT_PREMIUM_PENDING)
    private int pendingPoliciesCount;

    @Formula(value = COUNT_OFFERS)
    private int offersCount;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_details_offers",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "offer_id")
    )
    private final Set<Offer> offers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_details_policies",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "policy_id")
    )
    private final Set<Policy> policies = new HashSet<>();

    public UserDetails() {
    }

    public int getOffersCount() {
        return offersCount;
    }

    public int getApprovedPoliciesCount() {
        return approvedPoliciesCount;
    }

    public int getPendingPoliciesCount() {
        return pendingPoliciesCount;
    }

    public BigDecimal getPendingPoliciesNet() {
        if (pendingPoliciesNet == null) return BigDecimal.ZERO;
        return pendingPoliciesNet.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getApprovedPoliciesNet() {
        if (approvedPoliciesNet == null) return BigDecimal.ZERO;
        return approvedPoliciesNet.setScale(2, RoundingMode.HALF_UP);
    }

    public Set<Policy> getPolicies() {
        return policies;
    }

    public Set<Offer> getOffers() {
        return new HashSet<>(offers);
    }

    public void addOffer(Offer offer) {
        offers.add(offer);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void removeOffer(Offer offer) {
        offers.remove(offer);
    }

    public void addPolicy(Policy policy) {
        policies.add(policy);
    }

    public void removePolicy(Policy policy) {
        policies.remove(policy);
    }

    public boolean isActive() {
        return active;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getAddress() {
        return address;
    }

    public String getTelephone() {
        return telephone;
    }

    public Integer getIntegerId() {
        return integerId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setIntegerId(Integer integerId) {
        this.integerId = integerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetails that = (UserDetails) o;
        return Objects.equals(userName, that.userName) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, firstName, lastName);
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}
