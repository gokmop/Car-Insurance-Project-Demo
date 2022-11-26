package com.safetycar.web.dto.offer;


import com.safetycar.validation.ValidCapacity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

public class OfferDto {

    public static final String MUST_SELECT_MODEL = "Must select model";
    public static final String MUST_SELECT_MAKE = "Must select make";
    public static final String MUST_BE_A_POSITIVE_NUMBER = "Must be a positive number";
    public static final String CAN_NOT_BE_NULL = "Must be valid date";
    @PositiveOrZero(message = MUST_SELECT_MODEL)
    private int modelId;

    @PositiveOrZero(message = MUST_SELECT_MAKE)
    private int makeId;

    @ValidCapacity(message = MUST_BE_A_POSITIVE_NUMBER)
    private String capacity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = CAN_NOT_BE_NULL)
    private LocalDate dateRegistered;

    private String view;


    private String aboveTwentyFive = "false";
    private String hadAccidents = "false";

    public OfferDto() {
    }

    public String getView() {
        return view;
    }

    public String getAboveTwentyFive() {
        return aboveTwentyFive;
    }

    public String getHadAccidents() {
        return hadAccidents;
    }

    public int getModelId() {
        return modelId;
    }

    public int getMakeId() {
        return makeId;
    }

    public String getCapacity() {
        return capacity;
    }

    public LocalDate getDateRegistered() {
        return dateRegistered;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public void setMakeId(int makeId) {
        this.makeId = makeId;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public void setDateRegistered(LocalDate dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public void setAboveTwentyFive(String aboveTwentyFive) {
        this.aboveTwentyFive = aboveTwentyFive;
    }

    public void setHadAccidents(String hadAccidents) {
        this.hadAccidents = hadAccidents;
    }

    public void setView(String redirect) {
        this.view = redirect;
    }
}
