package dk.bierproductie.opc_ua_client.handlers;

import dk.bierproductie.opc_ua_client.CLI;
import dk.bierproductie.opc_ua_client.core.Subscription;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

import java.util.ArrayList;

public class SubscriptionHandler {
    private final OpcUaClient client;
    private static SubscriptionHandler instance;
    private static ArrayList<UaSubscription> subscriptions;

    public SubscriptionHandler(OpcUaClient client) {
        this.client = client;
    }

    public void subscribe(NodeId nodeId, long sleepTime) {
        Thread subscriptionThread = new Thread(new Subscription(client, nodeId, sleepTime));
        subscriptionThread.start();
    }

    public static void addSubscription(UaSubscription uaSubscription) {
        subscriptions.add(uaSubscription);
    }

    public void removeSubscriptions() {
        client.getSubscriptionManager().clearSubscriptions();
    }

    public static void setInstance(OpcUaClient client) {
        instance = new SubscriptionHandler(client);
    }

    public static SubscriptionHandler getInstance() {
        return instance;
    }
}
