package com.arman_jaurigue.models;

import com.arman_jaurigue.data_objects.data_annotations.MaxLength;
import com.arman_jaurigue.data_objects.data_annotations.Required;

import java.time.LocalDateTime;

public class PlanModel {
    @Required
    private int planId;
    private String planIdError;
    @Required
    private int userId;
    private String userIdError;
    @Required
    @MaxLength(32)
    private String name;
    private String nameError;
    @Required
    private LocalDateTime startDate;
    private String startStartError;
    @Required
    private LocalDateTime endDate;
    private String endStartError;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserIdError() {
        return userIdError;
    }

    public void setUserIdError(String userIdError) {
        this.userIdError = userIdError;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String getStartStartError() {
        return startStartError;
    }

    public void setStartStartError(String startStartError) {
        this.startStartError = startStartError;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getEndStartError() {
        return endStartError;
    }

    public void setEndStartError(String endStartError) {
        this.endStartError = endStartError;
    }
}
