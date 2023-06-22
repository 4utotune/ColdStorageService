package coldstorageservice;

import unibo.basicomm23.utils.CommUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ticket {

    private final String FORMAT = "yyyyMMddHHmmss"; // yyyy.MM.dd.HH.mm.ss
    private String timestamp;
    private Float FW;

    // TODO add status

    public Ticket(Float weight) {
        this.FW = weight;
        this.timestamp = new SimpleDateFormat(FORMAT).format(System.currentTimeMillis());
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Float getWeight() {
        return FW;
    }

    public Boolean isValid() throws ParseException {
        Date currentTimestamp = new Date();

        // Calcola la differenza in millisecondi tra i due timestamp
        long difference = currentTimestamp.getTime() - new SimpleDateFormat(FORMAT).parse(timestamp).getTime();

        return  (difference <= 15000);
    }
}
