package dk.bierproductie.opc_ua_client.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dk.bierproductie.opc_ua_client.enums.Commands;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HTTPCMDHandler implements HttpHandler {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("POST")){
            handlePostRequest(exchange);
        } else {
            LOGGER.log(Level.WARNING,"Error while posting Command");
        }
    }

    private void handlePostRequest(HttpExchange exchange) {
        //Get request body
        StringBuilder sb = new StringBuilder();
        InputStream is = exchange.getRequestBody();

        //Create json string
        try {
            int i;
            while((i = is.read()) != -1){
                sb.append((char) i);
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error on reading POST");
        }

        //Create Json object
        JSONObject json = new JSONObject(sb.toString());
        try {
            Commands command = Commands.getOrdinal(json.getString("method"));
            CommandHandler.getInstance().setCommand(command);
        } catch (ExecutionException | InterruptedException e) {
            LOGGER.log(Level.WARNING,"Error executing command");
        }
    }
}
