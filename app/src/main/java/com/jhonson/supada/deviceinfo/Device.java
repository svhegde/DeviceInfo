/**
 * Device.java
 *
 * @author Supada Hegde
 * @version 1.0
 */

package com.jhonson.supada.deviceinfo;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Device {

    @SerializedName("id")
    @Expose
    private int id;
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
    private boolean isCheckedOut;

    /**
     *
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     *     The device
     */
    public String getDevice() {
        return device;
    }

    /**
     *
     * @param device
     *     The device
     */
    public void setDevice(String device) {
        this.device = device;
    }

    /**
     *
     * @return
     *     The os
     */
    public String getOs() {
        return os;
    }

    /**
     *
     * @param os
     *     The os
     */
    public void setOs(String os) {
        this.os = os;
    }

    /**
     *
     * @return
     *     The manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     *
     * @param manufacturer
     *     The manufacturer
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     *
     * @return
     *     The lastCheckedOutDate
     */
    public String getLastCheckedOutDate() {
        return lastCheckedOutDate;
    }

    /**
     *
     * @param lastCheckedOutDate
     *     The lastCheckedOutDate
     */
    public void setLastCheckedOutDate(String lastCheckedOutDate) {
        this.lastCheckedOutDate = lastCheckedOutDate;
    }

    /**
     *
     * @return
     *     The lastCheckedOutBy
     */
    public String getLastCheckedOutBy() {
        return lastCheckedOutBy;
    }

    /**
     *
     * @param lastCheckedOutBy
     *     The lastCheckedOutBy
     */
    public void setLastCheckedOutBy(String lastCheckedOutBy) {
        this.lastCheckedOutBy = lastCheckedOutBy;
    }

    /**
     *
     * @return
     *     The isCheckedOut
     */
    public boolean isIsCheckedOut() {
        return isCheckedOut;
    }

    /**
     *
     * @param isCheckedOut
     *     The isCheckedOut
     */
    public void setIsCheckedOut(boolean isCheckedOut) {
        this.isCheckedOut = isCheckedOut;
    }

}
