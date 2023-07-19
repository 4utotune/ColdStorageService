package tbk.accessGUI;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccessGUI {
    private final ClientHandler clientHandler;
    private final ActorHandler actorHandler;

    private Float CurrentWeight = 0f;
    private Float ReservedWeight = 0f;
    private Float MaxWeight = 0f;

    public AccessGUI(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.actorHandler = new ActorHandler(this, "handler", "coldstorageservice");

        this.clientHandler.setManager(this);
    }

    protected boolean hasUpdate() {
        return CurrentWeight != 0 || ReservedWeight != 0 || MaxWeight != 0;
    }

    public void responseFromActor(String msg, String requestId) {
        if (requestId.equals("")) {
            updateWeight(msg);
            updateMsg();
        } else {
            String value = extractFromMsg(msg);
            if (!value.equals("")) {
                if (msg.contains("storeaccepted")) {
                    ticketMsg(value, requestId);
                } else if (msg.contains("storerejected")) {
                    errorMsg("storerejected/" + value, requestId);
                } else if (msg.contains("chargetaken")) {
                    notifyMsg("chargetaken", requestId);
                } else if (msg.contains("ticketrejected")) {
                    errorMsg("ticketrejected/" + value, requestId);
                }
            }
        }
    }

    public String extractFromMsg(String msg) {
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
        this.clientHandler.sendToClient("ticket/" + ticket, requestId);
    }

    private void notifyMsg(String message, String requestId) {
        this.clientHandler.sendToClient("notify/" + message, requestId);
    }

    private void errorMsg(String error, String requestId) {
        this.clientHandler.sendToClient("error/" + error, requestId);
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
                insertticket(payload, requestId);
                break;
            default:
                break;
        }
    }

    private void storerequest(String weight, String requestId) {
        this.actorHandler.storerequest(weight, requestId);
    }

    private void insertticket(String ticket, String requestId) {
        this.actorHandler.insertticket(ticket, requestId);
    }
}
