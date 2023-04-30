package com.arman_jaurigue.models;

import com.arman_jaurigue.data_objects.data_annotations.*;
import jdk.jfr.Timestamp;

public class CreateStopModel {
    @Required
    private int planId;
    private String planIdError;
    @Required
    @MinLength(3)
    @MaxLength(32)
    private String name;
    private String nameError;
    @Required
    @MinLength(3)
    @MaxLength(32)
    private String location;
    private String locationError;
    @Required
    @DateTime
    private String time;
    private String timeError;
    @Required
    @MinLength(5)
    @MaxLength(256)
    private String description;
    private String descriptionError;


    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getPlanIdError() {
        return planIdError;
    }

    public void setPlanIdError(String planIdError) {
        this.planIdError = planIdError;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationError() {
        return locationError;
    }

    public void setLocationError(String locationError) {
        this.locationError = locationError;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeError() {
        return timeError;
    }

    public void setTimeError(String timeError) {
        this.timeError = timeError;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionError() {
        return descriptionError;
    }

    public void setDescriptionError(String descriptionError) {
        this.descriptionError = descriptionError;
    }
}
