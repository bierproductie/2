package dk.bierproductie.opc_ua_client.enums;

public enum Commands {
    
    ZERO("Zero"),
    RESET("Reset"),
    START("Start"),
    STOP("Stop"),
    ABORT("Abort"),
    CLEAR("Clear");

    public final String output;

    Commands(String output) {
        this.output = output;
    }

    public static Commands getOrdinal(String cmd) {
        for (Commands command : Commands.values()){
            if (command.output.toLowerCase().equals(cmd.toLowerCase())) {
                return command;
            }
        }
        return Commands.values()[0];
    }
}
