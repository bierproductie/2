package dk.bierproductie.opc_ua_client.core;

import dk.bierproductie.opc_ua_client.enums.Products;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Batch {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private int id;
    private int productType;
    private float machineSpeed;
    private float amountToProduce;
    //private int defectiveProducts;
    //private int acceptedProducts;
    //private double productionTime;
    //private oee OEE
    //private errorFunction ErrorFunction
    //private int totalProductAmount
    //private map<double,float> tempOverTime

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
    }

    public int getId() {
        return id;
    }

    public int getProductType() {
        return productType;
    }

    public float getMachineSpeed() {
        return machineSpeed;
    }

    public float getAmountToProduce() {
        return amountToProduce;
    }

    class IncorrectMachineSpeedException extends Exception {
        public IncorrectMachineSpeedException(String errorMessage) {
            super(errorMessage);
        }
    }
}