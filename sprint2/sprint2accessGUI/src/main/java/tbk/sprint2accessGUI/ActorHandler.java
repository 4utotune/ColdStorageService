package tbk.sprint2accessGUI;

import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.springframework.web.socket.WebSocketSession;
import unibo.basicomm23.coap.CoapConnection;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.tcp.TcpClientSupport;
import unibo.basicomm23.utils.CommUtils;

import java.util.ArrayList;
import java.util.List;

public class ActorHandler {
    private final String senderId;
    private final String destActor;

    private final List<WebSocketSession> sessions = new ArrayList<>();

    private final AccessGUI guiManager;
    private final Interaction tcpConn;

    public ActorHandler(AccessGUI gui, String senderId, String destActor) {
        this.guiManager = gui;
        this.senderId = senderId;
        this.destActor = destActor;
        try {
            tcpConn = TcpClientSupport.connect("127.0.0.1", 11802, 10);
            System.out.println("AC | Stabilita connessione tcp: " + tcpConn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        CoapConnection coapConn = new CoapConnection("127.0.0.1:11802", "ctx_coldstorage/coldstorageservice");
        coapConn.observeResource(new CoapHandler() {
            @Override
            public void onLoad(CoapResponse response) {
                System.out.println("AC | Got update " + response.getResponseText());
                guiManager.responseFromActor(response.getResponseText(), "");
            }

            @Override
            public void onError() {
                System.out.println("AC | Error while receiving update");
            }
        });
    }


    private void sendToActor(IApplMessage message, String requestId) {
        IApplMessage response = null;
        try {
            System.out.println("AC | Sending " + message);
            response = tcpConn.request(message);
            System.out.println("AC | Got response " + response);
        } catch (Exception e) {
            System.out.println("AC | There was an error while sending the request");
        }
        if (response != null) {
            guiManager.responseFromActor(response.msgContent(), requestId);
        }

    }

    protected void storerequest(String weight, String requestId) {
        IApplMessage message = CommUtils.buildRequest(senderId, "storerequest", "storerequest(" + weight + ")", destActor);
        sendToActor(message, requestId);
    }

    protected void insertticket(String ticket, String requestId) {
        IApplMessage message = CommUtils.buildRequest(senderId, "insertticket", "insertticket(" + ticket + ")", destActor);
        sendToActor(message, requestId);
    }
}
