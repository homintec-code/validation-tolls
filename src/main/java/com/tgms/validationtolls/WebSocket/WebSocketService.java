package com.tgms.validationtolls.WebSocket;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketService {


    private final Map<String, WebSocketSession> connectedClients = new ConcurrentHashMap<>();

    public void broadcastMessage(String message) {
        for (WebSocketSession session : connectedClients.values()) {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(message));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addClient(WebSocketSession session, String uniqueClientId) {
        connectedClients.put(uniqueClientId, session);
    }

    public void removeClient(String uniqueClientId) {
        connectedClients.remove(uniqueClientId);
    }


}
