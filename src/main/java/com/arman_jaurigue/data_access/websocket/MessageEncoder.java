package com.arman_jaurigue.data_access.websocket;


import com.arman_jaurigue.data_objects.endpoint.Message;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message>{
    @Override
    public String encode(Message message) throws EncodeException {
        return message.toString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
