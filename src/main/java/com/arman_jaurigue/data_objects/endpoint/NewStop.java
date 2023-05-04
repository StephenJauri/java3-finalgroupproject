package com.arman_jaurigue.data_objects.endpoint;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.io.StringWriter;

public class NewStop {
    int stopId;

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public JsonObject getJson() {
        return Json.createObjectBuilder()
                .add("stopId", stopId)
                .build();
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
