package tbk.statusGUI;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler extends AbstractWebSocketHandler {
    private final List<WebSocketSession> sessions = new ArrayList<>();

    private StatusGUI guiManager;

    protected void setManager(StatusGUI manager) {
        this.guiManager = manager;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("CL | Added client session: " + session.getUri());

        if (guiManager.hasUpdate()) {
            guiManager.sendUpdateMsg();
        }

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
    }

    protected void sendToAll(String message) {
        System.out.println("CL | Sending: " + message);
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                System.out.println("Errore nell'invio del messaggio " + message);
            }
        }
    }
}
