package dk.bierproductie.opc_ua_client.handlers;

import dk.bierproductie.opc_ua_client.clients.MachineClient;
import dk.bierproductie.opc_ua_client.clients.SimulatorClient;
import dk.bierproductie.opc_ua_client.core.DataCollector;
import dk.bierproductie.opc_ua_client.core.DataWriter;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

import java.util.concurrent.ExecutionException;

public class HandlerFactory {

    private static HandlerFactory instance;
    public static boolean onSimulator;
    public static OpcUaClient client;

    public HandlerFactory(boolean isSimulator) throws InterruptedException, ExecutionException {
        if (isSimulator) {
            client = SimulatorClient.getInstance().getOpcUaClient(); //todo get simulator url from .env file
        } else {
            client = MachineClient.getInstance().getOpcUaClient(); // get machine url and port from .env file
        }
        CommandHandler.setInstance(client);
        SubscriptionHandler.setInstance(client);
        DataCollector.setInstance(client);
        DataWriter.setInstance(client);
        MachineHandler machineHandler = new MachineHandler();
        machineHandler.setUpSimulator();
        Thread.sleep(1000);
        HTTPHandler.setInstance("localhost"); //todo get backend url from somewhere
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
