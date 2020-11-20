package dk.bierproductie.opc_ua_client.enums.node_enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MachineNodesTest {

    @Test
    void isInventoryNode() {
        assertTrue(MachineNodes.isInventoryNode(MachineNodes.BARLEY.nodeId));
        assertFalse(MachineNodes.isInventoryNode(CommandNodes.SET_MACHINE_COMMAND.nodeId));
    }

    @Test
    void values() {
        assertNotNull(MachineNodes.values());
    }
}