package dk.bierproductie.opc_ua_client.handlers;

import dk.bierproductie.opc_ua_client.core.Batch;
import dk.bierproductie.opc_ua_client.core.DataWriter;
import dk.bierproductie.opc_ua_client.enums.Commands;
import dk.bierproductie.opc_ua_client.enums.node_enums.CommandNodes;
import dk.bierproductie.opc_ua_client.enums.node_enums.StatusNodes;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

import java.util.concurrent.ExecutionException;

public class BatchHandler {

    private CommandHandler commandHandler;
    private DataWriter dataWriter;
    private SubscriptionHandler subscriptionHandler;

    public BatchHandler(OpcUaClient client) {
        this.commandHandler = new CommandHandler(client);
        this.dataWriter = new DataWriter(client);
        this.subscriptionHandler = new SubscriptionHandler(client);
    }

    public void startBatch(Batch batch) throws ExecutionException, InterruptedException {
        setupSubscriptions(batch);
        dataWriter.writeData(CommandNodes.SET_NEXT_BATCH_ID.nodeId, batch.getId());
        dataWriter.writeData(CommandNodes.SET_PRODUCT_ID_FOR_NEXT_BATCH.nodeId, batch.getProductType());
        dataWriter.writeData(CommandNodes.SET_PRODUCT_AMOUNT_IN_NEXT_BATCH.nodeId, batch.getAmountToProduce());
        dataWriter.writeData(CommandNodes.SET_MACHINE_SPEED.nodeId, batch.getMachineSpeed());
        commandHandler.setCommand(Commands.START);
    }

    public void setupSubscriptions(Batch batch) {
        //  subscriptionHandler.subscribe(StatusNodes.TEMPERATURE.nodeId, batch, 50000);
        //more idk
    }
}
