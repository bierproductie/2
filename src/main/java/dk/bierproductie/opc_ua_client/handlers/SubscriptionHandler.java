package dk.bierproductie.opc_ua_client.handlers;

import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubscriptionHandler {
    public static final AtomicLong clientHandles = new AtomicLong(1L);
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void onSubscriptionValue(UaMonitoredItem item, DataValue value) {
        String msg = String.format("subscription value received: item=%s, value=%s",
                item.getReadValueId().getNodeId(), value.getValue());
        LOGGER.log(Level.INFO, msg);
    }
}
