package com.safetycar.web.dto.offer;

import com.safetycar.web.dto.GetSearchParameters;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.safetycar.util.Constants.Views.QueryConstants.*;

public class SearchOffersDto implements GetSearchParameters {

    private String premium;

    private String desc;

    private String sortParameter;

    private String submissionDate;

    private String expirationDate;

    public SearchOffersDto() {
    }

    public String getPremium() {
        return premium;
    }

    public String getDesc() {
        return desc;
    }

    public String getSortParameter() {
        return sortParameter;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setSortParameter(String sortParameter) {
        this.sortParameter = sortParameter;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public Map<String, String> getSearchParams() {
        Map<String, String> search = new HashMap<>();
        Optional<String> premium = Optional.ofNullable(this.premium);
        Optional<String> desc = Optional.ofNullable(this.desc);
        Optional<String> sortParameter = Optional.ofNullable(this.sortParameter);
        Optional<String> submissionDate = Optional.ofNullable(this.submissionDate);
        Optional<String> expirationDate = Optional.ofNullable(this.expirationDate);

        premium.ifPresent(s -> search.put(PREMIUM, s));
        desc.ifPresent(s -> search.put(DESC_SORT, s));
        sortParameter.ifPresent(s -> search.put(SORT_PARAMETER, s));
        submissionDate.ifPresent(s -> search.put(SUBMISSION_DATE, s));
        expirationDate.ifPresent(s -> search.put(EXPIRATION_DATE, s));
        return search;
    }
}
