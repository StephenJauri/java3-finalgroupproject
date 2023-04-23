package com.arman_jaurigue.data_objects;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Stop {
    private int stopId;
    private int planId;
    private int userId;
    private String name;
    private String location;
    private Timestamp time;
    private String description;
    private Boolean status;

    public Stop() {
        this(0, 0, 0, "Undefined", "Undefined", Timestamp.valueOf(LocalDateTime.now()), "Undefined", true);
    }

    public Stop(int stopId, int planId, int userId, String name, String location, Timestamp time, String description, Boolean status) {
        this.stopId = stopId;
        this.planId = planId;
        this.userId = userId;
        this.name = name;
        this.location = location;
        this.time = time;
        this.description = description;
        this.status = status;
    }

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
