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

   @Test
   public void twoTickets() throws InterruptedException {
       System.out.println("Sending first store request");
       IApplMessage first_store_response = handler.storerequest("10");
       Assertions.assertTrue(first_store_response.isReply() && first_store_response.msgContent().contains("storeaccepted"));
       String first_ticket = extractFromMsg(first_store_response.msgContent());
       System.out.println("Ticket received: " + first_ticket);

       System.out.println("\nSending second store request");
       IApplMessage second_store_response = handler.storerequest("10");
       Assertions.assertTrue(second_store_response.isReply() && second_store_response.msgContent().contains("storeaccepted"));
       String second_ticket = extractFromMsg(second_store_response.msgContent());
       System.out.println("Ticket received: " + second_ticket);

       System.out.println("Inserting first ticket");
       IApplMessage first_ticket_response = handler.insertticket(first_ticket);
       Assertions.assertTrue(first_ticket_response.isReply() && first_ticket_response.msgContent().contains("ticketaccepted"));
       System.out.println(("First ticket accepted"));

       System.out.println("Inserting second ticket");
       IApplMessage second_ticket_response = handler.insertticket(second_ticket);
       System.out.println(second_ticket_response);
       Assertions.assertTrue(second_ticket_response.isReply() && second_ticket_response.msgContent().contains("ticketrejected(full)"));
       System.out.println(("Second ticket should try again later"));

       Thread.sleep(3000);

       System.out.println("Inserting second ticket");
       IApplMessage second_ticket_response_retry = handler.insertticket(second_ticket);
       System.out.println(second_ticket_response_retry);
       Assertions.assertTrue(second_ticket_response_retry.isReply() && second_ticket_response_retry.msgContent().contains("ticketaccepted(wait)"));
       System.out.println(("Second ticket is accepted but must wait"));
   }
}

