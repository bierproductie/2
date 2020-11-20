package dk.bierproductie.opc_ua_client.enums.node_enums;

import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

public enum InventoryNodes {

    BARLEY(new NodeId(Constants.NAMESPACE_INDEX_VALUE, "::Program.Inventory.Barley")),
    MALT(new NodeId(Constants.NAMESPACE_INDEX_VALUE, "::Program.Inventory.Malt")),
    HOPS(new NodeId(Constants.NAMESPACE_INDEX_VALUE, "::Program.Inventory.Hops")),
    WHEAT(new NodeId(Constants.NAMESPACE_INDEX_VALUE, "::Program.Inventory.Wheat")),
    YEAST(new NodeId(Constants.NAMESPACE_INDEX_VALUE, "::Program.Inventory.Yeast"));

    public final NodeId nodeId;

    InventoryNodes(NodeId nodeId) {
        this.nodeId = nodeId;
    }

    public static boolean isInventoryNode(NodeId nodeId) {
        for (InventoryNodes node : InventoryNodes.values()) {
            if (node.nodeId == nodeId) {
                return true;
            }
        }
        return false;
    }
}
