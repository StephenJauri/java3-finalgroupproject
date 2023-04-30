package com.arman_jaurigue.data_objects;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.io.StringWriter;

public class Update {
    private JsonObject json;
    private int stopId;
    private boolean approved;

    public Update(JsonObject json) {
        this.json = json;
        stopId = json.getInt("stopId");
        approved = json.getBoolean("approved");
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
        return json;
    }

    public void setJson(JsonObject json) {
        this.json = json;
    }

    @Override
    public String toString()
    {
        StringWriter writer = new StringWriter();
        Json
                .createWriter(writer)
                .write(json);
       return writer.toString();
    }
}
