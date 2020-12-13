package dk.bierproductie.opc_ua_client.handlers;

public class ConfigHandler {

    private static ConfigHandler instance;
    private String apiUrl = System.getenv("API_URL");
    private String machineUrl = System.getenv("MACHINE_URL");
    private String machinePort = System.getenv("MACHINE_PORT");
    private String simUrl = System.getenv("SIMULATOR_URL");
    private String simUser = System.getenv("SIMULATOR_USER");
    private String simPwd = System.getenv("SIMULATOR_PASSWORD");

    public static ConfigHandler getInstance() {
        if (instance == null) {
            instance = new ConfigHandler();
        }
        return instance;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getMachineUrl() {
        return machineUrl;
    }

    public String getMachinePort() {
        return machinePort;
    }

    public String getSimUrl() {
        return simUrl;
    }

    public String getSimUser() {
        return simUser;
    }

    public String getSimPwd() {
        return simPwd;
    }
}
