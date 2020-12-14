package dk.bierproductie.opc_ua_client.core;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class BatchData {
    @SerializedName("batch_id")
    private int batchId;
    @SerializedName("measurement_ts")
    private String msTime;
    @SerializedName("temperature")
    private float temperature;
    @SerializedName("humidity")
    private float humidity;
    @SerializedName("vibration")
    private float vibration;
    @SerializedName("produced")
    private float produced;
    @SerializedName("state")
    private int state;
    @SerializedName("rejected")
    private int rejected;

    public BatchData(int batchId, int state) {
        this.batchId = batchId;
        this.state = state;
    }

    public void setMsTime(String msTime) {
        this.msTime = msTime;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public void setVibration(float vibration) {
        this.vibration = vibration;
    }

    public void setProduced(float produced) {
        this.produced = produced;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setRejected(int rejected) {
        this.rejected = rejected;
    }

    public int getBatchId() {
        return batchId;
    }

    public String getMsTime() {
        return msTime;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getVibration() {
        return vibration;
    }

    public float getProduced() {
        return produced;
    }

    public int getState() {
        return state;
    }

    public int getRejected() {
        return rejected;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
