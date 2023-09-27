package coldstorageservice;

import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import unibo.basicomm23.coap.CoapConnection;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.tcp.TcpClientSupport;
import unibo.basicomm23.utils.CommUtils;

public class ActorHandler {
    private final String senderId;
    private final String destActor;

    private final Interaction tcpConn;

    public ActorHandler(String senderId, String destActor) {
        this.senderId = senderId;
        this.destActor = destActor;
        try {
            tcpConn = TcpClientSupport.connect("127.0.0.1", 11802, 10);
            System.out.println("AC | Stabilita connessione tcp: " + tcpConn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        CoapConnection coapConn = new CoapConnection("127.0.0.1:11802", "ctx_coldstorage/coldstorageservice");
    }


    private IApplMessage sendToActor(IApplMessage message, String requestId) {
        IApplMessage response = null;
        try {
            System.out.println("AC | Sending " + message);
            response = tcpConn.request(message);
            System.out.println("AC | Got response " + response);
        } catch (Exception e) {
            System.out.println("AC | There was an error while sending the request");
        }
        if (response == null) {
            System.out.println("AC | There is no response :/");
        }

        return response;
    }

    public IApplMessage storerequest(String weight, String requestId) {
        IApplMessage message = CommUtils.buildRequest(senderId, "storerequest", "storerequest(" + weight + ")", destActor);
        return sendToActor(message, requestId);
    }

    public IApplMessage insertticket(String ticket, String requestId) {
        IApplMessage message = CommUtils.buildRequest(senderId, "insertticket", "insertticket(" + ticket + ")", destActor);
        return sendToActor(message, requestId);
    }

}
