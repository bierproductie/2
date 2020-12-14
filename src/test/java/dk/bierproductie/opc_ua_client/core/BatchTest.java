package dk.bierproductie.opc_ua_client.core;

import com.google.gson.Gson;
import dk.bierproductie.opc_ua_client.enums.Products;
import org.eclipse.milo.opcua.stack.core.types.builtin.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BatchTest {

    Batch batch;

    @BeforeEach
    void setUp() {
        batch = new Batch(1, Products.PILSNER, 500, 1000);
    }

    @Test
    void wrongSpeed(){
        Batch testBatch = new Batch(1, Products.PILSNER, 800, 1000);
        assertEquals(Products.PILSNER.speedLimit, testBatch.getMachineSpeed());
        testBatch = new Batch(1, Products.PILSNER, -100, 1000);
        assertEquals(Products.PILSNER.speedLimit, testBatch.getMachineSpeed());
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
    void runningTests() {
        batch.setRunning(true);
        assertTrue(batch.isRunning());
    }

    @Test
    void testOEE() {
        batch.setOee();
        assertEquals(0, batch.getOee());
    }

    @Test
    void testDefectiveProducts() {
        batch.setDefectiveProducts(20);
        assertEquals(20, batch.getDefectiveProducts());
    }

    @Test
    void testAmountProduced() {
        batch.setAmountProduced(20);
        assertEquals(20, batch.getAmountProduced());
    }

    @Test
    void toStringTest() {
        assertNotNull(batch.toString());
    }

    @Test
    void batchStartTimeTest() {
        long dateTime = new DateTime().getJavaTime();
        batch.setBatchStartTime(dateTime);
        assertEquals(dateTime,batch.getBatchStartTime());
    }

    @Test
    void stateStartTimeTest() {
        long dateTime = new DateTime().getJavaTime();
        batch.setStateStartTime(dateTime);
        assertEquals(dateTime,batch.getStateStartTime());
    }

    @Test
    void serializeAndDeserializeBatchTest() {
        Gson gson = new Gson();
        String testJson = batch.json();
        Batch result = gson.fromJson(testJson,Batch.class);
        assertEquals(batch.getId(),result.getId());
    }

    @Test
    void setProductionTime() {
        DateTime dateTime = new DateTime();
        batch.setProductionTime(dateTime.getJavaTime());
        assertEquals(dateTime.getJavaTime(),batch.getProductionTime());
    }
}