package dk.bierproductie.opc_ua_client.core;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.sdk.client.api.identity.UsernameProvider;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Client {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private OpcUaClient opcUaClient;

    public Client(String endpointURL) {
        try {
            List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints(endpointURL).get();
            LOGGER.log(Level.INFO, "Connecting to Endpoint: {}", endpoints.get(0));

            OpcUaClientConfigBuilder ocb = new OpcUaClientConfigBuilder();
            ocb.setEndpoint(endpoints.get(0));

            opcUaClient = OpcUaClient.create(ocb.build());
            opcUaClient.connect().get();
        } catch (InterruptedException | ExecutionException | UaException e) {
            LOGGER.log(Level.WARNING, "Error on connecting to OPC: {}", e.toString());
        }
    }

    public Client(String endpointURL, String username, String password) {
        try {
            List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints(endpointURL).get();
            LOGGER.log(Level.INFO, "Connecting to Endpoint: {}", endpoints.get(0));

            OpcUaClientConfigBuilder ocb = new OpcUaClientConfigBuilder().setIdentityProvider(new UsernameProvider(username,password));
            ocb.setEndpoint(endpoints.get(0));

            opcUaClient = OpcUaClient.create(ocb.build());
            opcUaClient.connect().get();
        } catch (InterruptedException | ExecutionException | UaException e) {
            LOGGER.log(Level.WARNING, "Error on connecting to OPC: {}", e.toString());
        }
    }

    public OpcUaClient getOpcUaClient() {
        return opcUaClient;
    }
}
