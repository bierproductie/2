package dk.bierproductie.opc_ua_client.core;

import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SubscriptionMethods {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    static void onSubscriptionValue(UaMonitoredItem item, DataValue value) {
        String msg = String.format("subscription value received: item=%s, value=%s",
                item.getReadValueId().getNodeId(), value.getValue());
        LOGGER.log(Level.INFO, msg);
    }

    static void onSubscriptionTemperatureValue(UaMonitoredItem item, DataValue value) {
        String msg = String.format("Temperature subscription value received: item=%s, value=%s",
                item.getReadValueId().getNodeId(), value.getValue());
        LOGGER.log(Level.INFO, msg);
    }
}
