package dk.bierproductie.opc_ua_client.enums;

public enum Products {
    PILSNER("Pilsner"),
    WHEAT("Wheat"),
    IPA("IPA"),
    Stout("Stout"),
    ALE("Ale"),
    ALCOHOL_FREE("Alcohol Free");

    public final String output;

    Products(String output) {
        this.output = output;
    }
}
