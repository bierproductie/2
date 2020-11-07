package dk.bierproductie.opc_ua_client.core;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import dk.bierproductie.opc_ua_client.core.*;

class ClientTest {
    Client client;
    public ClientTest(){
    }

    @Test
    void getClient(){
        assertThrows(ConfigException.class,() -> new Client("this is an invalid endpoint"));
    }
}
