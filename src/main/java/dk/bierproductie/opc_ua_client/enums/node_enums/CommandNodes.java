package dk.bierproductie.opc_ua_client.enums.node_enums;

import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

public enum CommandNodes {

    SET_MACHINE_SPEED(new NodeId(Constants.NAMESPACE_INDEX_VALUE, "::Program:Cube.Command.MachSpeed")),
    SET_MACHINE_COMMAND(new NodeId(Constants.NAMESPACE_INDEX_VALUE, "::Program:Cube.Command.CntrlCmd")),
    EXECUTE_MACHINE_COMMAND(new NodeId(Constants.NAMESPACE_INDEX_VALUE, "::Program:Cube.Command.CmdChangeRequest")),
    NEXT_BATCH_ID(new NodeId(Constants.NAMESPACE_INDEX_VALUE, "::Program:Cube.Command.Parameter[0]")),
    PRODUCT_ID_FOR_NEXT_BATCH(new NodeId(Constants.NAMESPACE_INDEX_VALUE, "::Program:Cube.Command.Parameter[1]")),
    PRODUCT_AMOUNT_IN_NEXT_BATCH(new NodeId(Constants.NAMESPACE_INDEX_VALUE, "::Program:Cube.Command.Parameter[2]"));

    public final NodeId nodeId;

    CommandNodes(NodeId nodeId) {
        this.nodeId = nodeId;
    }

}