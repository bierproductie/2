package dk.bierproductie.opc_ua_client.handlers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dk.bierproductie.opc_ua_client.core.Batch;
import dk.bierproductie.opc_ua_client.enums.Products;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HTTPHandler implements HttpHandler {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //Check type of request
        if(exchange.getRequestMethod().equals("POST")){
            handlePostRequest(exchange);
        } else {
            LOGGER.log(Level.INFO,"Error on POST");
        }
    }

    private void handlePostRequest(HttpExchange e) throws IOException {
        //Get request body
        StringBuilder sb = new StringBuilder();
        InputStream is = e.getRequestBody();

        //Create json string
        int i;
        while((i = is.read()) != -1){
            sb.append((char) i);
        }

        //Create Json object
        JSONObject json = new JSONObject(sb.toString());

        int id = json.getInt("id");
        Products type = Products.values()[json.getInt("recipe")];
        float speed = json.getFloat("speed");
        float amt = json.getFloat("amount_to_produce");

        //Create new Batch object
        Batch b = new Batch(id, type, speed, amt);

        try {
            BatchHandler.getInstance().startBatch(b);
        } catch (ExecutionException | InterruptedException executionException) {
            executionException.printStackTrace();
        }
    }
}