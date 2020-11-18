package dk.bierproductie.opc_ua_client.core;

import dk.bierproductie.opc_ua_client.clients.SimulatorClient;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataWriter {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private OpcUaClient client;
    private static DataWriter instance;

    public DataWriter(OpcUaClient client) {
        this.client = client;
    }

    public void writeData(NodeId nodeId, Object value) throws ExecutionException, InterruptedException {
        Variant variant = new Variant(value);
        String message = String.format("Wrote on:%s with value: %s of: %s", nodeId, value, value.getClass());
        LOGGER.log(Level.INFO, message);
        client.writeValue(nodeId, DataValue.valueOnly(variant)).get();
    }

    public static DataWriter getInstance() {
        return instance;
    }

    public static void setInstance(OpcUaClient client) {
        DataWriter.instance = new DataWriter(client);
    }
}
