package dk.bierproductie.opc_ua_client.enums;

public enum MachineState {

    DEACTIVATED("Deactivated", 0),
    CLEARING("Clearing", 1),
    STOPPED("Stopped", 2),
    STARTING("Starting", 3),
    IDLE("Idle", 4),
    SUSPENDED("Suspended", 5),
    EXECUTE("Execute", 6),
    STOPPING("Stopping", 7),
    ABORTING("Aborting", 8),
    ABORTED("Aborted", 9),
    HOLDING("Holding", 10),
    HELD("Held", 11),
    RESETTING("Resetting", 15),
    COMPLETING("Completing", 16),
    COMPLETE("Complete", 17),
    DEACTIVATING("Deactivating", 18),
    ACTIVATING("Activating", 19);

    public final String output;
    public final int value;

    MachineState(String output, int value) {
        this.output = output;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MachineState getStateFromValue(int value) {
        for (int i = 0; i < MachineState.values().length; i++) {
            if (MachineState.values()[i].getValue()==value){
                return MachineState.values()[i];
            }
        }
        return null;
    }
}
