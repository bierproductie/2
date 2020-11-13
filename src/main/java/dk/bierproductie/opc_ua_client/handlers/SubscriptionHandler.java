package dk.bierproductie.opc_ua_client.handlers;

import dk.bierproductie.opc_ua_client.core.Client;
import dk.bierproductie.opc_ua_client.core.Subscription;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

public class SubscriptionHandler {
    private final Client client;

    public SubscriptionHandler(Client client) {
        this.client = client;
    }

    public void subscribe(NodeId nodeId) {
        Thread subscriptionThread = new Thread(new Subscription(client, nodeId));
        subscriptionThread.start();
    }
}
