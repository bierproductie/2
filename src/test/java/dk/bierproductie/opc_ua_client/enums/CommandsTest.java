package dk.bierproductie.opc_ua_client.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CommandsTest {

    @Test
    void values() {
        assertNotNull(Commands.values());
    }

    @Test
    void getOrdinal() {
        assertEquals(Commands.RESET,Commands.getCommand("Reset"));
        assertEquals(Commands.ZERO,Commands.getCommand("Potato"));
    }
}