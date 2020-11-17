package dk.bierproductie.opc_ua_client.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MachineStateTest {

    @Test
    void values() {
        assertNotNull(MachineState.values());
    }

    @Test
    void getStateFromValue() {
        for (int i = 0; i < 12; i++) {
            assertEquals(i,MachineState.getStateFromValue(i).value);
        }
        for (int i = 15; i < 10; i++) {
            assertEquals(i,MachineState.getStateFromValue(i).value);
        }
        assertNull(MachineState.getStateFromValue(30));
    }
}