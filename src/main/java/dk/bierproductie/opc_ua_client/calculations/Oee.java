package dk.bierproductie.opc_ua_client.calculations;

public class Oee {
    private final double goodCount;
    private final double idealCycleTime;
    private final double plannedProductionTime;

    public Oee(int goodCount, int idealCycleTime, int plannedProductionTime) {
        this.goodCount = goodCount;
        this.idealCycleTime = idealCycleTime;
        this.plannedProductionTime = plannedProductionTime;
    }

    public double calculate(){
        return (this.goodCount * this.idealCycleTime) / this.plannedProductionTime;
    }
}
