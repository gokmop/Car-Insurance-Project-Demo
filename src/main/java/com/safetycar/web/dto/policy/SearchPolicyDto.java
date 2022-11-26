package com.safetycar.web.dto.policy;

import com.safetycar.web.dto.GetSearchParameters;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.safetycar.repositories.filter.UserSpec.STATUS;
import static com.safetycar.util.Constants.Views.QueryConstants.*;
import static com.safetycar.web.controllers.mvc.MvcPolicyController.USER;

public class SearchPolicyDto implements GetSearchParameters {

    private String status;
    private String brand;
    private String sortParam;
    private String desc;

    private int userId;

    public SearchPolicyDto() {
    }

    public int getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public String getBrand() {
        return brand;
    }

    public String getSortParam() {
        return sortParam;
    }

    public String getDesc() {
        return desc;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setSortParam(String sortParam) {
        this.sortParam = sortParam;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public Map<String, String> getSearchParams() {
        Map<String, String> search = new HashMap<>();
        Optional<String> status = Optional.ofNullable(this.status);
        Optional<String> desc = Optional.ofNullable(this.desc);
        Optional<String> sortParam = Optional.ofNullable(this.sortParam);
        Optional<String> brand = Optional.ofNullable(this.brand);
        status.ifPresent(s -> search.put(STATUS, s));
        desc.ifPresent(s -> search.put(DESC_SORT, s));
        sortParam.ifPresent(s -> search.put(SORT_PARAMETER, s));
        brand.ifPresent(s -> search.put(BRAND, s));
        return search;
    }
    public Map<String, String> getSearchParams(String additionalParam) {
        Map<String, String> searchParams = getSearchParams();
        searchParams.put(additionalParam, additionalParam);
        return searchParams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchPolicyDto that = (SearchPolicyDto) o;
        return userId == that.userId &&
                Objects.equals(status, that.status) &&
                Objects.equals(brand, that.brand) &&
                Objects.equals(sortParam, that.sortParam) &&
                Objects.equals(desc, that.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, brand, sortParam, desc, userId);
    }
}
