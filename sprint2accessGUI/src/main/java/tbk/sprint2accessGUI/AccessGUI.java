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

        switch (message) {
            case "update":
                updateMsg(payload);
                break;
            case "ticket":
                ticketMsg(payload);
                break;
            case "notify":
                notifyMsg(payload);
                break;
            case "error":
                errorMsg(payload);
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

    private void ticketMsg(String ticket) {
        this.clientHandler.sendToAll("ticket/" + ticket);
    }

    private void notifyMsg(String message) {
        this.clientHandler.sendToAll("notify/" + message);
    }

    private void errorMsg(String error) {
        this.clientHandler.sendToAll("error/" + error);
    }

    public void clientRequest(String msg) {
        String[] parts = msg.split("/");
        String message = parts[0];
        String payload = parts[1];

        switch (message) {
            case "storerequest":
                storerequest(payload);
                break;
            case "insertticket":
                insertticket(payload);
                break;
            default:
                break;
        }
    }

    private void storerequest(String weight) {
        this.actorHandler.storerequest(weight);
    }

    private void insertticket(String ticket) {
        this.actorHandler.insertticket(ticket);
    }
}
