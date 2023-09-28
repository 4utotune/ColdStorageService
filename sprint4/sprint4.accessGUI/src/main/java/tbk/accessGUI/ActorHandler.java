package tbk.accessGUI;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.tcp.TcpClientSupport;
import unibo.basicomm23.utils.CommUtils;

public class ActorHandler {
    private final String senderId;
    private final String destActor;

    private final Interaction tcpConn;

    public ActorHandler(String host, Integer port, String senderId, String destActor) {
        this.senderId = senderId;
        this.destActor = destActor;
        try {
            tcpConn = TcpClientSupport.connect(host, port, 10);
            System.out.println("AC | Stabilita connessione tcp: " + tcpConn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private IApplMessage sendToActor(IApplMessage message) {
        IApplMessage response = null;
        try {
            System.out.println("AC | Sending " + message);
            response = tcpConn.request(message);
            System.out.println("AC | Got response " + response);
        } catch (Exception e) {
            System.out.println("AC | There was an error while sending the request");
        }
        return response;
    }

    protected IApplMessage storerequest(String weight) {
        IApplMessage message = CommUtils.buildRequest(senderId, "storerequest", "storerequest(" + weight + ")", destActor);
        return sendToActor(message);
    }

    protected IApplMessage insertticket(String ticket) {
        IApplMessage message = CommUtils.buildRequest(senderId, "insertticket", "insertticket(" + ticket + ")", destActor);
        return sendToActor(message);
    }
}