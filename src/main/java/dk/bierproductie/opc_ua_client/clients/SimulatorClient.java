package dk.bierproductie.opc_ua_client.clients;

import dk.bierproductie.opc_ua_client.handlers.ConfigHandler;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.sdk.client.api.identity.UsernameProvider;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class SimulatorClient {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static SimulatorClient instance;
    private OpcUaClient opcUaClient;

    public SimulatorClient() {
        ConfigHandler configHandler = ConfigHandler.getInstance();
        String endpointUrl = configHandler.getSimUrl();
        String user = configHandler.getSimUser();
        String password = configHandler.getSimPwd();

        try {
            List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints(endpointUrl).get();
            LOGGER.log(Level.INFO, "Connecting to Endpoint: {}", endpoints.get(0));

            OpcUaClientConfigBuilder ocb = new OpcUaClientConfigBuilder().setIdentityProvider(new UsernameProvider(user, password));
            ocb.setEndpoint(endpoints.get(0));

            opcUaClient = OpcUaClient.create(ocb.build());
            opcUaClient.connect().get();
        } catch (ExecutionException | UaException e) {
            LOGGER.log(Level.WARNING, "Error on connecting to OPC: {}", e.toString());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static SimulatorClient getInstance() {
        if (instance == null) {
            instance = new SimulatorClient();
        }
        return instance;
    }

    public OpcUaClient getOpcUaClient() {
        return opcUaClient;
    }
}
