package com.safetycar.web.dto.policy;


import com.safetycar.validation.ValidFile;
import com.safetycar.validation.ValidStartDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.safetycar.enums.AllowedContent.*;

public class CreatePolicyDto {

    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final int DAYS_AHEAD = 60;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Can not be null")
    @ValidStartDate(value = DAYS_AHEAD, message = "Policy start date should be from today to 60 days ahead!")
    private LocalDate startDate;

    @ValidFile(value = {GIF, JPEG, PNG}, message = "Invalid file type.")
    private MultipartFile file;

    private int updatePolicyId;

    public CreatePolicyDto() {
    }

    public int getUpdatePolicyId() {
        return updatePolicyId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public LocalDate getStartDate() {
        return startDate;
    }


    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }


    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public void setUpdatePolicyId(int updatePolicyId) {
        this.updatePolicyId = updatePolicyId;
    }
}
