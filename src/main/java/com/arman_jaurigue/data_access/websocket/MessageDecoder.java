package com.arman_jaurigue.data_access.websocket;

import com.arman_jaurigue.data_objects.endpoint.Message;
import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;

public class MessageDecoder implements Decoder.Text<Message>{
    @Override
    public Message decode(String s) throws DecodeException {
        JsonObject jsonObject = Json
                .createReader(new StringReader(s))
                .readObject();

        return new Message(jsonObject);
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
