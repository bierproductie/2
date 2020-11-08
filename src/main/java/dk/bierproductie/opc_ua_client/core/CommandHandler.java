package dk.bierproductie.opc_ua_client.core;

import dk.bierproductie.opc_ua_client.enums.Commands;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandHandler {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private NodeFactory nodeFactory;
    private Client client;

    public CommandHandler(Client client, NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
        this.client = client;
    }

    public void executeCommand() {
        NodeId cmdExecId = nodeFactory.getCommandNodeMap().get("ExecuteMachineCommand");
        Variant cmdExecVariant = new Variant(true);
        DataValue cmdExecDataValue = new DataValue(cmdExecVariant);
        client.getOpcUaClient().writeValue(cmdExecId, cmdExecDataValue);
    }

    public void setCommand(Commands command) {
        NodeId cmdNodeId = nodeFactory.getCommandNodeMap().get("SetMachineCommand");
        Variant cmdVariant = new Variant(command.ordinal());
        DataValue cmdDataValue = new DataValue(cmdVariant);
        client.getOpcUaClient().writeValue(cmdNodeId, cmdDataValue);
        executeCommand();
        String message = String.format("Executed command: %s %d", command,command.ordinal());
        LOGGER.log(Level.INFO,message);
    }
}
