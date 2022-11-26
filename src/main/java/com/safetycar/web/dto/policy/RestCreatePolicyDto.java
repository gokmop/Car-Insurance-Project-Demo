package com.safetycar.web.dto.policy;

import com.safetycar.validation.ValidStartDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class RestCreatePolicyDto {
    public static final int DAYS_AHEAD = 60;

    @NotNull(message = "UserId can not be null")
    private int UserId;
    @NotNull(message = "OfferId not be null")
    private int OfferId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Can not be null")
    @ValidStartDate(value = DAYS_AHEAD, message = "Policy start date should be from today to 60 days ahead!")
    private LocalDate startDate;

    public RestCreatePolicyDto() {
    }

    public int getUserId() {
        return UserId;
    }

    public int getOfferId() {
        return OfferId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public void setOfferId(int offerId) {
        OfferId = offerId;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
