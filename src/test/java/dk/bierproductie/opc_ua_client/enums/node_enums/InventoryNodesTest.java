package dk.bierproductie.opc_ua_client.enums.node_enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryNodesTest {

    @Test
    void isInventoryNode() {
        assertTrue(InventoryNodes.isInventoryNode(InventoryNodes.BARLEY.nodeId));
        assertFalse(InventoryNodes.isInventoryNode(CommandNodes.SET_MACHINE_COMMAND.nodeId));
    }

    @Test
    void values() {
        assertNotNull(InventoryNodes.values());
    }
}