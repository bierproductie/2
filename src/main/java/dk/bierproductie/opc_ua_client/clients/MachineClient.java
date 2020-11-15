package dk.bierproductie.opc_ua_client.clients;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MachineClient {

    private static MachineClient INSTANCE;
    private OpcUaClient opcUaClient;

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public MachineClient(String endpointURL) throws InterruptedException {
        try {
            List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints(endpointURL).get();
            LOGGER.log(Level.INFO, "Connecting to Endpoint: {}", endpoints.get(0));

            OpcUaClientConfigBuilder ocb = new OpcUaClientConfigBuilder();
            ocb.setEndpoint(endpoints.get(0));

            opcUaClient = OpcUaClient.create(ocb.build());
            opcUaClient.connect().get();
        } catch (ExecutionException | UaException e) {
            LOGGER.log(Level.WARNING, "Error on connecting to OPC: {}", e.toString());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static MachineClient getInstance() throws InterruptedException {
        if (INSTANCE == null) {
            INSTANCE = new MachineClient("opc.tcp://127.0.0.1:4840");
        }
        return INSTANCE;
    }
}
