package dk.bierproductie.opc_ua_client.core;

import dk.bierproductie.opc_ua_client.enums.Commands;
import dk.bierproductie.opc_ua_client.enums.node_enums.CommandNodes;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandHandler {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private Client client;

    public CommandHandler(Client client) {
        this.client = client;
    }

    public void executeCommand() {
        NodeId nodeId = CommandNodes.EXECUTE_MACHINE_COMMAND.nodeId;
        Variant cmdExecVariant = new Variant(true);
        DataValue cmdExecDataValue = new DataValue(cmdExecVariant);
        client.getOpcUaClient().writeValue(nodeId, cmdExecDataValue);
    }

    public void setCommand(Commands command) {
        NodeId nodeId = CommandNodes.SET_MACHINE_COMMAND.nodeId;
        Variant cmdVariant = new Variant(command.ordinal());
        DataValue cmdDataValue = new DataValue(cmdVariant);
        client.getOpcUaClient().writeValue(nodeId, cmdDataValue);
        executeCommand();
        String message = String.format("Executed command: %s %d", command,command.ordinal());
        LOGGER.log(Level.INFO,message);
    }
}
