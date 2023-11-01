package tbk.accessGUI;

import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import unibo.basicomm23.coap.CoapConnection;
import unibo.basicomm23.interfaces.IApplMessage;

import java.util.concurrent.CountDownLatch;

import static tbk.accessGUI.AccessGUI.extractFromMsg;
import static unibo.basicomm23.utils.CommUtils.outgreen;

public class TestGuiCommunication {
    ActorHandler handler = new ActorHandler("127.0.0.1", 11802, "handler", "coldstorageservice");

    @Test
    @Order(1)
    public void sendTicketNotValid() {
        IApplMessage response = handler.insertticket("12345");
        Assertions.assertTrue(response.isReply() && response.msgContent().contains("ticketrejected(invalid)"));
    }

    @Test
    @Order(2)
    public void sendStoreAndTicketLate() throws InterruptedException {
        long delay = 21000; //set value(millis) greater than time limit of the ticket validity

        outgreen("Sending store request");
        IApplMessage response = handler.storerequest("10");
        Assertions.assertTrue(response.isReply() && response.msgContent().contains("storeaccepted"));
        String ticket = extractFromMsg(response.msgContent());
        outgreen("Ticket received: " + ticket);

        Thread.sleep(delay);

        outgreen("Inserting ticket");
        response = handler.insertticket(ticket);
        Assertions.assertTrue(response.isReply() && response.msgContent().contains("ticketrejected(timedout)"));
        outgreen("Ticket rejected");
    }

    @Test
    @Order(3)
    public void sendTicketTooHeavy() {
        IApplMessage store_response = handler.storerequest("60");
        Assertions.assertTrue(store_response.isReply() && store_response.msgContent().contains("storerejected(tooheavy)"));
    }

    @Test
    @Order(4)
    public void twoTickets() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(1);

        CoapConnection coapConn = new CoapConnection("127.0.0.1:11802", "ctx_coldstorage/coldstorageservice");
        coapConn.observeResource(new CoapHandler() {
            @Override
            public void onLoad(CoapResponse response) {
                if (response.getResponseText().contains("chargetaken")) {
                    lock.countDown();
                }
            }

            @Override
            public void onError() {
                outgreen("Error while receiving update");
            }
        });

        outgreen("Sending first store request");
        IApplMessage first_store_response = handler.storerequest("10");
        Assertions.assertTrue(first_store_response.isReply() && first_store_response.msgContent().contains("storeaccepted"));
        String first_ticket = extractFromMsg(first_store_response.msgContent());
        outgreen("Ticket received: " + first_ticket + "\n");

        outgreen("Sending second store request");
        IApplMessage second_store_response = handler.storerequest("20");
        Assertions.assertTrue(second_store_response.isReply() && second_store_response.msgContent().contains("storeaccepted"));
        String second_ticket = extractFromMsg(second_store_response.msgContent());
        outgreen("Ticket received: " + second_ticket + "\n");

        outgreen("Inserting first ticket");
        IApplMessage first_ticket_response = handler.insertticket(first_ticket);
        Assertions.assertTrue(first_ticket_response.isReply() && first_ticket_response.msgContent().contains("ticketaccepted"));
        outgreen("First ticket accepted\n");

        outgreen("Inserting second ticket");
        IApplMessage second_ticket_response = handler.insertticket(second_ticket);
        outgreen(String.valueOf(second_ticket_response));
        Assertions.assertTrue(second_ticket_response.isReply() && second_ticket_response.msgContent().contains("ticketrejected(full)"));
        outgreen("Second ticket should try again later\n");

        lock.await();

        outgreen("Retry insert second ticket");
        IApplMessage second_ticket_response_retry = handler.insertticket(second_ticket);
        outgreen(String.valueOf(second_ticket_response_retry));
        Assertions.assertTrue(second_ticket_response_retry.isReply() && second_ticket_response_retry.msgContent().contains("ticketaccepted(wait)"));
        outgreen("Second ticket is accepted but must wait");
    }

    @Test
    @Order(5)
    public void sendStoreAndTicketIntime() {
        outgreen("Sending store request");
        IApplMessage response = handler.storerequest("10");
        Assertions.assertTrue(response.isReply() && response.msgContent().contains("storeaccepted"));
        String ticket = extractFromMsg(response.msgContent());
        outgreen("Ticket received: " + ticket);

        outgreen("Inserting ticket");
        response = handler.insertticket(ticket);
        outgreen(response.msgContent());
        Assertions.assertTrue(response.isReply() && response.msgContent().contains("ticketaccepted(ok)"));
        outgreen("Ticket accepted");
    }
}

