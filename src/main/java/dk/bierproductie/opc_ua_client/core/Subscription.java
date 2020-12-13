package dk.bierproductie.opc_ua_client.core;

import dk.bierproductie.opc_ua_client.enums.node_enums.AdminNodes;
import dk.bierproductie.opc_ua_client.enums.node_enums.MachineNodes;
import dk.bierproductie.opc_ua_client.enums.node_enums.StatusNodes;
import dk.bierproductie.opc_ua_client.handlers.BatchHandler;
import dk.bierproductie.opc_ua_client.handlers.HTTPPostHandler;
import dk.bierproductie.opc_ua_client.handlers.SubscriptionHandler;
import dk.bierproductie.opc_ua_client.subscription_logic.MachineStateSubscription;
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

import java.time.Instant;
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
    private final OpcUaClient client;
    private final NodeId nodeId;
    private boolean constant;

    public Subscription(OpcUaClient client, NodeId nodeId, boolean constant) {
        this.client = client;
        this.nodeId = nodeId;
        this.constant = constant;
    }

    public static void onSubscriptionValue(UaMonitoredItem item, DataValue value) {
        if (item.getReadValueId().getNodeId() == StatusNodes.MACHINE_STATE.nodeId) {
            MachineStateSubscription.handleData(item, value);
        } else if (item.getReadValueId().getNodeId() == StatusNodes.TEMPERATURE.nodeId) {
            String msg = String.format("Temperature Subscription value received: item=%s, value=%s",
                    item.getReadValueId().getNodeId(), value.getValue());
            LOGGER.log(Level.INFO, msg);
            Float data = (Float) value.getValue().getValue();
            BatchHandler.getCurrentBatch().addToTempOverTime(value.getSourceTime(), data);
        } else if (item.getReadValueId().getNodeId() == StatusNodes.HUMIDITY.nodeId) {
            String msg = String.format("Humidity Subscription value received: item=%s, value=%s",
                    item.getReadValueId().getNodeId(), value.getValue());
            LOGGER.log(Level.INFO, msg);
            BatchHandler.getCurrentBatch().addToHumOverTime(value.getSourceTime(), (Float) value.getValue().getValue());
        } else if (item.getReadValueId().getNodeId() == StatusNodes.VIBRATION.nodeId) {
            String msg = String.format("Vibration Subscription value received: item=%s, value=%s",
                    item.getReadValueId().getNodeId(), value.getValue());
            LOGGER.log(Level.INFO, msg);
            Float data = (Float) value.getValue().getValue();
            BatchHandler.getCurrentBatch().addToVibOverTime(value.getSourceTime(), data);
        } else {
            String msg = String.format("Subscription value received: item=%s, value=%s",
                    item.getReadValueId().getNodeId(), value.getValue());
            LOGGER.log(Level.INFO, msg);
        }
    }

    public static void onConstantSubscriptionValue(UaMonitoredItem item, DataValue value) {
        BatchData batchData = BatchHandler.getCurrentBatchData();
        Instant time = value.getSourceTime().getJavaInstant();
        batchData.setMsTime(time.toString());
        if (item.getReadValueId().getNodeId() == StatusNodes.MACHINE_STATE.nodeId) {
            int data = (int) value.getValue().getValue();
            batchData.setState(data);
        } else if (item.getReadValueId().getNodeId() == StatusNodes.TEMPERATURE.nodeId) {
            Float data = (Float) value.getValue().getValue();
            batchData.setTemperature(data);
        } else if (item.getReadValueId().getNodeId() == StatusNodes.VIBRATION.nodeId) {
            Float data = (Float) value.getValue().getValue();
            batchData.setVibration(data);
        } else if (item.getReadValueId().getNodeId() == StatusNodes.HUMIDITY.nodeId) {
            Float data = (Float) value.getValue().getValue();
            batchData.setHumidity(data);
        } else if (item.getReadValueId().getNodeId() == AdminNodes.PRODUCED_PRODUCTS.nodeId) {
            int data = (int) value.getValue().getValue();
            batchData.setProduced(data);
        } else if (item.getReadValueId().getNodeId() == AdminNodes.DEFECTIVE_PRODUCTS.nodeId) {
            int data = (int) value.getValue().getValue();
            batchData.setRejected(data);
        }
        HTTPPostHandler.getInstance().postData();
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
        BiConsumer<UaMonitoredItem, Integer> onItemCreated;

        if (!constant){
            // setting the consumer after the subscription creation
            onItemCreated = (item, id) -> item.setValueConsumer(Subscription::onSubscriptionValue);
        } else {
            // setting the consumer after the subscription creation
            onItemCreated = (item, id) -> item.setValueConsumer(Subscription::onConstantSubscriptionValue);
        }

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
        if (!MachineNodes.isInventoryNode(nodeId) && !constant) {
            SubscriptionHandler.addSubscription(subscription);
        }
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
}
