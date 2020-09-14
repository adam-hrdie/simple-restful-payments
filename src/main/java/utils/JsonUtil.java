package utils;

import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonUtil {

    private static final Gson gson =  new Gson();

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }
}
