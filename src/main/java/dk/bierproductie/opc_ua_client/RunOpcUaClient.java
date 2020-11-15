package dk.bierproductie.opc_ua_client;

import dk.bierproductie.opc_ua_client.clients.SimulatorClient;
import dk.bierproductie.opc_ua_client.core.Batch;
import dk.bierproductie.opc_ua_client.core.OpcUaClient2;
import dk.bierproductie.opc_ua_client.enums.Products;
import dk.bierproductie.opc_ua_client.enums.node_enums.StatusNodes;
import dk.bierproductie.opc_ua_client.handlers.BatchHandler;
import dk.bierproductie.opc_ua_client.handlers.SubscriptionHandler;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunOpcUaClient {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        LOGGER.log(Level.WARNING, "Application is fresh and ready to do some work! Lets get to it");
        new OpcUaClient2().start();
        LOGGER.log(Level.WARNING, "Application stopped...");
        OpcUaClient opcUaClient = SimulatorClient.getInstance().getOpcUaClient();
        BatchHandler batchHandler = new BatchHandler(opcUaClient);
        Batch batch = new Batch(1, Products.PILSNER, 300, 100);
        batchHandler.startBatch(batch);
        SubscriptionHandler subscriptionHandler = new SubscriptionHandler(opcUaClient);
        subscriptionHandler.subscribe(StatusNodes.MACHINE_STATE.nodeId, batch, 50000);
    }
}
