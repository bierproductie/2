package dk.bierproductie.opc_ua_client.core;

import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataWriter {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private Client client;

    public DataWriter(Client client) {
        this.client = client;
    }

    public void writeData(NodeId nodeId, Object value) throws ExecutionException, InterruptedException {
        Variant variant = new Variant(value);
        String message = String.format("Wrote on:%s with value: %s of: %s", nodeId, value, value.getClass());
        LOGGER.log(Level.INFO, message);
        client.getOpcUaClient().writeValue(nodeId, DataValue.valueOnly(variant)).get();
    }
}
