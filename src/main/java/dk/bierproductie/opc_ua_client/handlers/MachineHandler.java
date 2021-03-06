package dk.bierproductie.opc_ua_client.handlers;

import dk.bierproductie.opc_ua_client.core.DataCollector;
import dk.bierproductie.opc_ua_client.enums.Commands;

import java.util.concurrent.ExecutionException;

public class MachineHandler {

    private DataCollector dataCollector;
    private CommandHandler commandHandler;

    public MachineHandler() {
        this.dataCollector = DataCollector.getInstance();
        this.commandHandler = CommandHandler.getInstance();
    }

    public void setUpSimulator() throws ExecutionException, InterruptedException {
        if (dataCollector.readMachineState(false) == 2) {
            commandHandler.setCommand(Commands.RESET);
        }
    }

}
