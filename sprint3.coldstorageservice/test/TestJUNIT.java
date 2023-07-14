import org.junit.Test;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.IObserver;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.tcp.TcpClientSupport;
import unibo.basicomm23.tcp.TcpConnection;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.ws.WsConnection;

import javax.websocket.OnMessage;

import static unibo.basicomm23.tcp.TcpClientSupport.connect;
import static unibo.basicomm23.utils.CommUtils.buildEvent;
import static unibo.basicomm23.utils.CommUtils.buildRequest;

public class TestJUNIT {
    int contatoreAlarm=0;
    @Test
    public void testObstacle() throws Exception {


        String hostAddr       = "localhost";
        int port              = 11802;
        ProtocolType protocol = ProtocolType.tcp;

        /*WsConnection client = WsConnection.create(hostAddr + ":" + port);
        client.sendMessage("obstacle");*/
        Interaction c = TcpClientSupport.connect("localhost", 11802, 10);
        System.out.println("Client creato");

        System.out.println("ostacolo!");
        IApplMessage mess1 = c.request(buildEvent("TESTJUNIT","sonarcleaned","9"));

        try {
            Thread.sleep(1000); // Mette in pausa il thread per 1000 millisecondi (1 secondo)
        } catch (InterruptedException e) {
            // Gestione dell'eccezione
        }
        System.out.println("libero!");
        IApplMessage mess2 = c.request(buildEvent("TESTJUNIT","sonarcleaned","11"));


    }
    @OnMessage
    public void onMessage(String message)   {
        if ("alarm".contains(message)) {
            contatoreAlarm=contatoreAlarm+1;
        }
    }
}