package dk.bierproductie.opc_ua_client.handlers;

import dk.bierproductie.opc_ua_client.core.DataCollector;
import dk.bierproductie.opc_ua_client.enums.Commands;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

import java.util.concurrent.ExecutionException;

public class MachineHandler {

    private OpcUaClient client;
    private DataCollector dataCollector;
    private CommandHandler commandHandler;

    public MachineHandler(OpcUaClient client) {
        this.client = client;
        this.dataCollector = DataCollector.getInstance();
        this.commandHandler = CommandHandler.getInstance();
    }

    public void setUpSimulator() throws ExecutionException, InterruptedException {
        if (dataCollector.readMachineState(false) == 2) {
            commandHandler.setCommand(Commands.RESET);
        }
    }

}
