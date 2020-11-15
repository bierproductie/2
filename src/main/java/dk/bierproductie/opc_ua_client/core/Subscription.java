package dk.bierproductie.opc_ua_client.core;

import dk.bierproductie.opc_ua_client.enums.SubscriptionType;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.core.AttributeId;
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
    private SubscriptionType subscriptionType;

    public Subscription(OpcUaClient client, NodeId nodeId, SubscriptionType subscriptionType) {
        this.client = client;
        this.nodeId = nodeId;
        this.subscriptionType = subscriptionType;
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

        BiConsumer<UaMonitoredItem, Integer> onItemCreated = getUaMonitoredItemIntegerBiConsumer();

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
        Thread.sleep(50000);
    }

    private BiConsumer<UaMonitoredItem, Integer> getUaMonitoredItemIntegerBiConsumer() {
        // setting the consumer after the subscription creation
        BiConsumer<UaMonitoredItem, Integer> onItemCreated = null;
        switch (subscriptionType) {
            case STANDARD:
                onItemCreated = (item, id) -> item.setValueConsumer(SubscriptionMethods::onSubscriptionValue);
                break;
            case TEMPERATURE:
                onItemCreated = (item, id) -> item.setValueConsumer(SubscriptionMethods::onSubscriptionTemperatureValue);
                break;
            case MACHINE_STATE:
                break;
            default:
                throw new IllegalStateException(String.format("Unexpected value: %s", subscriptionType));
        }
        return onItemCreated;
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
