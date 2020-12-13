package dk.bierproductie.opc_ua_client.handlers;

import dk.bierproductie.opc_ua_client.clients.MachineClient;
import dk.bierproductie.opc_ua_client.clients.SimulatorClient;
import dk.bierproductie.opc_ua_client.core.DataCollector;
import dk.bierproductie.opc_ua_client.core.DataWriter;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

import java.util.concurrent.ExecutionException;

public class HandlerFactory {

    public static boolean onSimulator;
    public static OpcUaClient client;
    private static HandlerFactory instance;

    public HandlerFactory(boolean isSimulator) throws InterruptedException, ExecutionException {
        ConfigHandler.getInstance();

        if (isSimulator) {
            client = SimulatorClient.getInstance().getOpcUaClient();
        } else {
            client = MachineClient.getInstance().getOpcUaClient();
        }
        CommandHandler.setInstance(client);
        SubscriptionHandler.setInstance(client);
        DataCollector.setInstance(client);
        DataWriter.setInstance(client);
        MachineHandler machineHandler = new MachineHandler();
        machineHandler.setUpSimulator();
        Thread.sleep(1000);
        HTTPHandler.getInstance();
        BatchHandler.setInstance();
    }

    public static HandlerFactory getInstance(boolean isSimulator) throws ExecutionException, InterruptedException {
        if (instance == null) {
            instance = new HandlerFactory(isSimulator);
            onSimulator = isSimulator;
        }
        return instance;
    }
}
