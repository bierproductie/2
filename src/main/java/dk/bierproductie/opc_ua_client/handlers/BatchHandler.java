package dk.bierproductie.opc_ua_client.handlers;

import dk.bierproductie.opc_ua_client.core.Batch;
import dk.bierproductie.opc_ua_client.core.DataCollector;
import dk.bierproductie.opc_ua_client.core.DataWriter;
import dk.bierproductie.opc_ua_client.enums.Commands;
import dk.bierproductie.opc_ua_client.enums.node_enums.CommandNodes;
import dk.bierproductie.opc_ua_client.enums.node_enums.StatusNodes;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BatchHandler {

    private CommandHandler commandHandler;
    private DataWriter dataWriter;
    private SubscriptionHandler subscriptionHandler;
    private DataCollector dataCollector;
    private static Batch currentBatch;

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public BatchHandler(OpcUaClient client) {
        this.commandHandler = new CommandHandler(client);
        this.dataWriter = new DataWriter(client);
        this.subscriptionHandler = new SubscriptionHandler(client);
        this.dataCollector = new DataCollector(client);
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

    public void setupSubscriptions() {
        subscriptionHandler.subscribe(StatusNodes.MACHINE_STATE.nodeId, 1000);
        //subscriptionHandler.subscribe(StatusNodes.TEMPERATURE.nodeId, 50000);
    }

    public static Batch getCurrentBatch() {
        return currentBatch;
    }

    public static void setCurrentBatch(Batch currentBatch) {
        BatchHandler.currentBatch = currentBatch;
    }
}
