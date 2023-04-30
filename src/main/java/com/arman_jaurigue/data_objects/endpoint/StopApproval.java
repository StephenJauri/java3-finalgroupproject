package com.arman_jaurigue.data_objects.endpoint;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.io.StringWriter;

public class StopApproval {
    private int stopId;
    private boolean approved;

    public StopApproval(JsonObject json) {
        setJson(json);
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
        return Json.createObjectBuilder()
                .add("stopId", stopId)
                .add("approved", approved)
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
