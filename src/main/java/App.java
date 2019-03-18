import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = null;
            int statusCode = 200;
            for (Method method : Routes.class.getMethods()) {
                if (method.isAnnotationPresent(WebRoute.class)) {
                    String path = method.getAnnotation(WebRoute.class).path();
                    if (path.equals(httpExchange.getRequestURI().getPath())) {
                        try {
                            response = (String) method.invoke(new Routes());
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                            statusCode = 500;
                            response = "Internal server error";
                        }
                    }
                }
            }
            if(response == null){
                statusCode = 404;
                response = "Page not found";
            }

            httpExchange.sendResponseHeaders(statusCode, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}
