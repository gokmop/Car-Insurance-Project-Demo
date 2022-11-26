package com.safetycar.web.dto.policy;

import com.safetycar.models.History;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

public class ShowPolicyDto {

    private int policyId;
    private int ownerId;
    private String carMake;
    private String carModel;
    private int offerId;
    private BigDecimal policyPremium;
    private String carFirstRegistration;
    private String submissionDate;
    private String startDate;
    private String endDate;
    private String status;
    private Set<History> historyList;


    public ShowPolicyDto() {
    }

    public Set<History> getHistoryList() {
        return historyList;
    }

    public int getPolicyId() {
        return policyId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getCarMake() {
        return carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public int getOfferId() {
        return offerId;
    }

    public String getCarFirstRegistration() {
        return carFirstRegistration;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public BigDecimal getPolicyPremium() {
        return policyPremium;
    }

    public String getStatus() {
        return status;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public void setCarFirstRegistration(String carFirstRegistration) {
        this.carFirstRegistration = carFirstRegistration;
    }

    public void setHistoryList(Set<History> historyList) {
        this.historyList = historyList;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setPolicyPremium(BigDecimal policyPremium) {
        this.policyPremium = policyPremium;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowPolicyDto that = (ShowPolicyDto) o;
        return policyId == that.policyId &&
                ownerId == that.ownerId &&
                offerId == that.offerId &&
                Objects.equals(carMake, that.carMake) &&
                Objects.equals(carModel, that.carModel) &&
                Objects.equals(policyPremium, that.policyPremium) &&
                Objects.equals(carFirstRegistration, that.carFirstRegistration) &&
                Objects.equals(submissionDate, that.submissionDate) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(status, that.status) &&
                Objects.equals(historyList, that.historyList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(policyId, ownerId, carMake, carModel, offerId, policyPremium, carFirstRegistration, submissionDate, startDate, endDate, status, historyList);
    }
}
