package com.safetycar.web.dto.user;

import com.safetycar.validation.ValidName;
import com.safetycar.validation.ValidTelephone;

import javax.validation.constraints.NotBlank;

import java.util.Objects;

import static com.safetycar.util.Constants.ValidationConstants.*;

public class CreateDetailsDto {

    public static final String INVALID_FIRST_NAME = "Invalid first name.";
    public static final String INVALID_LAST_NAME = "Invalid last name.";
    int id;

    @NotBlank(message = THIS_FIELD_CANNOT_BE_BLANK)
    private String address;

    @NotBlank(message = THIS_FIELD_CANNOT_BE_BLANK)
    @ValidTelephone(regexp = TELEPHONE_REGEX,
            message = PLEASE_PROVIDE_A_VALID_PHONE)
    private String telephone;

    @NotBlank(message = THIS_FIELD_CANNOT_BE_BLANK)
    @ValidName(regexp = NAME_REGEX,
            message = INVALID_FIRST_NAME)
    private String firstName;

    @NotBlank(message = THIS_FIELD_CANNOT_BE_BLANK)
    @ValidName(regexp = NAME_REGEX,
            message = INVALID_LAST_NAME)
    private String lastName;

    public CreateDetailsDto() {
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CreateDetailsDto{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateDetailsDto that = (CreateDetailsDto) o;
        return id == that.id &&
                Objects.equals(address, that.address) &&
                Objects.equals(telephone, that.telephone) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, telephone, firstName, lastName);
    }
}
