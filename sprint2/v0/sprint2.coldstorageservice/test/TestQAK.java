import org.junit.Test;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.tcp.TcpClientSupport;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;
import static unibo.basicomm23.utils.CommUtils.buildRequest;

public class TestQAK {

    @Test
    public void ticketAccepted() throws Exception {
        String hostAddr       = "localhost";
        int port              = 11802;
        ProtocolType protocol = ProtocolType.tcp;

        Interaction c = TcpClientSupport.connect("localhost", 11802, 10);
        System.out.println("Client creato");
        int peso = 20;
        IApplMessage response = c.request(buildRequest( "iomestesso", "proxy_storerequest", "proxy_storerequest("+peso+")", "coldstorageservice"));
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
        IApplMessage esito = c.request(buildRequest( "iomestesso", "proxy_insertticket", "proxy_insertticket("+extractedString+")", "coldstorageservice"));
        System.out.println(esito.msgContent());
        assertTrue(esito.msgContent().contains("ticketaccepted"));
        c.close();
    }

    @Test
    public void ticketExpired() throws Exception {
        String hostAddr       = "localhost";
        int port              = 11802;
        ProtocolType protocol = ProtocolType.tcp;

        Interaction c = TcpClientSupport.connect("localhost", 11802, 10);
        System.out.println("Client creato");
        int peso = 10;
        IApplMessage response = c.request(buildRequest( "iomestesso", "proxy_storerequest", "proxy_storerequest("+peso+")", "coldstorageservice"));
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
        IApplMessage esito = c.request(buildRequest( "iomestesso", "proxy_insertticket", "proxy_insertticket("+extractedString+")", "coldstorageservice"));
        System.out.println(esito.msgContent());
        assertTrue(esito.msgContent().contains("ticketrejected(timedout)"));
        c.close();
    }
}
