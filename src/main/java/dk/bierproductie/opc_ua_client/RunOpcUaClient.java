package dk.bierproductie.opc_ua_client;

import dk.bierproductie.opc_ua_client.core.OpcUaClient;
import java.util.logging.Logger;
import java.util.logging.Level;

public class RunOpcUaClient {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        LOGGER.log(Level.WARNING, "Application is fresh and ready to do some work! Lets get to it");
        new OpcUaClient().start();
        LOGGER.log(Level.WARNING, "Application stopped...");
    }
}
