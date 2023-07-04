
import org.junit.Before;
import org.junit.Test;
import unibo.basicomm23.enablers.ServerFactory;
import unibo.basicomm23.examples.prodcons.ConsumerLogic;
import unibo.basicomm23.examples.prodcons.ConsumerMsgHandler;
import unibo.basicomm23.examples.prodcons.ProducerCaller;
import unibo.basicomm23.examples.prodcons.ProducerLogic;
import unibo.basicomm23.interfaces.IApplMsgHandler;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.ConnectionFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class testQAK {
    Interaction client;

    @Before
    public void connectionConfiguration(){
        String hostAddr       = "localhost";
        int port              = 11802;
        ProtocolType protocol = ProtocolType.tcp;

        client = ConnectionFactory.createClientSupport23(protocol, hostAddr, "" + port);
    }
    @Test
    public void updateWeight() throws Exception {
        int pesoEccessivo = 200;
        String response = client.request(""+pesoEccessivo);
        assertTrue(response.contains("storeaccepted"));
        Thread.sleep(30000);
        String esito = client.request(response);
        assertTrue(esito.contains("ticketrejected"));
    }
}
