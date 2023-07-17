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
    }

    protected void updateStatusGUI(String update) {
        String[] parts = update.split("/");
        String sender = parts[0];
        String payload = parts[1];
        switch (sender) {
            case "basicrobot":
                break;
            case "engager":
                break;
            case "robotpos":
                break;
            case "planexec":
                break;
            case "coldstorageservice":
                break;
            case "transporttrolley":
                break;
            default:
                break;
        }

        String message = sender + "/" + payload;
        System.out.println("CL | Sending: " + message);
        this.sendToAll(message);
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

}
