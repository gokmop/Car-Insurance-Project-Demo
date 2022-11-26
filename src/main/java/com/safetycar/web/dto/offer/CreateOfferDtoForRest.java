package com.safetycar.web.dto.offer;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

import static com.safetycar.web.dto.offer.OfferDto.*;

public class CreateOfferDtoForRest {

    public static final String OWNER_CAN_NOT_BE_NULL = "Owner Can not be null";
    @PositiveOrZero(message = MUST_SELECT_MODEL)
    private int modelId;


    @PositiveOrZero(message = MUST_BE_A_POSITIVE_NUMBER)
    private int capacity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = CAN_NOT_BE_NULL)
    private LocalDate dateRegistered;

    @NotNull(message = OWNER_CAN_NOT_BE_NULL)
    private int ownerId;

    private String aboveTwentyFive = "false";
    private String hadAccidents = "false";

    public CreateOfferDtoForRest() {
    }

    public int getModelId() {
        return modelId;
    }

    public int getCapacity() {
        return capacity;
    }

    public LocalDate getDateRegistered() {
        return dateRegistered;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getAboveTwentyFive() {
        return aboveTwentyFive;
    }

    public String getHadAccidents() {
        return hadAccidents;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setDateRegistered(LocalDate dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setAboveTwentyFive(String aboveTwentyFive) {
        this.aboveTwentyFive = aboveTwentyFive;
    }

    public void setHadAccidents(String hadAccidents) {
        this.hadAccidents = hadAccidents;
    }
}
