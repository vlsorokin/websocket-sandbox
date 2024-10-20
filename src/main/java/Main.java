import java.net.URL;
import java.util.Objects;
import org.eclipse.jetty.ee10.servlet.DefaultServlet;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.ee10.websocket.server.config.JettyWebSocketServletContainerInitializer;
import org.eclipse.jetty.server.Server;

public class Main {

    public static void main(String[] args) throws Exception {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
        System.out.println("Starting Jetty server on port " + port);

        Server server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        JettyWebSocketServletContainerInitializer.configure(context, null);
        ServletHolder wsHolder = new ServletHolder("sandbox", new SandboxWebSocketServlet());
        context.addServlet(wsHolder, "/sandbox");

        URL urlIndexHtml = Thread.currentThread().getContextClassLoader().getResource("default/index.html");
        Objects.requireNonNull(urlIndexHtml, "Unable to find default/index.html in classpath");
        String urlBase = urlIndexHtml.toExternalForm().replaceFirst("/[^/]*$", "/");
        ServletHolder defHolder = new ServletHolder("default", new DefaultServlet());
        defHolder.setInitParameter("resourceBase", urlBase);
        defHolder.setInitParameter("dirAllowed", "true");
        context.addServlet(defHolder, "/");

        server.start();
        server.dump(System.err);

        System.out.println();
        System.out.println("WebSocket server is running - ws://localhost:" + port + "/sandbox");
        System.out.println("Open in browser to test - http://localhost:8080");
        server.join();
    }
}
