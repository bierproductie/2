package dk.bierproductie.opc_ua_client.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandsTest {

    @Test
    void values() {
        assertNotNull(Commands.values());
    }

    @Test
    void valueOf() {
        assertNotNull(Commands.valueOf("ZERO"));
    }
}