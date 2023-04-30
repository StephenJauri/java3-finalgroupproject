package com.arman_jaurigue.data_access.websocket;

import com.arman_jaurigue.data_objects.Update;
import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;

public class UpdateDecoder implements Decoder.Text<Update>{
    @Override
    public Update decode(String s) throws DecodeException {
        JsonObject jsonObject = Json
                .createReader(new StringReader(s))
                .readObject();

        return new Update(jsonObject);
    }

    @Override
    public boolean willDecode(String s) {
        boolean result;
        try {
            JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
            result = true;
        } catch (JsonException jex) {
            result = false;
        }
        return result;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
