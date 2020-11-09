package dk.bierproductie.opc_ua_client.core;

import dk.bierproductie.opc_ua_client.enums.Products;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BatchTest {

    Batch batch;

    @BeforeEach
    void setUp() {
        batch = new Batch(1,Products.PILSNER,800,1000);
        batch = new Batch(1, Products.PILSNER, 500, 1000);
    }

    @Test
    void getId() {
        assertEquals(batch.getId(), 1);
    }

    @Test
    void getProductType() {
        assertEquals(batch.getProductType(), Products.PILSNER.ordinal());
    }

    @Test
    void getMachineSpeed() {
        assertEquals(batch.getMachineSpeed(), 500);
    }

    @Test
    void getAmountToProduce() {
        assertEquals(batch.getAmountToProduce(),1000);
    }
}