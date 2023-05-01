package com.arman_jaurigue.data_objects.endpoint;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

import java.io.StringReader;
import java.io.StringWriter;

public class Message {
    private String eventName;
    private JsonObject eventData;

    public Message()
    {

    }
    public Message(JsonObject json) {
        System.out.println("Began Creation");
        setJson(json);
        System.out.println("Built Message");
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public JsonObject getEventData() {
        return eventData;
    }

    public void setEventData(JsonObject eventData) {
        this.eventData = eventData;
    }

    public JsonObject getJson() {
        return Json.createObjectBuilder()
                .add("event", eventName)
                .add("data", eventData)
                .build();
    }

    public void setJson(JsonObject json) {
        this.eventName = json.getString("event");
        this.eventData = Json
                .createReader(new StringReader(json.getString("data")))
                .readObject();
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
