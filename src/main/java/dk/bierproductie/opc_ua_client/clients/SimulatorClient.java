package dk.bierproductie.opc_ua_client.clients;

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

public final class SimulatorClient {

    private static SimulatorClient instance;

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private OpcUaClient opcUaClient;

    public SimulatorClient(String endpointURL, String username, String password) throws InterruptedException {
        try {
            List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints(endpointURL).get();
            LOGGER.log(Level.INFO, "Connecting to Endpoint: {}", endpoints.get(0));

            OpcUaClientConfigBuilder ocb = new OpcUaClientConfigBuilder().setIdentityProvider(new UsernameProvider(username,password));
            ocb.setEndpoint(endpoints.get(0));

            opcUaClient = OpcUaClient.create(ocb.build());
            opcUaClient.connect().get();
        } catch (ExecutionException | UaException e) {
            LOGGER.log(Level.WARNING, "Error on connecting to OPC: {}", e.toString());
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public static SimulatorClient getInstance(String url, String username, String password) throws InterruptedException {
        if (instance == null){
            instance = new SimulatorClient(url,username,password);
        }
        return instance;
    }

    public OpcUaClient getOpcUaClient() {
        return opcUaClient;
    }
}
