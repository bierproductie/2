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
}
