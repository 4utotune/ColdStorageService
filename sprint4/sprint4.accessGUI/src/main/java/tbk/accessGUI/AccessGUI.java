package tbk.accessGUI;


import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import unibo.basicomm23.coap.CoapConnection;
import unibo.basicomm23.interfaces.IApplMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccessGUI {
    private final ClientHandler clientHandler;
    private final ActorHandler actorHandler;

    private Float CurrentWeight = 0f;
    private Float ReservedWeight = 0f;
    private Float MaxWeight = 0f;

    private final Map<String, String> waitingTickets = new HashMap<>();

    public AccessGUI(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.actorHandler = new ActorHandler("127.0.0.1", 11802, "handler", "coldstorageservice");

        this.clientHandler.setManager(this);

        CoapConnection coapConn = new CoapConnection("127.0.0.1:11802", "ctx_coldstorage/coldstorageservice");
        coapConn.observeResource(new CoapHandler() {
            @Override
            public void onLoad(CoapResponse response) {
                System.out.println("AC | Got update " + response.getResponseText());
                parseActorResponse(response.getResponseText(), "");
            }

            @Override
            public void onError() {
                System.out.println("AC | Error while receiving update");
            }
        });
    }

    protected boolean hasUpdate() {
        return CurrentWeight != 0 || ReservedWeight != 0 || MaxWeight != 0;
    }

    public void parseActorResponse(String msg, String requestId) {
        String value = extractFromMsg(msg);
        if (requestId.equals("")) {
            if (msg.contains("chargetaken")) {
                requestId = waitingTickets.remove(value);
                if (requestId != null) {
                    chargetakenMsg(requestId);
                }
            } else if (msg.contains("weight")) {
                updateWeight(value);
                updateMsg();
            }
        } else {
            if (!value.equals("")) {
                if (msg.contains("storeaccepted")) {
                    ticketMsg(value, requestId);
                } else if (msg.contains("storerejected")) {
                    errorMsg("storerejected/" + value, requestId);
                } else if (msg.contains("ticketaccepted")) {
                    notifyMsg("ticketaccepted/" + value, requestId);
                } else if (msg.contains("ticketrejected")) {
                    errorMsg("ticketrejected/" + value, requestId);
                    waitingTickets.remove(ticketAssociatedWithRequest(requestId));
                }
            }
        }
    }

    public static String extractFromMsg(String msg) {
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(msg);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    private void updateWeight(String data) {
        Pattern pattern = Pattern.compile("\\d+\\.\\d+");
        Matcher matcher = pattern.matcher(data);
        List<Float> matches = new ArrayList<>();
        while (matcher.find()) {
            matches.add(Float.parseFloat(matcher.group()));

        }
        if (matches.size() == 3) {
            this.CurrentWeight = matches.get(0);
            this.ReservedWeight = matches.get(1);
            this.MaxWeight = matches.get(2);
        }
    }

    protected void updateMsg() {
        this.clientHandler.sendToAll("update/" + (CurrentWeight + ReservedWeight) + "," + MaxWeight);
    }

    private void ticketMsg(String ticket, String requestId) {
        this.clientHandler.sendToClient("ticket/" + ticket, requestId, true);
    }

    private void notifyMsg(String message, String requestId) {
        this.clientHandler.sendToClient("notify/" + message, requestId, false);
    }

    private void chargetakenMsg(String requestId) {
        this.clientHandler.sendToClient("chargetaken", requestId, true);
    }

    private void errorMsg(String error, String requestId) {
        this.clientHandler.sendToClient("error/" + error, requestId, true);
    }

    public void clientRequest(String msg, String requestId) {
        String[] parts = msg.split("/");
        String message = parts[0];
        String payload = parts[1];

        switch (message) {
            case "storerequest":
                storerequest(payload, requestId);
                break;
            case "insertticket":
                waitingTickets.put(payload, requestId);
                insertticket(payload, requestId);
                break;
            default:
                break;
        }
    }

    private void storerequest(String weight, String requestId) {
        IApplMessage resp = this.actorHandler.storerequest(weight);
        parseActorResponse(resp.msgContent(), requestId);
    }

    private void insertticket(String ticket, String requestId) {
        IApplMessage resp = this.actorHandler.insertticket(ticket);
        parseActorResponse(resp.msgContent(), requestId);

    }

    private String ticketAssociatedWithRequest(String requestId) {
        for (String ticket : waitingTickets.keySet()) {
            if (waitingTickets.get(ticket).equals(requestId)) {
                return waitingTickets.get(ticket);
            }
        }
        return "";
    }
}
