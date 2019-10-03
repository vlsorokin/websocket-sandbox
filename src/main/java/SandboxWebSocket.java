import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import java.io.IOException;

public class SandboxWebSocket extends WebSocketAdapter {
    private Session session;

    @Override
    public void onWebSocketConnect(Session session) {
        super.onWebSocketConnect(session);
        System.out.println("CONNECTED: " + session.getRemoteAddress().toString());
        this.session = session;
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        this.session = null;
        super.onWebSocketClose(statusCode, reason);
        System.out.println("CLOSE: " + statusCode + " " + reason);
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        super.onWebSocketError(cause);
        System.out.println("ERROR: " + cause.toString());
        cause.printStackTrace();
    }

    @Override
    public void onWebSocketText(String message) {
        try {
            super.onWebSocketText(message);
            System.out.println("RECEIVED: " + message.length() + " characters: " + (message.length() > 20 ? message.substring(0, 20) + "..." : message));
            if (session != null && session.isOpen()) {
                session.getRemote().sendString("Text length is " + message.length());
                session.getRemote().sendString("Text hash is " + message.hashCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
