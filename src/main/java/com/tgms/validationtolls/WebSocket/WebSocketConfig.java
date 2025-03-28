package com.tgms.validationtolls.WebSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {



    public WebSocketConfig(   ) {
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
     

    }

}