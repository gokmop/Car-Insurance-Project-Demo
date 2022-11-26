package com.safetycar.web.dto.user;

import com.safetycar.enums.LegalAges;
import com.safetycar.models.Offer;
import com.safetycar.validation.MinAge;
import com.safetycar.validation.PasswordMatches;
import com.sun.istack.Nullable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

import static com.safetycar.util.Constants.ValidationConstants.*;

@PasswordMatches(message = PLEASE_PROVIDE_MATCHING_PASSWORDS)
public class CreateUserDto {

    @NotBlank(message = THIS_FIELD_CANNOT_BE_BLANK)
    @Email(regexp = EMAIL_REGEX)
    private String email;

    @NotBlank(message = THIS_FIELD_CANNOT_BE_BLANK)
    @Size(min = 6, message = PASSWORD_MUST_BE_AT_LEAST_SIX_CHARACTERS_LONG)
    private String password;

    private String confirmationPassword;

    @NotNull(message = THIS_FIELD_CANNOT_BE_BLANK)
    @MinAge(value = LegalAges.MIN, message = USER_YOUNGER_THAN_LEGAL_YEARS_ERROR)
    @DateTimeFormat(pattern = LOCAL_DATE_FORMAT)
    private LocalDate birthDate;

    public CreateUserDto() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmationPassword(String confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
    }

}
