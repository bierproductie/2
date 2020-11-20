package dk.bierproductie.opc_ua_client.handlers;

import dk.bierproductie.opc_ua_client.core.Batch;
import dk.bierproductie.opc_ua_client.core.DataCollector;
import dk.bierproductie.opc_ua_client.core.DataWriter;
import dk.bierproductie.opc_ua_client.enums.Commands;
import dk.bierproductie.opc_ua_client.enums.node_enums.AdminNodes;
import dk.bierproductie.opc_ua_client.enums.node_enums.CommandNodes;
import dk.bierproductie.opc_ua_client.enums.node_enums.StatusNodes;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class BatchHandler {

    private static BatchHandler instance;
    private static Batch currentBatch;
    private final CommandHandler commandHandler;
    private final DataWriter dataWriter;
    private final SubscriptionHandler subscriptionHandler;
    private final DataCollector dataCollector;

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public BatchHandler() {
        this.commandHandler = CommandHandler.getInstance();
        this.dataWriter = DataWriter.getInstance();
        this.subscriptionHandler = SubscriptionHandler.getInstance();
        this.dataCollector = DataCollector.getInstance();
    }

    public void startBatch(Batch batch) throws ExecutionException, InterruptedException {
        if (batch.isRunning() || dataCollector.readMachineState(false) != 4 && dataCollector.readMachineState(false) != 17) {
            LOGGER.log(Level.WARNING, "Another batch is already running");
        } else {
            commandHandler.setCommand(Commands.RESET);
            Thread.sleep(1000);
            setCurrentBatch(batch);
            currentBatch.setRunning(true);
            setupSubscriptions();
            dataWriter.writeData(CommandNodes.SET_NEXT_BATCH_ID.nodeId, currentBatch.getId());
            dataWriter.writeData(CommandNodes.SET_PRODUCT_ID_FOR_NEXT_BATCH.nodeId, currentBatch.getProductType());
            dataWriter.writeData(CommandNodes.SET_PRODUCT_AMOUNT_IN_NEXT_BATCH.nodeId, currentBatch.getAmountToProduce());
            dataWriter.writeData(CommandNodes.SET_MACHINE_SPEED.nodeId, currentBatch.getMachineSpeed());
            commandHandler.setCommand(Commands.START);
        }
    }

    public static void finishBatch() {
        currentBatch.setDefectiveProducts((int) DataCollector.getInstance().readData("accepted", AdminNodes.DEFECTIVE_PRODUCTS.nodeId, false));
        currentBatch.setOEE();
        LOGGER.log(Level.INFO, currentBatch.toString());
    }

    public void setupSubscriptions() {
        subscriptionHandler.subscribe(StatusNodes.MACHINE_STATE.nodeId, 1000);
        subscriptionHandler.subscribe(StatusNodes.TEMPERATURE.nodeId, 1000);
        subscriptionHandler.subscribe(StatusNodes.HUMIDITY.nodeId, 1000);
    }

    public static Batch getCurrentBatch() {
        return currentBatch;
    }

    public static void setCurrentBatch(Batch currentBatch) {
        BatchHandler.currentBatch = currentBatch;
    }

    public static void setInstance() {
        BatchHandler.instance = new BatchHandler();
    }

    public static BatchHandler getInstance() {
        return instance;
    }
}
