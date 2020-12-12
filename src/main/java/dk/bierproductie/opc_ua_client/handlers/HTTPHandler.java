package dk.bierproductie.opc_ua_client.handlers;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HTTPHandler {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static HTTPHandler instance;
    private HttpURLConnection connection;

    public HTTPHandler() {
        ConfigHandler configHandler = ConfigHandler.getInstance();

        String apiUrl = configHandler.getApiUrl();

        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "HTTPManger connection not established");
        }
    }

    public static HTTPHandler getInstance() {
        if (instance == null) {
            instance = new HTTPHandler();
        }
        return instance;
    }

    public void postBatch() {
        try {
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            writeOutput(BatchHandler.getCurrentBatch().json());
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }

    public void postData(String data, long time, String endpoint, boolean isSimulating) {
        System.out.println(data);
        System.out.println(endpoint);
        Date d = new Date(time);
        System.out.println(d);
        if (HandlerFactory.onSimulator && !isSimulating) {
            simulateOutput(endpoint, time);
        }
//        String urls = connection.getURL().getHost() + endpoint;
//        System.out.println(urls);
//        try {
//            URL url = new URL(urls);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("POST");
//            con.setRequestProperty("Content-Type", "application/json; utf-8");
//            con.setRequestProperty("Accept", "application/json");
//            con.setDoOutput(true);
//            writeOutput(data);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void simulateOutput(String endpoint, long time) {
        Random r = new Random();
        Gson gson = new Gson();
        if (endpoint == "producedProducts") {
            float random = (float) (20 + r.nextFloat() * (20.5 - 20));
            postData(gson.toJson(random), time, "temperature", true);
        }
    }

    public void writeOutput(String data) {
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = data.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
