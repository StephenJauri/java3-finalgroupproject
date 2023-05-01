package com.arman_jaurigue.data_objects.endpoint;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.io.StringWriter;

public class StopApproval {
    private int stopId;
    private boolean approved;
    private int position;

    public StopApproval(JsonObject json) {
        setJson(json);
        position = 0;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public JsonObject getJson() {
        System.out.println("Building Json");
        return Json.createObjectBuilder()
                .add("stopId", stopId)
                .add("approved", approved)
                .add("insertPosition", position)
                .build();
    }

    public void setJson(JsonObject json) {
        stopId = json.getInt("stopId");
        approved = json.getBoolean("approved");
    }

    @Override
    public String toString()
    {
        StringWriter writer = new StringWriter();
        Json
                .createWriter(writer)
                .write(getJson());
        return writer.toString();
    }
}
