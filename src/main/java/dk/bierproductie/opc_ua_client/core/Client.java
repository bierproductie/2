package dk.bierproductie.opc_ua_client.core;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.sdk.client.api.identity.UsernameProvider;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Client {

    private OpcUaClient opcUaClient;

    public Client(String endpointURL) throws ConfigException {
        List<EndpointDescription> endpoints = null;
        try {
            endpoints = DiscoveryClient.getEndpoints(endpointURL).get();
        } catch (InterruptedException | ExecutionException e){
            System.out.println("Check endpoint or there is not connection to the server");
        } finally {
            if (endpoints == null) {
                throw new ConfigException("Check there is noting wrong with: URL, Connection to the server");
            }
        }
        System.out.println("Connecting to Endpoint: " + endpoints.get(0));
        OpcUaClientConfigBuilder ocb = new OpcUaClientConfigBuilder();
        ocb.setEndpoint(endpoints.get(0));
        try { 
            opcUaClient = OpcUaClient.create(ocb.build());
        } catch(UaException e){
            System.out.println("System, got UaException");
        }
        try {
            opcUaClient.connect().get();
        } catch(InterruptedException | ExecutionException e){
            System.out.println("Can't connect to the system, check url, maybe there should be a username?");
        }

    }

    public Client(String endpointURL, String username, String password) throws ConfigException {
        List<EndpointDescription> endpoints = null;
        try {
            endpoints = DiscoveryClient.getEndpoints(endpointURL).get();
        } catch (InterruptedException | ExecutionException e){
            System.out.println("Check endpoint or there is not connection to the server");
        } finally {
            if (endpoints == null) {
                throw new ConfigException("Check there is noting wrong with: URL, Connection to the server");
            }
        }
        System.out.println("Connecting to Endpoint: " + endpoints.get(0));
        OpcUaClientConfigBuilder ocb = new OpcUaClientConfigBuilder().setIdentityProvider(new UsernameProvider(username,password));
        ocb.setEndpoint(endpoints.get(0));
        try { 
            opcUaClient = OpcUaClient.create(ocb.build());
        } catch(UaException e){
            System.out.println("System, got UaException");
        }
        try {
            opcUaClient.connect().get();
        } catch(InterruptedException | ExecutionException e){
            System.out.println("Can't connect to the system, check url, maybe there should be a username?");
        }
    }

    public OpcUaClient getOpcUaClient() {
        return opcUaClient;
    }
}
