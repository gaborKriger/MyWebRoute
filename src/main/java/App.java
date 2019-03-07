import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) throws IOException, InvocationTargetException, IllegalAccessException {

        // create server and set port
       HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        // create route and collect the context
        for (Method method : Routes.class.getMethods()) {
            if (method.isAnnotationPresent(WebRoute.class)) {
                String path = method.getAnnotation(WebRoute.class).path();
                String response = method.invoke(new Routes()).toString();
                System.out.println(path);
                System.out.println(response);

                server.createContext(path, new MyHandler(response));
            }
        }
        // creates a default executor
        server.setExecutor(null);
        server.start();
    }

    static class MyHandler implements HttpHandler {
        private String response;

        public MyHandler(String response) {
            this.response = response;
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
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
