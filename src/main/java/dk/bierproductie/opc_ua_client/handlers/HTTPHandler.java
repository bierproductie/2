package dk.bierproductie.opc_ua_client.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HTTPHandler {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static HTTPHandler instance;
    private HttpURLConnection connection;

    public HTTPHandler(String urlString) {
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "HTTPManger connection not established");
        }
    }

    public static HTTPHandler getInstance() {
        return instance;
    }

    public static void setInstance(String urlString) {
        HTTPHandler.instance = new HTTPHandler(urlString);
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

    public void postData(String data, String endpoint) {
        String urls = connection.getURL().getHost() + endpoint;
        System.out.println(urls);
        try {
            URL url = new URL(urls);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            writeOutput(data);
        } catch (IOException e) {
            e.printStackTrace();
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
