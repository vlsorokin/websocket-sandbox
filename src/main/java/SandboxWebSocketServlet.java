import org.eclipse.jetty.ee10.websocket.server.JettyWebSocketServlet;
import org.eclipse.jetty.ee10.websocket.server.JettyWebSocketServletFactory;

public class SandboxWebSocketServlet extends JettyWebSocketServlet {
    @Override
    public void configure(JettyWebSocketServletFactory factory) {
        factory.register(SandboxWebSocket.class);
    }
}