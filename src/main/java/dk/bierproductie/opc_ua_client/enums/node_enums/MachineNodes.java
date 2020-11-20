package dk.bierproductie.opc_ua_client.enums.node_enums;

import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

public enum MachineNodes {

    BARLEY(new NodeId(Constants.NAMESPACE_INDEX_VALUE, "::Program.Inventory.Barley")),
    MALT(new NodeId(Constants.NAMESPACE_INDEX_VALUE, "::Program.Inventory.Malt")),
    HOPS(new NodeId(Constants.NAMESPACE_INDEX_VALUE, "::Program.Inventory.Hops")),
    WHEAT(new NodeId(Constants.NAMESPACE_INDEX_VALUE, "::Program.Inventory.Wheat")),
    YEAST(new NodeId(Constants.NAMESPACE_INDEX_VALUE, "::Program.Inventory.Yeast")),
    MAINTENANCE(new NodeId(Constants.NAMESPACE_INDEX_VALUE, "::Program.Maintenance"));

    public final NodeId nodeId;

    MachineNodes(NodeId nodeId) {
        this.nodeId = nodeId;
    }

    public static boolean isInventoryNode(NodeId nodeId) {
        for (MachineNodes node : MachineNodes.values()) {
            if (node.nodeId == nodeId) {
                return true;
            }
        }
        return false;
    }
}
