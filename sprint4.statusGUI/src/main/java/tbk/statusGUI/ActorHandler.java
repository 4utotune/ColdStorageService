package tbk.statusGUI;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.tcp.TcpClientSupport;
import unibo.basicomm23.utils.CommUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActorHandler extends AbstractWebSocketHandler {
    private final List<WebSocketSession> sessions = new ArrayList<>();

    private ClientHandler clientHandler;

    protected void init(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("AC | Added coldstorage session: " + session.getUri());
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("AC | Removed " + session);
        super.afterConnectionClosed(session, status);
    }

    // messaggi in arrivo da attore
    @Override
    protected void handleTextMessage(WebSocketSession session,
                                     TextMessage message) throws IOException {
        String msg = message.getPayload();
        System.out.println("AC | Received: " + msg);

        this.clientHandler.updateStatusGUI(msg);
    }
}
