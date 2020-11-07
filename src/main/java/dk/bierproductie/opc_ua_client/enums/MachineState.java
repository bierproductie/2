package dk.bierproductie.opc_ua_client.enums;

public enum MachineState {
    DEACTIVATED("Deactivated"),
    CLEARING("Clearing"),
    STOPPED("Stopped"),
    STARTING("Starting"),
    IDLE("Idle"),
    SUSPENDED("Suspended"),
    EXECUTE("Execute"),
    STOPPING("Stopping"),
    ABORTING("Aborting"),
    ABORTED("Aborted"),
    HOLDING("Holding"),
    HELD("Held"),
    RESETTING("Resetting"),
    COMPLETING("Completing"),
    COMPLETE("Complete"),
    DEACTIVATING("Deactivating"),
    ACTIVATING("Activating");

    public final String output;

    MachineState(String output) {
        this.output = output;
    }
}
