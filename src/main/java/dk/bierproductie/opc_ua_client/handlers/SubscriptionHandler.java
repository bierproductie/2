package dk.bierproductie.opc_ua_client.handlers;

import dk.bierproductie.opc_ua_client.core.Subscription;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

public class SubscriptionHandler {
    private final OpcUaClient client;
    private static SubscriptionHandler instance;

    public SubscriptionHandler(OpcUaClient client) {
        this.client = client;
    }

    public Thread subscribe(NodeId nodeId, long sleepTime) {
        Thread subscriptionThread = new Thread(new Subscription(client, nodeId, sleepTime));
        subscriptionThread.start();
        return subscriptionThread;
    }

    public static void setInstance(OpcUaClient client) {
        instance = new SubscriptionHandler(client);
    }

    public static SubscriptionHandler getInstance() {
        return instance;
    }
}
