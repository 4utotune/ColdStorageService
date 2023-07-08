package tbk.sprint2accessGUI;

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
    private String senderId;
    private String destActor;

    private final List<WebSocketSession> sessions = new ArrayList<>();

    private AccessGUI guiManager;
    private Interaction relay_tcp;

    protected void init(AccessGUI gui, String senderId, String destActor) {
        this.guiManager = gui;
        this.senderId = senderId;
        this.destActor = destActor;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("AC | Added coldstorage session: " + session.getUri());
        relay_tcp = TcpClientSupport.connect("127.0.0.1", 11802, 10);
        System.out.println("AC | Stabilita connessione tcp: " + relay_tcp);
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

        this.guiManager.messageFromActor(msg);
    }

    private void sendToActor(IApplMessage message) {
        try {
            relay_tcp.forward(message.toString());
            System.out.println("AC | Sending " + message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void storerequest(String weight) {
        IApplMessage message = CommUtils.buildDispatch(senderId, "gui_storerequest", "gui_storerequest(" + weight + ")", destActor);
        sendToActor(message);
    }

    protected void insertticket(String ticket) {
        IApplMessage message = CommUtils.buildDispatch(senderId, "gui_insertticket", "gui_insertticket(" + ticket + ")", destActor);
        sendToActor(message);
    }
}
