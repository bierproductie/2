package dk.bierproductie.opc_ua_client.core;

import dk.bierproductie.opc_ua_client.enums.Products;
import org.eclipse.milo.opcua.stack.core.types.builtin.DateTime;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Batch {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private float id;
    private float productType;
    private float machineSpeed;
    private float amountToProduce;
    private boolean running;
    //private int defectiveProducts;
    //private int acceptedProducts;
    //private double productionTime;
    //private oee OEE
    //private errorFunction ErrorFunction
    //private int totalProductAmount
    private Map<DateTime,Float> tempOverTime;

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

    public void addToTempOverTime(DateTime dateTime, Float value) {
        tempOverTime.put(dateTime,value);
    }

    public Map<DateTime, Float> getTempOverTime() {
        return tempOverTime;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    class IncorrectMachineSpeedException extends Exception {
        public IncorrectMachineSpeedException(String errorMessage) {
            super(errorMessage);
        }
    }
}