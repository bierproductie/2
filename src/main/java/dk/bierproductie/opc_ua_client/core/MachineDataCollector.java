package dk.bierproductie.opc_ua_client.core;

import dk.bierproductie.opc_ua_client.enums.Commands;
import dk.bierproductie.opc_ua_client.enums.MachineState;
import dk.bierproductie.opc_ua_client.enums.Products;
import dk.bierproductie.opc_ua_client.enums.StopReasons;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MachineDataCollector {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private Client client;
    private NodeFactory nodeFactory;

    public MachineDataCollector(Client client, NodeFactory nodeFactory) {
        this.client = client;
        this.nodeFactory = nodeFactory;
    }

    public void readData(String name, NodeId nodeId) {
        try {
            DataValue dataValue = client.getOpcUaClient().readValue(0, TimestampsToReturn.Both, nodeId).get();
            Variant variant = dataValue.getValue();
            String message = String.format("%s : %s", name, String.valueOf(variant.getValue()));
            LOGGER.log(Level.INFO, message);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.log(Level.WARNING, "Interrupted!", e);
            Thread.currentThread().interrupt();
        }
    }

    public void readMachineState() {
        String nodeName = "MachineState";
        NodeId nodeId = nodeFactory.getStatusNodeMap().get(nodeName);
        try {
            DataValue dataValue = client.getOpcUaClient().readValue(0, TimestampsToReturn.Both, nodeId).get();
            Variant variant = dataValue.getValue();
            int stateInt = (int) variant.getValue();
            String message = nodeName + " : " + stateInt + " : " + MachineState.values()[stateInt];
            LOGGER.log(Level.INFO, message);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.log(Level.WARNING, "Interrupted!", e);
            Thread.currentThread().interrupt();
        }
    }


    public void readStopReason() { // TODO: 08-11-2020 needs to me redone when commands are implemented
        String nodeName = "StopReasonValue";
        NodeId nodeId = nodeFactory.getAdminNodeMap().get(nodeName);
        try {
            DataValue dataValue = client.getOpcUaClient().readValue(0, TimestampsToReturn.Both, nodeId).get();
            Variant variant = dataValue.getValue();
            int intValue = (int) variant.getValue();
            String message = String.format("%s : %d : %s", nodeName, intValue, StopReasons.values()[intValue]);
            LOGGER.log(Level.INFO, message);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.log(Level.WARNING, "Interrupted!", e);
            Thread.currentThread().interrupt();
        }
    }

    public void readCurrentCommand() {
        String nodeName = "SetMachineCommand";
        NodeId nodeId = nodeFactory.getCommandNodeMap().get(nodeName);
        try {
            DataValue dataValue = client.getOpcUaClient().readValue(0, TimestampsToReturn.Both, nodeId).get();
            Variant variant = dataValue.getValue();
            int intValue = (int) variant.getValue();
            String message = String.format("%s : %d : %s", nodeName, intValue, Commands.values()[intValue]);
            LOGGER.log(Level.INFO, message);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.log(Level.WARNING, "Interrupted!", e);
            Thread.currentThread().interrupt();
        }
    }

    public void readCurrentProductId() {
        String nodeName = "IDofProductInBatch";
        NodeId nodeId = nodeFactory.getAdminNodeMap().get(nodeName);
        try {
            DataValue dataValue = client.getOpcUaClient().readValue(0, TimestampsToReturn.Both, nodeId).get();
            Variant variant = dataValue.getValue();
            int intValue = Math.round((Float) variant.getValue());
            String message = String.format("%s : %d : %s", nodeName, intValue, Products.values()[intValue]);
            LOGGER.log(Level.INFO,message);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.log(Level.WARNING, "Interrupted!", e);
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
            LOGGER.log(Level.WARNING, "Interrupted!", e);
            Thread.currentThread().interrupt();
        }
    }
}
