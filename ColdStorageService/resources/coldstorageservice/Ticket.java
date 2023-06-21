package coldstorageservice;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ticket {

    private String timestamp;
    private Float FW;

    // TODO add status

    public Ticket(Float weight) {
        this.FW = weight;
        this.timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(System.currentTimeMillis());
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
        long difference = currentTimestamp.getTime() - new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").parse(timestamp).getTime();

        return  (difference <= 5000);
    }
}
