package org.acme.chat;

import java.io.IOException;

import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.inject.Inject;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/chatbot")
public class ChatBotWebSocket {

    @Inject
    ChatService chat;

    // @Inject
    // ChatMemoryBean chatMemoryBean;

    @OnClose
    void onClose(Session session) {
        // chatMemoryBean.clear(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        Infrastructure.getDefaultExecutor().execute(() -> {
            System.out.println("ChatBotWebSocket.onMessage() "+message);
            String response = chat.chat(message);
            try {
                session.getBasicRemote().sendText(response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

}