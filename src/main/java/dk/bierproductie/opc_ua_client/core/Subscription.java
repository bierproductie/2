package dk.bierproductie.opc_ua_client.core;

import dk.bierproductie.opc_ua_client.enums.MachineState;
import dk.bierproductie.opc_ua_client.enums.node_enums.StatusNodes;
import dk.bierproductie.opc_ua_client.handlers.BatchHandler;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Subscription implements Runnable {
    public static final AtomicLong clientHandles = new AtomicLong(1L);
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private OpcUaClient client;
    private NodeId nodeId;
    private long sleepTime;

    public Subscription(OpcUaClient client, NodeId nodeId, long sleepTime) {
        this.client = client;
        this.nodeId = nodeId;
        this.sleepTime = sleepTime;
    }

    public void subscribe() throws InterruptedException, ExecutionException {
        // what to read
        ReadValueId readValueId = new ReadValueId(nodeId, AttributeId.Value.uid(), null, null);

        // important: client handle must be unique per item
        UInteger clientHandle = Unsigned.uint(clientHandles.getAndIncrement());
        MonitoringParameters parameters = new MonitoringParameters(
                clientHandle,
                1000.0,         // sampling interval
                null,                   // filter, null means use default
                Unsigned.uint(10),      // queue size
                true              // discard oldest
        );

        // creation request
        MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(readValueId, MonitoringMode.Reporting, parameters);

        // setting the consumer after the subscription creation
        BiConsumer<UaMonitoredItem, Integer> onItemCreated = (item, id) -> item.setValueConsumer(Subscription::onSubscriptionValue);

        // create a subscription @ 1000ms
        UaSubscription subscription = client.getSubscriptionManager().createSubscription(1000.0).get();

        List<UaMonitoredItem> items = subscription.createMonitoredItems(TimestampsToReturn.Both,
                Collections.singletonList(request),
                onItemCreated).get();

        for (UaMonitoredItem item : items) {
            if (item.getStatusCode().isGood()) {
                String msg = String.format("item created for nodeId=%s",
                        item.getReadValueId().getNodeId());
                LOGGER.log(Level.INFO, msg);
            } else {
                String msg = String.format("failed to create item for nodeId=%s (status=%s)",
                        item.getReadValueId().getNodeId(), item.getStatusCode());
                LOGGER.log(Level.INFO, msg);
            }
        }

        // let the example run for 50 seconds then terminate
        Thread.sleep(sleepTime);
    }

    @Override
    public void run() {
        try {
            subscribe();
        } catch (InterruptedException | ExecutionException e) {
            String msg = "hmmm";
            LOGGER.log(Level.WARNING, msg);
            Thread.currentThread().interrupt();
        }
    }

    public static void onSubscriptionValue(UaMonitoredItem item, DataValue value) {
        if (item.getReadValueId().getNodeId() == StatusNodes.MACHINE_STATE.nodeId) {
            String state = MachineState.values()[(int) value.getValue().getValue()].output;
            String msg = String.format("MachineState Subscription value received: item=%s, value=%s, prettyValue=%s",
                    item.getReadValueId().getNodeId(), value.getValue(), state);
            LOGGER.log(Level.INFO, msg);
            System.out.println(value.getServerTime());
        } else if (item.getReadValueId().getNodeId() == StatusNodes.TEMPERATURE.nodeId) {
            String msg = String.format("Temperature Subscription value received: item=%s, value=%s",
                    item.getReadValueId().getNodeId(), value.getValue());
            LOGGER.log(Level.INFO, msg);
            BatchHandler.getCurrentBatch().addToTempOverTime(value.getSourceTime(),(Float)value.getValue().getValue());
        } else {
            String msg = String.format("Subscription value received: item=%s, value=%s",
                    item.getReadValueId().getNodeId(), value.getValue());
            LOGGER.log(Level.INFO, msg);
        }
    }

}
