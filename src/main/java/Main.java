import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Main {

    public static void main(String[] args) throws Exception {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
        System.out.println("Starting Jetty server on port " + port);

        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.addConnector(connector);

        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        server.setHandler(handler);

        handler.addServlet(SandboxWebSocketServlet.class, "/sandbox");

        server.start();
        server.dump(System.err);

        System.out.println();
        System.out.println("WebSocket server is running - ws://localhost:" + port + "/sandbox");
        server.join();
    }
}

