package dk.bierproductie.opc_ua_client.handlers;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigHandler {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static ConfigHandler instance;
    private String apiUrl = "API_URL";
    private String machineUrl = "MACHINE_URL";
    private String machinePort = "MACHINE_PORT";
    private String simUrl = "SIMULATOR_URL";
    private String simUser = "SIMULATOR_USER";
    private String simPwd = "SIMULATOR_PASSWORD";

    private ConfigHandler(){
        apiUrl = getEnv(apiUrl);
        machineUrl = getEnv(machineUrl);
        machinePort = getEnv(machinePort);
        simUrl = getEnv(simUrl);
        simUser = getEnv(simUser);
        simPwd = getEnv(simPwd);
    }
    private String getEnv(String envStr){
        String env = System.getenv(envStr);
        if (env == null) {
            LOGGER.log(Level.WARNING, "envStr, ${0} is null", envStr);
            return env;
        }
        LOGGER.log(Level.INFO, "Enviroment variable , ${0} load sucessfull", envStr);
        return env;
    }


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
