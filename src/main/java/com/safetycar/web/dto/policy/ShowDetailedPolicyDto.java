package com.safetycar.web.dto.policy;

import java.math.BigDecimal;
import java.util.Objects;

public class ShowDetailedPolicyDto {

    private int policyId;
    private int ownerId;
    private String ownerFirstName;
    private String ownerLastName;
    private String ownerTelephone;
    private String ownerAddress;
    private String carMake;
    private String carModel;
    private String carFirstRegistration;
    private int offerId;
    private boolean accidentsInPreviousYear;
    private boolean ownerAbove25Years;
    private BigDecimal policyPremium;
    private String submissionDate;
    private String startDate;
    private String endDate;

    public ShowDetailedPolicyDto() {
    }

    public int getPolicyId() {
        return policyId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public String getOwnerTelephone() {
        return ownerTelephone;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public String getCarMake() {
        return carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getCarFirstRegistration() {
        return carFirstRegistration;
    }

    public int getOfferId() {
        return offerId;
    }

    public boolean getAccidentsInPreviousYear() {
        return accidentsInPreviousYear;
    }

    public boolean getOwnerAbove25Years() {
        return ownerAbove25Years;
    }

    public BigDecimal getPolicyPremium() {
        return policyPremium;
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

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public void setOwnerTelephone(String ownerTelephone) {
        this.ownerTelephone = ownerTelephone;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setCarFirstRegistration(String carFirstRegistration) {
        this.carFirstRegistration = carFirstRegistration;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public void setAccidentsInPreviousYear(boolean accidentsInPreviousYear) {
        this.accidentsInPreviousYear = accidentsInPreviousYear;
    }

    public void setOwnerAbove25Years(boolean ownerAbove25Years) {
        this.ownerAbove25Years = ownerAbove25Years;
    }

    public void setPolicyPremium(BigDecimal policyPremium) {
        this.policyPremium = policyPremium;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowDetailedPolicyDto that = (ShowDetailedPolicyDto) o;
        return policyId == that.policyId &&
                ownerId == that.ownerId &&
                offerId == that.offerId &&
                accidentsInPreviousYear == that.accidentsInPreviousYear &&
                ownerAbove25Years == that.ownerAbove25Years &&
                Objects.equals(ownerFirstName, that.ownerFirstName) &&
                Objects.equals(ownerLastName, that.ownerLastName) &&
                Objects.equals(ownerTelephone, that.ownerTelephone) &&
                Objects.equals(ownerAddress, that.ownerAddress) &&
                Objects.equals(carMake, that.carMake) &&
                Objects.equals(carModel, that.carModel) &&
                Objects.equals(carFirstRegistration, that.carFirstRegistration) &&
                Objects.equals(policyPremium, that.policyPremium) &&
                Objects.equals(submissionDate, that.submissionDate) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(policyId, ownerId, ownerFirstName, ownerLastName, ownerTelephone, ownerAddress, carMake, carModel, carFirstRegistration, offerId, accidentsInPreviousYear, ownerAbove25Years, policyPremium, submissionDate, startDate, endDate);
    }
}
