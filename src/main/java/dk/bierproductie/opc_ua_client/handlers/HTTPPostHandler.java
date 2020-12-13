package dk.bierproductie.opc_ua_client.handlers;

import com.squareup.okhttp.*;
import dk.bierproductie.opc_ua_client.core.BatchData;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HTTPPostHandler {

    private static final MediaType JSON = MediaType.parse("application/json");
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static HTTPPostHandler instance;
    private String apiUrl;

    public HTTPPostHandler() {
        ConfigHandler configHandler = ConfigHandler.getInstance();
        apiUrl = configHandler.getApiUrl();
    }

    public static HTTPPostHandler getInstance() {
        if (instance == null) {
            instance = new HTTPPostHandler();
        }
        return instance;
    }


    public void postData() {
        BatchData batchData = BatchHandler.getCurrentBatchData();
        OkHttpClient client = new OkHttpClient();
        String json = batchData.toJson();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseMsg = response.body().string();
            LOGGER.log(Level.INFO,responseMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}