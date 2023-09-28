package tbk.accessGUI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unibo.basicomm23.interfaces.IApplMessage;

import static tbk.accessGUI.AccessGUI.extractFromMsg;

public class TestGuiCommunication {
    ActorHandler handler = new ActorHandler("127.0.0.1", 11802, "handler", "coldstorageservice");

    @Test
    public void sendTicketNotValid() {
        System.out.println("Test sendTicketNotValid");
        IApplMessage response = handler.insertticket("12345");
        Assertions.assertTrue(response.isReply() && response.msgContent().contains("ticketrejected(invalid)"));
    }

    @Test
    public void sendStoreAndTicketIntime() {
        System.out.println("Test sendStoreAndTicketIntime");

        System.out.println("Sending store request");
        IApplMessage response = handler.storerequest("10");
        Assertions.assertTrue(response.isReply() && response.msgContent().contains("storeaccepted"));
        String ticket = extractFromMsg(response.msgContent());
        System.out.println("Ticket received: " + ticket);

        System.out.println("Inserting ticket");
        response = handler.insertticket(ticket);
        System.out.println(response.msgContent());
        Assertions.assertTrue(response.isReply() && response.msgContent().contains("ticketaccepted"));
        System.out.println(("Ticket accepted"));
    }

    @Test
    public void sendStoreAndTicketLate() throws InterruptedException {
        long delay = 21000; //set value(millis) greater than time limit of the ticket validity
        System.out.println("Test sendStoreAndTicketOuttime");

        System.out.println("Sending store request");
        IApplMessage response = handler.storerequest("10");
        Assertions.assertTrue(response.isReply() && response.msgContent().contains("storeaccepted"));
        String ticket = extractFromMsg(response.msgContent());
        System.out.println("Ticket received: " + ticket);

        Thread.sleep(delay);

        System.out.println("Inserting ticket");
        response = handler.insertticket(ticket);
        Assertions.assertTrue(response.isReply() && response.msgContent().contains("ticketrejected(timedout)"));
        System.out.println(("Ticket rejected"));
    }
}

