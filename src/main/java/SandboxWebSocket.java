import org.eclipse.jetty.websocket.api.Callback;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketOpen;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class SandboxWebSocket {
    private Session session;

    @OnWebSocketOpen
    public void onWebSocketOpen(Session session) {
        System.out.println("CONNECTED: " + session.getRemoteSocketAddress());
        this.session = session;
    }

    @OnWebSocketClose
    public void onWebSocketClose(int statusCode, String reason) {
        this.session = null;
        System.out.println("CLOSE: " + statusCode + " " + reason);
    }

    @OnWebSocketError
    public void onWebSocketError(Throwable cause) {
        System.out.println("ERROR: " + cause.toString());
    }

    @OnWebSocketMessage
    public void onWebSocketText(String message) {
        System.out.println("RECEIVED: " + message.length() + " characters");
        session.sendText("Text length is " + message.length(), Callback.NOOP);
        session.sendText("Text hash is " + message.hashCode(), Callback.NOOP);
    }
}
