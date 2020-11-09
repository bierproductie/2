package dk.bierproductie.opc_ua_client;

import dk.bierproductie.opc_ua_client.core.Batch;
import dk.bierproductie.opc_ua_client.core.BatchHandler;
import dk.bierproductie.opc_ua_client.core.Client;
import dk.bierproductie.opc_ua_client.core.OpcUaClient;
import dk.bierproductie.opc_ua_client.enums.Products;

import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class RunOpcUaClient {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        LOGGER.log(Level.WARNING, "Application is fresh and ready to do some work! Lets get to it");
        new OpcUaClient().start();
        LOGGER.log(Level.WARNING, "Application stopped...");
        Client client = new Client("opc.tcp://127.0.0.1:4840","sdu","1234");
        BatchHandler batchHandler = new BatchHandler(client);
        Batch batch = new Batch(1, Products.STOUT, 100, 100);
        batchHandler.startBatch(batch);
    }
}
