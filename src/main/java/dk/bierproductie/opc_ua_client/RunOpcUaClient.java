package dk.bierproductie.opc_ua_client;

import dk.bierproductie.opc_ua_client.core.Batch;
import dk.bierproductie.opc_ua_client.core.Client;
import dk.bierproductie.opc_ua_client.core.DataWriter;
import dk.bierproductie.opc_ua_client.core.OpcUaClient;
import dk.bierproductie.opc_ua_client.enums.Products;
import dk.bierproductie.opc_ua_client.enums.node_enums.CommandNodes;
import dk.bierproductie.opc_ua_client.handlers.BatchHandler;
import dk.bierproductie.opc_ua_client.handlers.SubscriptionHandler;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class RunOpcUaClient {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        LOGGER.log(Level.WARNING, "Application is fresh and ready to do some work! Lets get to it");
        new OpcUaClient().start();
        LOGGER.log(Level.WARNING, "Application stopped...");
        Client client = new Client("opc.tcp://127.0.0.1:4840", "sdu", "1234");
        BatchHandler batchHandler = new BatchHandler(client);
        Batch batch = new Batch(1, Products.STOUT, 100, 100);
        batchHandler.startBatch(batch);
        subscriptionTest(client);
    }

    private static void subscriptionTest(Client client) throws ExecutionException, InterruptedException {
        SubscriptionHandler subscriptionHandler = new SubscriptionHandler(client);
        subscriptionHandler.subscribe(CommandNodes.SET_MACHINE_COMMAND.nodeId);
        DataWriter dataWriter = new DataWriter(client);
        dataWriter.writeData(CommandNodes.SET_MACHINE_COMMAND.nodeId, 1);
        sleep(2000);
        dataWriter.writeData(CommandNodes.SET_MACHINE_COMMAND.nodeId, 2);
        sleep(2000);
        dataWriter.writeData(CommandNodes.SET_MACHINE_COMMAND.nodeId, 3);
        sleep(2000);
        dataWriter.writeData(CommandNodes.SET_MACHINE_COMMAND.nodeId, 4);
    }
}
