package com.safetycar.web.dto.policy;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class ManagePolicyDto {

    private String status;

    @NotNull
    private Integer statusId;

    @Size(min = 20, message = "Reason for status change must be at least 20 characters.")
    @NotBlank
    private String message;

    public ManagePolicyDto() {
    }

    public String getStatus() {
        return status;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManagePolicyDto that = (ManagePolicyDto) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(statusId, that.statusId) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, statusId, message);
    }
}
