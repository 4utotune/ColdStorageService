package tbk.statusGUI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatusGUI {

    private final ClientHandler clientHandler;
    private final ActorHandler actorHandler;
    private Float CurrentWeight = 0f;
    private Float ReservedWeight = 0f;
    private Float MaxWeight = 0f;
    private Integer PosX = 0;
    private Integer PosY = 0;
    private String RobotState = "";
    private String Other = "";
    private Integer Rejected = 0;

    public StatusGUI(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.clientHandler.setManager(this);

        this.actorHandler = new ActorHandler(this);
    }

    protected void updateStatusGUI(String source, String update) {
        switch (source) {
            case "transporttrolley":
                updateRobotState(update);
                break;
            case "basicrobot":
            case "engager":
            case "planexec":
                updateOther(update);
                break;
            case "robotpos":
                updateCoordinates(update);
                break;
            case "coldstorageservice":
                updateFromColdStorageService(update);
                break;
            default:
                System.out.println("StatusGui | Unknown source");
                break;
        }

        if (hasUpdate()) {
            sendUpdateMsg();
        }
    }

    protected void sendUpdateMsg() {
        String message = buildUpdateMsg();
        this.clientHandler.sendToAll(message);
    }

    private String buildUpdateMsg() {
        return CurrentWeight + "/" + ReservedWeight + "/" + MaxWeight + "/" + PosX + "/" + PosY + "/" + Rejected + "/" + RobotState + "/" + Other;
    }

    protected boolean hasUpdate() {
        return CurrentWeight != 0 || ReservedWeight != 0 || MaxWeight != 0 || PosX != 0 || PosY != 0 || Rejected != 0 || !RobotState.equals("") || !Other.equals("");
    }

    protected boolean updateFromColdStorageService(String payload) {
        Pattern pattern = Pattern.compile("\\((-?.*)\\)");
        Matcher matcher = pattern.matcher(payload);

        if (matcher.find()) {
            if (payload.contains("weight")) {
                updateWeight(matcher.group(1));
                return true;
            }
            if (payload.contains("rejected")) {
                updateRejected(matcher.group(1));
                return true;
            }
        }
        return false;
    }

    protected void updateWeight(String data) {
        String[] parts = data.split(",");

        this.CurrentWeight = Float.parseFloat(parts[1]);
        this.ReservedWeight = Float.parseFloat(parts[3]);
        this.MaxWeight = Float.parseFloat(parts[5]);
    }

    protected void updateRejected(String data) {
        Rejected = Integer.valueOf(data);
    }

    protected boolean updateCoordinates(String payload) {
        Pattern pattern = Pattern.compile("\\((-?\\d+),(-?\\d+)\\)");
        Matcher matcher = pattern.matcher(payload);

        if (matcher.find()) {
            PosX = Integer.parseInt(matcher.group(1));
            PosY = Integer.parseInt(matcher.group(2));
            return true;
        }
        return false;
    }

    protected void updateRobotState(String payload) {
        if (payload.contains("stato"))
            RobotState = payload;
    }

    protected void updateOther(String payload) {
        if (payload != null)
            Other = payload;
    }
}
