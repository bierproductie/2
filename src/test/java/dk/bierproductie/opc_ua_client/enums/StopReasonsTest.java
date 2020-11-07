package dk.bierproductie.opc_ua_client.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StopReasonsTest {

    @Test
    void values() {
        assertNotNull(StopReasons.values());
    }

    @Test
    void valueOf() {
        assertNotNull(StopReasons.valueOf("EMPTY_INVENTORY"));
    }

}