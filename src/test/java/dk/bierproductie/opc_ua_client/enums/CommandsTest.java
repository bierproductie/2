package dk.bierproductie.opc_ua_client.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CommandsTest {

    @Test
    void values() {
        assertNotNull(Commands.values());
    }

}