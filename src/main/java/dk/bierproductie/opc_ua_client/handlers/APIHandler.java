package dk.bierproductie.opc_ua_client.handlers;

import com.squareup.okhttp.*;
import dk.bierproductie.opc_ua_client.core.BatchData;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class APIHandler {

    private static final MediaType JSON = MediaType.parse("application/json");
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static APIHandler instance;
    private final String apiUrl;
    private final OkHttpClient client = new OkHttpClient();

    public APIHandler() {
        ConfigHandler configHandler = ConfigHandler.getInstance();
        apiUrl = configHandler.getApiUrl();
    }

    public static APIHandler getInstance() {
        if (instance == null) {
            instance = new APIHandler();
        }
        return instance;
    }

    public void postData(BatchData batchData) {
        String json = batchData.toJson();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(apiUrl+"/data_over_time/")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseMsg = response.body().string();
            LOGGER.log(Level.INFO, responseMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putData(BatchData batchData) {
        String json = batchData.toJson();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(apiUrl+"/data_over_time")
                .put(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseMsg = response.body().string();
            LOGGER.log(Level.INFO, responseMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putMaintenanceValue (String data) {
        RequestBody body = RequestBody.create(JSON, data);
        Request request = new Request.Builder()
                .url(apiUrl + "/maintenance/")
                .put(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseMsg = response.body().string();
            LOGGER.log(Level.INFO, responseMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putInventoryStatus (String data, String name) {
        RequestBody body = RequestBody.create(JSON, data);
        Request request = new Request.Builder()
                .url(apiUrl + "/inventory_statuses/" + name)
                .put(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseMsg = response.body().string();
            LOGGER.log(Level.INFO, responseMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
