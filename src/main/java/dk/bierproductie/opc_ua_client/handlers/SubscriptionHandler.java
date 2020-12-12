package dk.bierproductie.opc_ua_client.handlers;

import dk.bierproductie.opc_ua_client.core.Subscription;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

import java.util.ArrayList;

public class SubscriptionHandler {
    private static SubscriptionHandler instance;
    private static ArrayList<UaSubscription> subscriptions = new ArrayList<>();
    private final OpcUaClient client;

    public SubscriptionHandler(OpcUaClient client) {
        this.client = client;
    }

    public static SubscriptionHandler getInstance() {
        return instance;
    }

    public static void setInstance(OpcUaClient client) {
        instance = new SubscriptionHandler(client);
    }

    public static void addSubscription(UaSubscription subscription) {
        subscriptions.add(subscription);
    }

    public static void removeSubscriptions() {
        for (UaSubscription subscription : subscriptions) {
            HandlerFactory.client.getSubscriptionManager().deleteSubscription(subscription.getSubscriptionId());
        }
    }

    public void subscribe(NodeId nodeId, boolean constant) {
        Thread subscriptionThread = new Thread(new Subscription(client, nodeId, constant));
        subscriptionThread.start();
    }
}
