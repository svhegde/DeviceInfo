/**
 * DeviceApiResponse.java
 *
 * @author Supada Hegde
 * @version 1.0
 */

package com.jhonson.supada.deviceinfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import retrofit2.Call;
import retrofit2.http.*;
import android.util.Log;

@Generated("org.jsonschema2pojo")
public class DeviceApiResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("device")
    @Expose
    private String device;
    @SerializedName("os")
    @Expose
    private String os;
    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;
    @SerializedName("lastCheckedOutDate")
    @Expose
    private String lastCheckedOutDate;
    @SerializedName("lastCheckedOutBy")
    @Expose
    private String lastCheckedOutBy;
    @SerializedName("isCheckedOut")
    @Expose
    private Boolean isCheckedOut;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The device
     */
    public String getDevice() {
        return device;
    }

    /**
     *
     * @param device
     * The device
     */
    public void setDevice(String device) {
        this.device = device;
    }

    /**
     *
     * @return
     * The os
     */
    public String getOs() {
        return os;
    }

    /**
     *
     * @param os
     * The os
     */
    public void setOs(String os) {
        this.os = os;
    }

    /**
     *
     * @return
     * The manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     *
     * @param manufacturer
     * The manufacturer
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     *
     * @return
     * The lastCheckedOutDate
     */
    public String getLastCheckedOutDate() {
        return lastCheckedOutDate;
    }

    /**
     *
     * @param lastCheckedOutDate
     * The lastCheckedOutDate
     */
    public void setLastCheckedOutDate(String lastCheckedOutDate) {
        this.lastCheckedOutDate = lastCheckedOutDate;
    }

    /**
     *
     * @return
     * The lastCheckedOutBy
     */
    public String getLastCheckedOutBy() {
        return lastCheckedOutBy;
    }

    /**
     *
     * @param lastCheckedOutBy
     * The lastCheckedOutBy
     */
    public void setLastCheckedOutBy(String lastCheckedOutBy) {
        this.lastCheckedOutBy = lastCheckedOutBy;
    }

    /**
     *
     * @return
     * The isCheckedOut
     */
    public Boolean getIsCheckedOut() {
        return isCheckedOut;
    }

    /**
     *
     * @param isCheckedOut
     * The isCheckedOut
     */
    public void setIsCheckedOut(Boolean isCheckedOut) {
        this.isCheckedOut = isCheckedOut;
    }

    public static DeviceApiResponse parseJSON(String response) {
        Log.d("API", "CALLED");
        Gson gson = new GsonBuilder().create();
        DeviceApiResponse deviceApiResponse = gson.fromJson(response, DeviceApiResponse.class);
        Log.d("API", "CALLED");
        return deviceApiResponse;
    }

    public interface DeviceInfoService {
        @GET("/devices")
        public Call<DeviceApiResponse> listDevices();
    }

}

