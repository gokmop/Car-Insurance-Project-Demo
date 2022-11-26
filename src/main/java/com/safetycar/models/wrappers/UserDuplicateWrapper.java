package com.safetycar.models.wrappers;
import com.safetycar.models.users.UserDetails;

public class UserDuplicateWrapper {

    private String email;

    private String telephone;

    private String address;

    private String firstName;

    private String lastName;

    public UserDuplicateWrapper() {
    }

    public UserDuplicateWrapper(String email,
                                String telephone,
                                String address,
                                String firstName,
                                String lastName) {
        this.email = email;
        this.telephone = telephone;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean duplicate(UserDetails userDetails) {
        if (!userDetails.isActive()){
            return false;
        }
        boolean telephoneEquals = userDetails.getTelephone().equals(this.telephone);
        String firstName = userDetails.getFirstName();
        String lastName = userDetails.getLastName();
        String address = userDetails.getAddress();
        boolean firstNameEquals = firstName.equals(this.firstName);
        boolean lastNameEquals = lastName.equals(this.lastName);
        boolean addressEquals = address.equals(this.address);
        return telephoneEquals || (firstNameEquals && lastNameEquals && addressEquals);
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
