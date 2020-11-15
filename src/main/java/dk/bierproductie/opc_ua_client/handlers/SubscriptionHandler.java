package dk.bierproductie.opc_ua_client.handlers;

import dk.bierproductie.opc_ua_client.core.Batch;
import dk.bierproductie.opc_ua_client.core.Subscription;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

public class SubscriptionHandler {
    private OpcUaClient client;

    public SubscriptionHandler(OpcUaClient client) {
        this.client = client;
    }

    public void subscribe(NodeId nodeId, Batch batch, long sleepTime) {
        Thread subscriptionThread = new Thread(new Subscription(client, nodeId, batch, sleepTime));
        subscriptionThread.start();
    }
}
