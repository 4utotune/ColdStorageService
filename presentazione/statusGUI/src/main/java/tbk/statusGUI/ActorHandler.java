package tbk.statusGUI;

import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import unibo.basicomm23.coap.CoapConnection;

public class ActorHandler extends AbstractWebSocketHandler {
    private final StatusGUI guiManager;

    public ActorHandler(StatusGUI guiManager) {
        this.guiManager = guiManager;

        observerFactory("localhost", "11802", "ctx_coldstorage", "coldstorageservice");
        observerFactory("localhost", "11802", "ctx_coldstorage", "transporttrolley");
        //observerFactory("127.0.0.1", "8020", "ctxbasicrobot", "basicrobot");
        observerFactory("127.0.0.1", "8020", "ctxbasicrobot", "engager");
        observerFactory("127.0.0.1", "8020", "ctxbasicrobot", "robotpos");
        observerFactory("127.0.0.1", "8020", "ctxbasicrobot", "planexec");
    }

    private void observerFactory(String host, String port, String context, String actor) {
        new CoapConnection(host + ":" + port, context + "/" + actor)
                .observeResource(new CoapObserver(this.guiManager, actor));
    }
}




