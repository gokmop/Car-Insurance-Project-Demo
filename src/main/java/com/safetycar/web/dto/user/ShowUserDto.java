package com.safetycar.web.dto.user;

import java.math.BigDecimal;
import java.util.Objects;

public class ShowUserDto {

    private int userId;
    private String fullName;
    private int simulatedOffers;
    private int approvedCount;
    private int pendingCount;
    private BigDecimal approvedNet;
    private BigDecimal pendingNet;

    public ShowUserDto() {
    }

    public BigDecimal getApprovedPoliciesNet() {
        return approvedNet;
    }

    public BigDecimal getPendingPoliciesNet() {
        return pendingNet;
    }

    public int getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public int getSimulatedOffers() {
        return simulatedOffers;
    }

    public int getApprovedNCount() {
        return approvedCount;
    }

    public int getPendingCount() {
        return pendingCount;
    }

    public BigDecimal getApprovedNet() {
        return approvedNet;
    }

    public BigDecimal getPendingNet() {
        return pendingNet;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSimulatedOffers(int simulatedOffers) {
        this.simulatedOffers = simulatedOffers;
    }

    public void setApprovedCount(int approvedCount) {
        this.approvedCount = approvedCount;
    }

    public void setPendingCount(int pendingCount) {
        this.pendingCount = pendingCount;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setApprovedNet(BigDecimal approvedNet) {
        this.approvedNet = approvedNet;
    }

    public void setPendingNet(BigDecimal pendingNet) {
        this.pendingNet = pendingNet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowUserDto that = (ShowUserDto) o;
        return userId == that.userId &&
                simulatedOffers == that.simulatedOffers &&
                approvedCount == that.approvedCount &&
                pendingCount == that.pendingCount &&
                Objects.equals(fullName, that.fullName) &&
                Objects.equals(approvedNet, that.approvedNet) &&
                Objects.equals(pendingNet, that.pendingNet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, fullName, simulatedOffers, approvedCount, pendingCount, approvedNet, pendingNet);
    }
}
