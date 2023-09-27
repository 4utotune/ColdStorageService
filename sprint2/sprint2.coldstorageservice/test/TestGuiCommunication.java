import coldstorageservice.ActorHandler;
import org.junit.Test;
import unibo.basicomm23.interfaces.IApplMessage;

import java.sql.Time;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class TestGuiCommunication {
    ActorHandler handler = new ActorHandler("handler", "coldstorageservice");

    public String extractFromMsg(String msg) {
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(msg);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
    @Test
    public void sendTicketNotValid(){
        System.out.println("Test sendTicketNotValid");
        IApplMessage response = handler.insertticket("12345", "none"); //dummy request id ( here we don't have a client to forward the message)
        assertTrue(response.isReply()&&response.msgContent().contains("ticketrejected(invalid)"));
    }

    @Test
    public void sendStoreAndTicketIntime(){
        System.out.println("Test sendStoreAndTicketIntime");

        System.out.println("Sending store request");
        IApplMessage response = handler.storerequest("10","none"); //dummy request id ( here we don't have a client to forward the message)
        assertTrue(response.isReply()&&response.msgContent().contains("storeaccepted"));
        String ticket = extractFromMsg(response.msgContent());
        System.out.println("Ticket received: "+ticket);

        System.out.println("Inserting ticket");
        response = handler.insertticket(ticket,"none");
        System.out.println(response.msgContent());
        assertTrue(response.isReply()&&response.msgContent().contains("ticketaccepted"));
        System.out.println(("Ticket accepted"));
    }

    @Test
    public void sendStoreAndTicketOuttime() throws InterruptedException {
        long delay = 30000; //set value(millis) greater than time limit of the ticket validity
        System.out.println("Test sendStoreAndTicketOuttime");

        System.out.println("Sending store request");
        IApplMessage response = handler.storerequest("10","none"); //dummy request id ( here we don't have a client to forward the message)
        assertTrue(response.isReply()&&response.msgContent().contains("storeaccepted"));
        String ticket = extractFromMsg(response.msgContent());
        System.out.println("Ticket received: "+ticket);

        Thread.sleep(delay);

        System.out.println("Inserting ticket");
        response = handler.insertticket(ticket,"none");
        assertTrue(response.isReply()&&response.msgContent().contains("ticketrejected(timedout)"));
        System.out.println(("Ticket rejected"));
    }
}

