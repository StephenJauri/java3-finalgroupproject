package com.arman_jaurigue.data_objects.endpoint;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.io.StringWriter;

public class ViewingUserChanged {
    private int userId;
    private String name;
    private boolean joined;

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

    public boolean isJoined() {
        return joined;
    }

    public void setJoined(boolean joined) {
        this.joined = joined;
    }

    public JsonObject getJson() {
        System.out.println("Building Json");
        return Json.createObjectBuilder()
                .add("userId", userId)
                .add("name", name)
                .add("joined", joined)
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
