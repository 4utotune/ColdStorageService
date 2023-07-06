package tbk.sprint2accessGUI;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler extends AbstractWebSocketHandler {
    private final List<WebSocketSession> sessions = new ArrayList<>();

    private AccessGUI guiManager;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("AG | Added accessgui session: " + session.getUri());

        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("AG | Removed " + session);
        super.afterConnectionClosed(session, status);
    }

    // messaggi in arrivo da client
    @Override
    protected void handleTextMessage(WebSocketSession session,
                                     TextMessage message) {
        String msg = message.getPayload();
        System.out.println("AG | Received: " + msg);

        this.guiManager.clientRequest(msg);
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

    protected void setManager(AccessGUI gui) {
        this.guiManager = gui;
    }
}
