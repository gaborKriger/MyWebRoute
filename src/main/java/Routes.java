import static HttpMethod.HttpMethod.GET;
import static HttpMethod.HttpMethod.POST;

public class Routes {

    @WebRoute(path = "/test1")
    public String onTest1() {
        // Here goes the code to handle all requests going to localhost:8000/test
        // and to return something
        return "This is the test1 response!";
    }

    @WebRoute(path = "/test2", method = POST)
    public String onTest2()  {
        return null;
    }

    @WebRoute(path = "/test3", method = GET)
    public String onTest3() {
        return "This is the test3 response!";
    }
}
