package dk.bierproductie.opc_ua_client.core;

import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;

public class DataWriter {
    private Client client;

    public void writeIntData(NodeId nodeId, Object value){
        Variant variant = new Variant(value);
        DataValue dataValue = new DataValue(variant);
        client.getOpcUaClient().writeValue(nodeId,dataValue);
    }
}
