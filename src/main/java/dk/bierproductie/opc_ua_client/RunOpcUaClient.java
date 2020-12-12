package dk.bierproductie.opc_ua_client;

import dk.bierproductie.opc_ua_client.core.Batch;
import dk.bierproductie.opc_ua_client.core.OpcUaClient2;
import dk.bierproductie.opc_ua_client.enums.Products;
import dk.bierproductie.opc_ua_client.handlers.BatchHandler;
import dk.bierproductie.opc_ua_client.handlers.HandlerFactory;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunOpcUaClient {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        LOGGER.log(Level.WARNING, "Application is fresh and ready to do some work! Lets get to it");
        new OpcUaClient2().start();
        LOGGER.log(Level.WARNING, "Application stopped...");
        HandlerFactory.getInstance(true);
        BatchHandler batchHandler = BatchHandler.getInstance();
        Batch batch = new Batch(1, Products.ALE, 100, 50);
        batchHandler.startBatch(batch);
        while (BatchHandler.getCurrentBatch().isRunning()) {
            Thread.sleep(1000);
        }
//        Batch batch1 = new Batch(4, Products.WHEAT, 300, 150);
//        Batch batch2 = new Batch(53, Products.ALE, 100, 50);
//        ArrayList<Batch> batches = new ArrayList<>();
//        batches.add(batch);
//        batches.add(batch1);
//        batches.add(batch2);
//        for (Batch bth : batches){
//            batchHandler.startBatch(bth);
//            while (BatchHandler.getCurrentBatch().isRunning()) {
//                Thread.sleep(1000);
//            }
//            System.out.println("finished batch" + bth.getId());
//        }
    }
}
