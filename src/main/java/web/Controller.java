package web;

import cache.Cache;
import cache.InMemoryCache;
import exceptions.ResponseError;
import handler.AccountHandler;
import handler.PaymentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.servlet.SparkApplication;

import static converter.TransactionConverter.convertParamsToTx;
import static spark.Spark.*;
import static utils.JsonUtil.json;
import static utils.JsonUtil.toJson;


public class Controller implements SparkApplication {
    private static Logger LOG = LoggerFactory.getLogger(Controller.class);

    private final PaymentHandler paymentHandler;
    private final AccountHandler accountHandler;
    private static final Cache cache = new InMemoryCache();

    public static void main(String[] args) {
        new Controller().init();
    }

    public Controller() {
        this.paymentHandler = new PaymentHandler(cache);
        this.accountHandler = new AccountHandler(cache);
    }

    @Override
    public void init() {
        put("/account", (Request req, Response res) -> {
            try {
                String name = req.queryParams("name");
                String currency = req.queryParams("currency");
                String balance = req.queryParams("balance");
                logReceipt("createAccount", name, currency, balance);
                return accountHandler.createAccount(name, currency, balance);
            } catch (Exception e) {
                res.status(400);
                return new ResponseError(e);
            }
        }, json());

        post("/payment", (req, res) -> {
            try {
                /*
                todo - possible sensitive data leak by passing parameters in url ... need body -> json converter in future...
                 */
                req.body();
                String payer = req.queryParams("from");
                String payee = req.queryParams("to");
                String currency = req.queryParams("currency");
                String amount = req.queryParams("amount");
                logReceipt("makePayment", payer, payee, currency, amount);
                return paymentHandler.makePayment(convertParamsToTx(amount, payer, payee, currency));
            } catch (Exception e) {
                res.status(400);
                return new ResponseError(e);
            }
        }, json());

        after((req, res) -> {
           res.type("application/json");
        });

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(toJson(new ResponseError(e)));
        });

        exception(Exception.class, (e, req, res) -> {
            res.status(500);
            res.body(toJson(new ResponseError(e)));
        });
    }

    private static void logReceipt(String method, String... params) {
        LOG.info("method [{}] -> received parameters [{}]",method, params);
    }
}

