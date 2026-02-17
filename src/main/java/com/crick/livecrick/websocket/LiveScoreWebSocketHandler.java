package com.crick.livecrick.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LiveScoreWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        System.out.println("WebSocket connection established: " + session.getId());
        
        // Send welcome message
        Map<String, Object> welcomeMessage = Map.of(
            "type", "CONNECTION_ESTABLISHED",
            "message", "Connected to LiveCrick WebSocket",
            "sessionId", session.getId()
        );
        sendMessage(session, welcomeMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        System.out.println("WebSocket connection closed: " + session.getId() + ", Status: " + status);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received message from " + session.getId() + ": " + payload);
        
        // Echo message back or handle specific commands
        Map<String, Object> response = Map.of(
            "type", "MESSAGE_RECEIVED",
            "originalMessage", payload
        );
        sendMessage(session, response);
    }

    /**
     * Broadcast message to all connected clients
     */
    public void broadcast(Object message) {
        String messageJson;
        try {
            messageJson = objectMapper.writeValueAsString(message);
        } catch (Exception e) {
            System.err.println("Error serializing message: " + e.getMessage());
            return;
        }

        sessions.values().forEach(session -> {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(messageJson));
                }
            } catch (IOException e) {
                System.err.println("Error sending message to session " + session.getId() + ": " + e.getMessage());
            }
        });
    }

    /**
     * Send message to specific session
     */
    private void sendMessage(WebSocketSession session, Object message) throws IOException {
        if (session.isOpen()) {
            String messageJson = objectMapper.writeValueAsString(message);
            session.sendMessage(new TextMessage(messageJson));
        }
    }

    /**
     * Get count of active connections
     */
    public int getActiveConnectionsCount() {
        return sessions.size();
    }
}
