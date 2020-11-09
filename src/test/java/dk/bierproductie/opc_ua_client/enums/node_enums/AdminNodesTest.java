package dk.bierproductie.opc_ua_client.enums.node_enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminNodesTest {

    @Test
    void values() {
        assertNotNull(AdminNodes.values());
    }

    @Test
    void valueOf() {
        assertNotNull(AdminNodes.valueOf(""));
    }
}