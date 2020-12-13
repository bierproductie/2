package dk.bierproductie.opc_ua_client.core;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BatchDataTest {

    public static final int INT = 12;
    static BatchData batchData;

    @BeforeAll
    static void setup() {
        batchData = new BatchData(1);
        assertEquals(1,batchData.getBatchId());
    }

    @Test
    @Order(1)
    void setMsTime() {
        batchData.setMsTime("time");
        assertEquals("time",batchData.getMsTime());
    }

    @Test
    @Order(2)
    void setTemperature() {
        batchData.setTemperature(INT);
        assertEquals(INT,batchData.getTemperature());
    }

    @Test
    @Order(3)
    void setHumidity() {
        batchData.setHumidity(INT);
        assertEquals(INT,batchData.getHumidity());
    }

    @Test
    @Order(4)
    void setVibration() {
        batchData.setVibration(INT);
        assertEquals(INT,batchData.getVibration());
    }

    @Test
    @Order(5)
    void setProduced() {
        batchData.setProduced(INT);
        assertEquals(INT,batchData.getProduced());
    }

    @Test
    @Order(6)
    void setState() {
        batchData.setState(INT);
        assertEquals(INT,batchData.getState());
    }

    @Test
    @Order(7)
    void setRejected() {
        batchData.setRejected(INT);
        assertEquals(INT,batchData.getRejected());
    }

    @Test
    @Order(8)
    void toJson() {
        assertEquals("{\"batch_id\":1,\"measurement_ts\":\"time\",\"temperature\":12.0,\"humidity\":12.0,\"vibration\":12.0,\"produced\":12.0,\"state\":12,\"rejected\":12}", batchData.toJson());
    }
}