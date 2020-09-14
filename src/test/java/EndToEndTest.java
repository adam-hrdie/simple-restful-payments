import cache.Cache;
import cache.InMemoryCache;
import com.google.gson.Gson;
import handler.AccountHandler;
import handler.PaymentHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Spark;
import spark.utils.IOUtils;
import web.Controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static spark.Spark.awaitInitialization;

public class EndToEndTest {

    private static Controller controller;

    @BeforeAll
    public static void beforeClass() {
        controller = new Controller();
    }

    @AfterAll
    public static void afterClass() {
        Spark.stop();
    }

    @BeforeEach
    public void setup(){
        controller.init();
        awaitInitialization();
    }

    @Test
    public void testAHappyPath() throws Exception {
        TestResponse res = request("PUT", "/account?name=adam&currency=eur&balance=100");
        assertThat(res.status, is(200));

        TestResponse res2 = request("PUT", "/account?name=bob&currency=eur&balance=100");
        assertThat(res2.status, is(200));

        TestResponse res4 = request("POST", "/payment?from=bob&to=adam&currency=EUR&amount=100");
        assertThat(res4.status, is(200));
        Map<String, String> jsonFromPayment = res4.json();
        assertThat(jsonFromPayment.toString(), is("{payee=adam, amount=100.0, newBalance=200.0, currency=EUR, payer=bob}"));

    }

    private TestResponse request(String method, String path) throws Exception {
        URL url = new URL("http://localhost:4567" + path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setDoOutput(true);
        connection.connect();
        String body = IOUtils.toString(connection.getInputStream());
        return new TestResponse(body, connection.getResponseCode());
    }

    private static class TestResponse {
        public final String body;
        public final int status;

        public TestResponse(String body, int status) {
            this.body = body;
            this.status = status;
        }

        public Map<String, String> json() {
            return new Gson().fromJson(body, HashMap.class);
        }
    }

}
