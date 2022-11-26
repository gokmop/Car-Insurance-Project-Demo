package com.safetycar.models.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.safetycar.models.Offer;
import com.safetycar.models.Policy;
import com.safetycar.models.Role;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User {

    //field names must equal these constants for criteriaQuery
    public static final String USERNAME = "userName";

    @Id
    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "deleted")
    private boolean deleted;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL, CascadeType.REMOVE})
    @JoinColumn(name = "username")
    private Set<Role> roles;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL, CascadeType.REMOVE})
    @JoinColumn(name = "username")
    private UserDetails userDetails;

    public User() {
    }

    public void addOffer(Offer offer) {
        this.userDetails.addOffer(offer);
    }

    public void removeOffer(Offer offer) {
        this.userDetails.removeOffer(offer);
    }

    public void addPolicy(Policy policy) {
        this.userDetails.addPolicy(policy);
    }

    public void removePolicy(Policy policy) {
        this.userDetails.removePolicy(policy);
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isDisabled() {
        return !enabled;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Set<Role> getRoles() {
        return new HashSet<>(roles);
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    @JsonIgnore
    public Collection<Offer> getOffers() {
        return new LinkedList<>(userDetails.getOffers());
    }

    @JsonIgnore
    public Collection<Policy> getPolicies() {
        return new LinkedList<>(userDetails.getPolicies());
    }

    public boolean isAdmin() {
        return roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_AGENT"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return enabled == user.enabled &&
                deleted == user.deleted &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(roles, user.roles) &&
                Objects.equals(userDetails, user.userDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, enabled, deleted, roles, userDetails);
    }

    @Override
    public String toString() {
        return userName;
    }
    //principal is this user
}
