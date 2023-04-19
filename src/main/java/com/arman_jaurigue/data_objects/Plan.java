package com.arman_jaurigue.data_objects;

import java.time.LocalDateTime;

public class Plan {
    private int planId;
    private int userId;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Plan() {
        this(0, 0, "Undefined", LocalDateTime.now(), LocalDateTime.now());
    }

    public Plan(int planId, int userId, String name, LocalDateTime startDate, LocalDateTime endDate) {
        setPlanId(planId);
        setUserId(userId);
        setName(name);
        setStartDate(startDate);
        setEndDate(endDate);
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}



