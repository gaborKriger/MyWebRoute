import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) throws IOException {

        // create server and set port
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        // create route and collect the context
        server.createContext("/test", new MyHandler());
        server.createContext("/another", new AnotherMyHandler());
        // creates a default executor
        server.setExecutor(null);
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = "This is the response";
            // create http header (set the length)
            httpExchange.sendResponseHeaders(200, response.length());
            // stream inside the date
            OutputStream os = httpExchange.getResponseBody();
            // convert the data
            os.write(response.getBytes());
            os.close();
        }
    }

    private static class AnotherMyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = "This is another response";
            // create http header (set the length)
            httpExchange.sendResponseHeaders(200, response.length());
            // stream inside the date
            OutputStream os = httpExchange.getResponseBody();
            // convert the data
            os.write(response.getBytes());
            os.close();
        }
    }
}
