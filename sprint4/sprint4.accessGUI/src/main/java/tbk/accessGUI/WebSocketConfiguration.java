package tbk.accessGUI;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
    public static final ClientHandler wshandler_client = new ClientHandler();
    public static final ActorHandler wshandler_actor = new ActorHandler();
    public static final String wspath_client = "socket";
    public static final String wspath_actor = "coldstoragesocket";

    public final AccessGUI guiManager = new AccessGUI(wshandler_client, wshandler_actor);

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(wshandler_actor, wspath_actor).setAllowedOrigins("*");
        registry.addHandler(wshandler_client, wspath_client).setAllowedOrigins("*");
    }
}