package dk.bierproductie.opc_ua_client.core;

import dk.bierproductie.opc_ua_client.enums.Commands;
import dk.bierproductie.opc_ua_client.enums.MachineState;
import dk.bierproductie.opc_ua_client.enums.Products;
import dk.bierproductie.opc_ua_client.enums.StopReasons;
import dk.bierproductie.opc_ua_client.enums.node_enums.AdminNodes;
import dk.bierproductie.opc_ua_client.enums.node_enums.CommandNodes;
import dk.bierproductie.opc_ua_client.enums.node_enums.StatusNodes;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataCollector {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static final String INTERRUPTED_MESSAGE = "Interrupted!";
    public static final String FORMAT = "%s : %d : %s";
    private static DataCollector instance;

    private final OpcUaClient client;

    public DataCollector(OpcUaClient client) {
        this.client = client;
    }

    public Object readData(String name, NodeId nodeId, boolean withString) {
        try {
            DataValue dataValue = client.readValue(0, TimestampsToReturn.Both, nodeId).get();
            Variant variant = dataValue.getValue();
            if (withString) {
                String message = String.format("%s : %s", name, variant.getValue());
                LOGGER.log(Level.INFO, message);
            }
            return variant.getValue();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.log(Level.WARNING, INTERRUPTED_MESSAGE, e);
            Thread.currentThread().interrupt();
        }
        return null;
    }

    public int readMachineState(boolean withString) {
        NodeId nodeId = StatusNodes.MACHINE_STATE.nodeId;
        try {
            DataValue dataValue = client.readValue(0, TimestampsToReturn.Both, nodeId).get();
            Variant variant = dataValue.getValue();
            int stateInt = (int) variant.getValue();
            if (withString){
                String message = String.format(FORMAT, StatusNodes.MACHINE_STATE, stateInt, MachineState.getStateFromValue(stateInt).output);
                LOGGER.log(Level.INFO, message);
            }
            return stateInt;
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.log(Level.WARNING, INTERRUPTED_MESSAGE, e);
            Thread.currentThread().interrupt();
        }
        return 0;
    }


    public void readStopReason() {
        NodeId nodeId = AdminNodes.STOP_REASON_VALUE.nodeId;
        try {
            DataValue dataValue = client.readValue(0, TimestampsToReturn.Both, nodeId).get();
            Variant variant = dataValue.getValue();
            int intValue = (int) variant.getValue();
            String message = String.format(FORMAT, nodeId, intValue, StopReasons.values()[intValue]);
            LOGGER.log(Level.INFO, message);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.log(Level.WARNING, INTERRUPTED_MESSAGE, e);
            Thread.currentThread().interrupt();
        }
    }

    public void readCurrentCommand() {
        NodeId nodeId = CommandNodes.SET_MACHINE_COMMAND.nodeId;
        try {
            DataValue dataValue = client.readValue(0, TimestampsToReturn.Both, nodeId).get();
            Variant variant = dataValue.getValue();
            int intValue = (int) variant.getValue();
            String message = String.format(FORMAT, nodeId, intValue, Commands.values()[intValue]);
            LOGGER.log(Level.INFO, message);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.log(Level.WARNING, INTERRUPTED_MESSAGE, e);
            Thread.currentThread().interrupt();
        }
    }

    public void readCurrentProductId() {
        NodeId nodeId = AdminNodes.BATCH_PRODUCT_ID.nodeId;
        try {
            DataValue dataValue = client.readValue(0, TimestampsToReturn.Both, nodeId).get();
            Variant variant = dataValue.getValue();
            int intValue = Math.round((Float) variant.getValue());
            String message = String.format(FORMAT, nodeId, intValue, Products.values()[intValue]);
            LOGGER.log(Level.INFO,message);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.log(Level.WARNING, INTERRUPTED_MESSAGE, e);
            Thread.currentThread().interrupt();
        }
    }

    public void readNodeValue(NodeId nodeId) {
        try {
            DataValue dataValue = client.readValue(0, TimestampsToReturn.Both, nodeId).get();
            Variant variant = dataValue.getValue();
            var value = variant.getValue();
            String message = nodeId.getIdentifier() + " : " + value;
            LOGGER.log(Level.INFO, message);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.log(Level.WARNING, INTERRUPTED_MESSAGE, e);
            Thread.currentThread().interrupt();
        }
    }

    public static DataCollector getInstance() {
        return instance;
    }

    public static void setInstance(OpcUaClient client) {
        DataCollector.instance = new DataCollector(client);
    }
}
