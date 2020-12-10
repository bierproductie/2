package dk.bierproductie.opc_ua_client.clients;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointConfiguration;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.eclipse.milo.opcua.stack.core.util.EndpointUtil;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MachineClient {

    private static MachineClient instance;
    private OpcUaClient opcUaClient;

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public MachineClient(String url, int port) throws InterruptedException {
        try {
            List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints(url + ":" + port).get();
            LOGGER.log(Level.INFO, "Connecting to Endpoint: {}", endpoints.get(0));
            EndpointDescription endDesc = EndpointUtil.updateUrl(endpoints.get(0), url, port);

            OpcUaClientConfigBuilder ocb = new OpcUaClientConfigBuilder();
            ocb.setEndpoint(endDesc);

            opcUaClient = OpcUaClient.create(ocb.build());
            opcUaClient.connect().get();
        } catch (ExecutionException | UaException e) {
            LOGGER.log(Level.WARNING, "Error on connecting to OPC: {}", e.toString());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static MachineClient getInstance(String url, int port) throws InterruptedException {
        if (instance == null) {
            instance = new MachineClient(url, port);
        }
        return instance;
    }

    public OpcUaClient getOpcUaClient() {
        return opcUaClient;
    }
}
