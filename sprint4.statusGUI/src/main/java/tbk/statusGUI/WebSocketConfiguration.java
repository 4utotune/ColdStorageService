package tbk.statusGUI;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
    public static final ClientHandler wshandler_client = new ClientHandler();
    public static final ActorHandler wshandler_actor = new ActorHandler(wshandler_client);
    public static final String wspath_client = "statusgui";
    public static final String wspath_actor = "statusguiproxy";

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(wshandler_client, wspath_client).setAllowedOrigins("*");
        registry.addHandler(wshandler_actor, wspath_actor).setAllowedOrigins("*");
    }
}