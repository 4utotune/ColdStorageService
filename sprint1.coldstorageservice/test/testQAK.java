
import org.junit.Before;
import org.junit.Test;
import unibo.basicomm23.enablers.ServerFactory;
import unibo.basicomm23.examples.prodcons.ConsumerLogic;
import unibo.basicomm23.examples.prodcons.ConsumerMsgHandler;
import unibo.basicomm23.examples.prodcons.ProducerCaller;
import unibo.basicomm23.examples.prodcons.ProducerLogic;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.IApplMsgHandler;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.tcp.TcpClientSupport;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;
import unibo.basicomm23.examples.ActorNaiveCaller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static unibo.basicomm23.utils.CommUtils.buildDispatch;
import static unibo.basicomm23.utils.CommUtils.buildRequest;

public class testQAK {

    @Test
    public void ticketExpired() throws Exception {
        String hostAddr       = "localhost";
        int port              = 11802;
        ProtocolType protocol = ProtocolType.tcp;

        Interaction c = TcpClientSupport.connect("localhost", 11802, 10);
        System.out.println("Client creato");
        int peso = 10;
        IApplMessage response = c.request(buildRequest( "iomestesso", "storerequest", "storerequest("+peso+")", "coldstorageservice"));
        System.out.println("la risposta e' "+response.msgContent());
        assertTrue(response.msgContent().contains("storeaccepted"));
        System.out.println("aspetto");
        Thread.sleep(20000);
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(response.msgContent());

        String extractedString="";
        while (matcher.find()) {
            extractedString = matcher.group(1);
            System.out.println(extractedString);
        }
        IApplMessage esito = c.request(buildRequest( "iomestesso", "insertticket", "insertticket("+extractedString+")", "coldstorageservice"));
        System.out.println(esito.msgContent());
        assertTrue(esito.msgContent().contains("ticketrejected(timedout)"));
        c.close();
    }


    @Test
    public void ticketAccepted() throws Exception {
        String hostAddr       = "localhost";
        int port              = 11802;
        ProtocolType protocol = ProtocolType.tcp;

        Interaction c = TcpClientSupport.connect("localhost", 11802, 10);
        System.out.println("Client creato");
        int peso = 20;
        IApplMessage response = c.request(buildRequest( "iomestesso", "storerequest", "storerequest("+peso+")", "coldstorageservice"));
        System.out.println("la risposta e' "+response.msgContent());
        assertTrue(response.msgContent().contains("storeaccepted"));
        System.out.println("aspetto");
        Thread.sleep(2000);
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(response.msgContent());

        String extractedString="";
        while (matcher.find()) {
            extractedString = matcher.group(1);
            System.out.println(extractedString);
        }
        IApplMessage esito = c.request(buildRequest( "iomestesso", "insertticket", "insertticket("+extractedString+")", "coldstorageservice"));
        System.out.println(esito.msgContent());
        assertTrue(esito.msgContent().contains("ticketaccepted"));
        c.close();
    }
}
