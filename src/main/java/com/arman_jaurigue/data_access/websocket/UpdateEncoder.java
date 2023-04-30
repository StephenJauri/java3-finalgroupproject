package com.arman_jaurigue.data_access.websocket;


import com.arman_jaurigue.data_objects.Update;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class UpdateEncoder implements Encoder.Text<Update>{
    @Override
    public String encode(Update update) throws EncodeException {
        return update.toString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
