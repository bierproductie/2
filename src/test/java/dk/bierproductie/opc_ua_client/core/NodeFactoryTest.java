package dk.bierproductie.opc_ua_client.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeFactoryTest {

    @Test
    void nodeFactoryTests() {
        NodeFactory nodeFactory = new NodeFactory();
        assertNotNull(nodeFactory.getAdminNodeMap());
        assertNotNull(nodeFactory.getCommandNodeMap());
        assertNotNull(nodeFactory.getStatusNodeMap());
    }
}