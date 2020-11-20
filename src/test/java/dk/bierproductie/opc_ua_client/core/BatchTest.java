package dk.bierproductie.opc_ua_client.core;

import dk.bierproductie.opc_ua_client.enums.Products;
import org.eclipse.milo.opcua.stack.core.types.builtin.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BatchTest {

    Batch batch;

    @BeforeEach
    void setUp() {
        batch = new Batch(1, Products.PILSNER, 800, 1000);
        batch = new Batch(1, Products.PILSNER, 500, 1000);
    }

    @Test
    void addToTempOverTime() {
        DateTime dateTime = new DateTime();
        Float testFloat = Float.valueOf(12);
        batch.addToTempOverTime(dateTime, testFloat);
        assertEquals(testFloat,batch.getTempOverTime().get(dateTime));
    }

    @Test
    void getId() {
        assertEquals(1, batch.getId());
    }

    @Test
    void getProductType() {
        assertEquals(Products.PILSNER.ordinal(), batch.getProductType());
    }

    @Test
    void getMachineSpeed() {
        assertEquals(500, batch.getMachineSpeed());
    }

    @Test
    void getAmountToProduce() {
        assertEquals(1000, batch.getAmountToProduce());
    }

    @Test
    void runningTests(){
        batch.setRunning(true);
        assertEquals(true,batch.isRunning());
    }

    @Test
    void testOEE(){
        batch.setOEE();
        assertEquals(0,batch.getOEE());
    }

    @Test
    void testDefectiveProducts(){
        batch.setDefectiveProducts(20);
        assertEquals(20,batch.getDefectiveProducts());
    }

    @Test
    void tostringTest() {
        assertNotNull(batch.toString());
    }
}