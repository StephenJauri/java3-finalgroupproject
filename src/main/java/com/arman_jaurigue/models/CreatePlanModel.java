package com.arman_jaurigue.models;

import com.arman_jaurigue.data_objects.data_annotations.*;

import java.time.LocalDateTime;

public class CreatePlanModel {
    @Required
    @MinLength(5)
    @MaxLength(100)
    private String name;
    private String nameError;

    @Required
    @DateTime
    @Validate(errorMessage = "Start date cannot be before today")
    private String startDate;
    private String startDateError;

    @Required
    @DateTime
    private String endDate;
    private String endDateError;


    @CheckAfter(errorField = "endDateError", errorMessage = "End date cannot be before the start date")
    private boolean startAndEndDateValidation() {
        boolean valid = true;
        if ((startDateError == null || startDateError.length() == 0) && (endDateError == null || endDateError.length() == 0)) {
            if (LocalDateTime.parse(startDate).isAfter(LocalDateTime.parse(endDate))) {
                valid = false;
            }
        }
        return valid;
    }
    private boolean startDateValidation(String startDate) {
        boolean valid = true;
        if (startDateError == null || startDateError.length() == 0) {
            if (LocalDateTime.parse(startDate).isBefore(LocalDateTime.now())) {
                valid = false;
            }
        }
        return valid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDateError() {
        return startDateError;
    }

    public void setStartDateError(String startDateError) {
        this.startDateError = startDateError;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDateError() {
        return endDateError;
    }

    public void setEndDateError(String endDateError) {
        this.endDateError = endDateError;
    }
}
