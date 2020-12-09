package dk.bierproductie.opc_ua_client.core;

import com.google.gson.Gson;
import dk.bierproductie.opc_ua_client.enums.Products;
import org.eclipse.milo.opcua.stack.core.types.builtin.DateTime;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Batch {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final float id;
    private final float productType;
    private final float amountToProduce;
    private final Map<Long, Float> tempOverTime;
    private final Map<Long, Float> humOverTime;
    private final Map<Long, Float> vibOverTime;
    private final Map<String, Long> stateDurationTime;
    private float machineSpeed;
    private boolean running;
    private int amountProduced;
    private int defectiveProducts;
    private int acceptedProducts;
    private long stateStartTime;
    private long batchStartTime;
    private double productionTime;
    private double oee;

    public Batch(int id, Products productType, float machineSpeed, float amountToProduce) {
        this.id = id;
        this.productType = productType.ordinal();
        try {
            if (machineSpeed > 0 && machineSpeed <= productType.speedLimit) {
                this.machineSpeed = machineSpeed;
            } else {
                throw new IncorrectMachineSpeedException(String.format("Incorrect speed. It needs to be 0 < speed <= %d for the specific recipe", productType.speedLimit));
            }
        } catch (IncorrectMachineSpeedException e) {
            LOGGER.log(Level.WARNING, String.valueOf(e));
        }
        this.amountToProduce = amountToProduce;
        tempOverTime = new HashMap<>();
        humOverTime = new HashMap<>();
        vibOverTime = new HashMap<>();
        stateDurationTime = new HashMap<>();
    }

    public float getId() {
        return id;
    }

    public float getProductType() {
        return productType;
    }

    public float getMachineSpeed() {
        return machineSpeed;
    }

    public float getAmountToProduce() {
        return amountToProduce;
    }

    public int getAmountProduced() {
        return amountProduced;
    }

    public void setAmountProduced(int amountProduced) {
        this.amountProduced = amountProduced;
    }

    public void addToTempOverTime(DateTime dateTime, Float value) {
        tempOverTime.put(dateTime.getJavaTime(), value);
    }

    public Map<Long, Float> getTempOverTime() {
        return tempOverTime;
    }

    public void addToHumOverTime(DateTime dateTime, Float value) {
        humOverTime.put(dateTime.getJavaTime(), value);
    }

    public Map<Long, Float> getHumOverTime() {
        return humOverTime;
    }

    public void addToVibOverTime(DateTime dateTime, Float value) {
        vibOverTime.put(dateTime.getJavaTime(), value);
    }

    public Map<Long, Float> getVibOverTime() {
        return vibOverTime;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getDefectiveProducts() {
        return defectiveProducts;
    }

    public void setDefectiveProducts(int defectiveProducts) {
        this.defectiveProducts = defectiveProducts;
        this.acceptedProducts = (int) amountToProduce - defectiveProducts;
    }

    public void setStateStartTime(long stateStartTime) {
        this.stateStartTime = stateStartTime;
    }

    public long getStateStartTime() {
        return stateStartTime;
    }

    public void setBatchStartTime(long batchStartTime) {
        this.batchStartTime = batchStartTime;
    }

    public long getBatchStartTime() {
        return batchStartTime;
    }

    class IncorrectMachineSpeedException extends Exception {
        public IncorrectMachineSpeedException(String errorMessage) {
            super(errorMessage);
        }
    }

    public void addStateChangeDuration(long time, String state){
        stateDurationTime.put(state,time);
    }

    public Map<String, Long> getStateDurationTime() {
        return stateDurationTime;
    }

    public void setOee() {
        double plannedProductionTime = (this.amountToProduce / this.machineSpeed) * 60;
        double idealCycleTime = plannedProductionTime / this.amountToProduce;

        this.oee = ((this.acceptedProducts * idealCycleTime) / plannedProductionTime) * 100;
    }

    public double getOee() {
        return oee;
    }

    public void setProductionTime(long time){
        productionTime =  time - getBatchStartTime();
    }

    @Override
    public String toString() {
        return "Batch{" +
                "id=" + id +
                ", productType=" + productType +
                ", amountToProduce=" + amountToProduce +
                ", stateDurationTime=" + stateDurationTime +
                ", machineSpeed=" + machineSpeed +
                ", running=" + running +
                ", amountProduced=" + amountProduced +
                ", defectiveProducts=" + defectiveProducts +
                ", acceptedProducts=" + acceptedProducts +
                ", stateStartTime=" + stateStartTime +
                ", batchStartTime=" + batchStartTime +
                ", oee=" + oee +
                '}';
    }

    public String json(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
