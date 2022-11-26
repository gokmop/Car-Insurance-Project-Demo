package com.safetycar.web.dto.user;

import com.safetycar.web.dto.GetSearchParameters;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.safetycar.repositories.filter.UserSpec.*;
import static com.safetycar.util.Constants.Views.QueryConstants.DESC_SORT;
import static com.safetycar.util.Constants.Views.QueryConstants.SORT_PARAMETER;

public class SearchUsersDto implements GetSearchParameters {

    private String fullName;
    private String policyStatus;
    private String desc;
    private String sortParam;

    public SearchUsersDto() {
    }

    public String getFullName() {
        return fullName;
    }

    public String getPolicyStatus() {
        return policyStatus;
    }

    public String getDesc() {
        return desc;
    }

    public String getSortParam() {
        return sortParam;
    }

    public void setFullName(String lastName) {
        this.fullName = lastName;
    }

    public void setPolicyStatus(String policyStatus) {
        this.policyStatus = policyStatus;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setSortParam(String sortParam) {
        this.sortParam = sortParam;
    }

    @Override
    public Map<String, String> getSearchParams() {
        Optional<String> search = Optional.ofNullable(fullName);
        Optional<String> status = Optional.ofNullable(policyStatus);
        Optional<String> sort = Optional.ofNullable(desc);
        Optional<String> sortParameter = Optional.ofNullable(sortParam);
        Map<String, String> filters = new HashMap<>();

        search.ifPresent(s -> filters.put(FULL_NAME, s));
        status.ifPresent(s -> {
            if (!s.isEmpty()) {
                filters.put(POLICY_STATUS, s);
            }
        });
        sort.ifPresent(s -> filters.put(DESC_SORT, s));
        sortParameter.ifPresent(s -> filters.put(SORT_PARAMETER, s));
        return filters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchUsersDto that = (SearchUsersDto) o;
        return Objects.equals(fullName, that.fullName) &&
                Objects.equals(policyStatus, that.policyStatus) &&
                Objects.equals(desc, that.desc) &&
                Objects.equals(sortParam, that.sortParam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, policyStatus, desc, sortParam);
    }
}
