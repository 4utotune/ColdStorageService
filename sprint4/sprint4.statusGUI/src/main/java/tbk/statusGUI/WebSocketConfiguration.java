package tbk.statusGUI;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
    public final ClientHandler clientHandler = new ClientHandler();
    public final String clientPath = "statusgui";

    public final StatusGUI guiManager = new StatusGUI(clientHandler);

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(clientHandler, clientPath).setAllowedOrigins("*");
    }
}