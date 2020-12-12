package dk.bierproductie.opc_ua_client.subscription_logic;

import dk.bierproductie.opc_ua_client.core.Batch;
import dk.bierproductie.opc_ua_client.enums.MachineState;
import dk.bierproductie.opc_ua_client.handlers.BatchHandler;
import dk.bierproductie.opc_ua_client.handlers.SubscriptionHandler;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MachineStateSubscription {

    private MachineStateSubscription() {
    }

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void handleData(UaMonitoredItem item, DataValue value) {
        Batch currentBatch = BatchHandler.getCurrentBatch();
        int stateInt = (int) value.getValue().getValue();
        String state = Objects.requireNonNull(MachineState.getStateFromValue(stateInt)).output;
        setStateDuration(value, state);
        if (stateInt == 4) {
            currentBatch.setStateStartTime(Objects.requireNonNull(value.getSourceTime()).getJavaTime());
        }
        if (stateInt == 6) {
            currentBatch.setBatchStartTime(Objects.requireNonNull(value.getSourceTime()).getJavaTime());
        }
        if (stateInt == 17) {
            SubscriptionHandler.removeSubscriptions();
            BatchHandler.getCurrentBatch().setProductionTime(value.getSourceTime().getJavaTime());
            LOGGER.log(Level.INFO,""+BatchHandler.getCurrentBatch().getProductionTime());
            BatchHandler.finishBatch();
            currentBatch.setRunning(false);
        }
        logData(item, value, state);
    }

    private static void logData(UaMonitoredItem item, DataValue value, String state) {
        String msg = String.format("MachineState Subscription value received: item=%s, value=%s, prettyValue=%s",
                item.getReadValueId().getNodeId(), value.getValue(), state);
        LOGGER.log(Level.INFO, msg);
    }

    private static void setStateDuration(DataValue value, String state) {
        long stateChangeTime = value.getSourceTime().getJavaTime();
        BatchHandler.getCurrentBatch().addStateChangeDuration(stateChangeTime, state);
    }


}
