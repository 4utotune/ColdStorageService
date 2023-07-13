package tbk.sprint2accessGUI;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientHandler extends AbstractWebSocketHandler {
    private final List<WebSocketSession> sessions = new ArrayList<>();
    private final Map<String, WebSocketSession> pendingRequests = new HashMap<>();

    private AccessGUI guiManager;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("CL | Added client session: " + session.getUri());

        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("CL | Removed " + session);
        super.afterConnectionClosed(session, status);
    }

    // messaggi in arrivo da client
    @Override
    protected void handleTextMessage(WebSocketSession session,
                                     TextMessage message) {
        String msg = message.getPayload();
        System.out.println("CL | Received: " + msg);

        this.guiManager.clientRequest(msg, newRequest(session));
    }

    protected void sendToAll(String message) {
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void sendToClient(String message, String requestId) {
        try {
            this.pendingRequests.get(requestId).sendMessage(new TextMessage(message));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.pendingRequests.remove(requestId);
    }

    protected void setManager(AccessGUI gui) {
        this.guiManager = gui;
    }

    protected String newRequest(WebSocketSession session) {
        String requestId = "req" + session.getId().substring(session.getId().lastIndexOf('-') + 1);
        pendingRequests.put(requestId, session);
        return requestId;
    }
}
