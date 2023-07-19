package tbk.statusGUI;

import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;

public class CoapObserver implements CoapHandler {

    private final StatusGUI guiManager;
    private final String observedActor;

    public CoapObserver(StatusGUI manager, String actor) {
        this.guiManager = manager;
        this.observedActor = actor;
    }

    @Override
    public void onLoad(CoapResponse response) {
        System.out.println("AC | Got update from " + observedActor + ": " + response.getResponseText());
        guiManager.updateStatusGUI(observedActor, response.getResponseText());
    }

    @Override
    public void onError() {
        System.out.println("AC | Error while receiving update from " + observedActor);
    }
}
