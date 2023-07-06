package tbk.sprint2accessGUI;


public class AccessGUI {
    public final ClientHandler clientHandler;
    public final ActorHandler actorHandler;

    public AccessGUI(ClientHandler clientHandler, ActorHandler actorHandler) {
        this.clientHandler = clientHandler;
        this.actorHandler = actorHandler;

        this.clientHandler.setManager(this);
        this.actorHandler.init(this, "handler", "accessgui_proxy");
    }

    public void messageFromActor(String msg) {
        String[] parts = msg.split("-");
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
        this.clientHandler.sendToAll(data);
    }

    private void ticketMsg(String ticket) {
        this.clientHandler.sendToAll(ticket);
    }

    private void notifyMsg(String message) {
        this.clientHandler.sendToAll(message);
    }

    private void errorMsg(String error) {
        this.clientHandler.sendToAll(error);
    }

    public void clientRequest(String msg) {
        String[] parts = msg.split("-");
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
