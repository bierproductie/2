package dk.bierproductie.opc_ua_client.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MachineStateTest {

    @Test
    void values() {
        assertNotNull(MachineState.values());
    }
}