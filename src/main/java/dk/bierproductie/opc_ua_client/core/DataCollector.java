package dk.bierproductie.opc_ua_client.core;

import dk.bierproductie.opc_ua_client.enums.Commands;
import dk.bierproductie.opc_ua_client.enums.MachineState;
import dk.bierproductie.opc_ua_client.enums.Products;
import dk.bierproductie.opc_ua_client.enums.StopReasons;
import dk.bierproductie.opc_ua_client.enums.node_enums.AdminNodes;
import dk.bierproductie.opc_ua_client.enums.node_enums.CommandNodes;
import dk.bierproductie.opc_ua_client.enums.node_enums.StatusNodes;
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

    private Client client;

    public DataCollector(Client client) {
        this.client = client;
    }

    public void readData(String name, NodeId nodeId) {
        try {
            DataValue dataValue = client.getOpcUaClient().readValue(0, TimestampsToReturn.Both, nodeId).get();
            Variant variant = dataValue.getValue();
            String message = String.format("%s : %s", name, String.valueOf(variant.getValue()));
            LOGGER.log(Level.INFO, message);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.log(Level.WARNING, INTERRUPTED_MESSAGE, e);
            Thread.currentThread().interrupt();
        }
    }

    public void readMachineState() {
        NodeId nodeId = StatusNodes.MACHINE_STATE.nodeId;
        try {
            DataValue dataValue = client.getOpcUaClient().readValue(0, TimestampsToReturn.Both, nodeId).get();
            Variant variant = dataValue.getValue();
            int stateInt = (int) variant.getValue();
            String message = String.format(FORMAT, StatusNodes.MACHINE_STATE, stateInt, MachineState.values()[stateInt]);
            LOGGER.log(Level.INFO, message);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.log(Level.WARNING, INTERRUPTED_MESSAGE, e);
            Thread.currentThread().interrupt();
        }
    }


    public void readStopReason() { // TODO: 08-11-2020 needs to me redone when commands are implemented
        NodeId nodeId = AdminNodes.STOP_REASON_VALUE.nodeId;
        try {
            DataValue dataValue = client.getOpcUaClient().readValue(0, TimestampsToReturn.Both, nodeId).get();
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
            DataValue dataValue = client.getOpcUaClient().readValue(0, TimestampsToReturn.Both, nodeId).get();
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
            DataValue dataValue = client.getOpcUaClient().readValue(0, TimestampsToReturn.Both, nodeId).get();
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
            DataValue dataValue = client.getOpcUaClient().readValue(0, TimestampsToReturn.Both, nodeId).get();
            Variant variant = dataValue.getValue();
            var value = variant.getValue();
            String message = nodeId.getIdentifier() + " : " + value;
            LOGGER.log(Level.INFO, message);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.log(Level.WARNING, INTERRUPTED_MESSAGE, e);
            Thread.currentThread().interrupt();
        }
    }
}
