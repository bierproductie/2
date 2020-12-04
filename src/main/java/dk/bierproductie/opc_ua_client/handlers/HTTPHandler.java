package dk.bierproductie.opc_ua_client.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HTTPHandler {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private HTTPHandler instance;
    private HttpURLConnection connection;

    public HTTPHandler(String urlString) {
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection)url.openConnection();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,"HTTPManger connection not established");
        }
    }

    public HTTPHandler getInstance(String urlString) {
        if (instance == null) {
            instance = new HTTPHandler(urlString);
        }
        return instance;
    }
}
