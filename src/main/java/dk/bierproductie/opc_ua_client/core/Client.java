package dk.bierproductie.opc_ua_client.core;

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

public class Client {
    private OpcUaClient opcUaClient;
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Client(String endpointURL) throws ConfigException, InterruptedException {
        this(endpointURL, null, null);
    }

    public Client(String endpointURL, String username, String password) throws ConfigException, InterruptedException {
        List<EndpointDescription> endpoints = null;
        try {
            endpoints = DiscoveryClient.getEndpoints(endpointURL).get();
            LOGGER.log(Level.INFO, "Connection to Endpoint: {}", endpoints.get(0));
        } catch (InterruptedException e){
            LOGGER.log(Level.WARNING, "InterruptedException: {}", e.toString());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e){
            throw new ConfigException("Check endpoint, check connection to the server");
        }
        OpcUaClientConfigBuilder ocb = new OpcUaClientConfigBuilder();
        if (username != null && !username.equals("")) {
            ocb.setIdentityProvider(new UsernameProvider(username, password));
        }
        
        if(endpoints == null) {
            throw new ConfigException("Could not get end points, check connection?");
        } else {
            ocb.setEndpoint(endpoints.get(0));
        }
        try { 
            opcUaClient = OpcUaClient.create(ocb.build());
        } catch(UaException e){
            LOGGER.log(Level.WARNING, "Got an UaException: {}", e.toString());
        }
        try {
            opcUaClient.connect().get();
        } catch (InterruptedException e){
            LOGGER.log(Level.WARNING, "InterruptedException: {}", e.toString());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e){
            LOGGER.log(Level.WARNING, "Check endpoint or check connection to the server or wrong user");
        }
    }

    public OpcUaClient getOpcUaClient() {
        return opcUaClient;
    }
}
