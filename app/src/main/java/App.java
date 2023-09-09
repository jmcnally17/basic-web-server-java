import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static class HomeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().set("Content-Type", "application/json");

            String response = "{\"message\": \"Well hello there!\"}";

            OutputStream os = exchange.getResponseBody();
            exchange.sendResponseHeaders(200, response.length());
            os.write(response.getBytes());
            os.flush();
            os.close();
        }
    }

    private static class NumberHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().set("Content-Type", "application/json");

            String response = "{\"score\": 25}";

            OutputStream os = exchange.getResponseBody();
            exchange.sendResponseHeaders(200, response.length());
            os.write(response.getBytes());
            os.flush();
            os.close();
        }
    }

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8000), 0);
        server.createContext("/", new HomeHandler());
        server.createContext("/number", new NumberHandler());

        server.start();
        logger.info("Listening on port 8000...");
    }
}
