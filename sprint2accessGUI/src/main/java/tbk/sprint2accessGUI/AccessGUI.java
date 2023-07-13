package tbk.sprint2accessGUI;


public class AccessGUI {
    private final ClientHandler clientHandler;
    private final ActorHandler actorHandler;

    private Float CurrentWeight = 0f;
    private Float ReservedWeight = 0f;
    private Float MaxWeight = 0f;

    public AccessGUI(ClientHandler clientHandler, ActorHandler actorHandler) {
        this.clientHandler = clientHandler;
        this.actorHandler = actorHandler;

        this.clientHandler.setManager(this);
        this.actorHandler.init(this, "handler", "accessgui_proxy");
    }

    public void messageFromActor(String msg) {
        String[] parts = msg.split("/");
        String message = parts[0];
        String payload = parts[1];
        String requestId = "";
        if (parts.length > 2) {
            requestId = parts[2];
        }

        switch (message) {
            case "update":
                updateMsg(payload);
                break;
            case "ticket":
                ticketMsg(payload, requestId);
                break;
            case "notify":
                notifyMsg(payload, requestId);
                break;
            case "error":
                errorMsg(payload, requestId);
                break;
            default:
                break;
        }
    }

    private void updateMsg(String data) {
        String[] parts = data.split(",");

        this.CurrentWeight = Float.parseFloat(parts[1]);
        this.ReservedWeight = Float.parseFloat(parts[3]);
        this.MaxWeight = Float.parseFloat(parts[5]);

        this.clientHandler.sendToAll("update/" + (CurrentWeight + ReservedWeight) + "," + MaxWeight);
    }

    private void ticketMsg(String ticket, String requestId) {
        this.clientHandler.sendToClient("ticket/" + ticket, requestId);
    }

    private void notifyMsg(String message, String requestId) {
        if (requestId.equals("")) {
            this.clientHandler.sendToAll("notify/" + message);
        } else {
            this.clientHandler.sendToClient("notify/" + message, requestId);
        }
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
