package com.carro.vendor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverOtpModel {
    @SerializedName("m_driver_id")
    @Expose
    private String mDriverId;

    public String getmDriverId() {
        return mDriverId;
    }

    public void setmDriverId(String mDriverId) {
        this.mDriverId = mDriverId;
    }
}
