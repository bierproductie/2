package dk.bierproductie.opc_ua_client;

import dk.bierproductie.opc_ua_client.core.OpcUaClient;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunOpcUaClient {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        LOGGER.log(Level.INFO, "The OPC UA Clien it starting!...");
        new OpcUaClient().start();
        LOGGER.log(Level.INFO, "The OPC UA Clien it stopped!...");
    }
}
