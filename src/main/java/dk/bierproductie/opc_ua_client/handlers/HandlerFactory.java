package dk.bierproductie.opc_ua_client.handlers;

import dk.bierproductie.opc_ua_client.clients.MachineClient;
import dk.bierproductie.opc_ua_client.clients.SimulatorClient;
import dk.bierproductie.opc_ua_client.core.DataCollector;
import dk.bierproductie.opc_ua_client.core.DataWriter;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

import java.util.concurrent.ExecutionException;

public class HandlerFactory {

    public HandlerFactory(boolean isSimulator) throws InterruptedException, ExecutionException {
        OpcUaClient client;
        if (isSimulator){
            client = SimulatorClient.getInstance().getOpcUaClient();
        } else {
            client = MachineClient.getInstance().getOpcUaClient();
        }
        CommandHandler.setInstance(client);
        SubscriptionHandler.setInstance(client);
        DataCollector.setInstance(client);
        DataWriter.setInstance(client);
        MachineHandler machineHandler = new MachineHandler(client);
        machineHandler.setUpSimulator();
        Thread.sleep(1000);
        BatchHandler.setInstance();
    }
}
