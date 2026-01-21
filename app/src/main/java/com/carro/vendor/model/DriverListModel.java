package com.carro.vendor.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverListModel {


    @SerializedName("m_driver_id")
    @Expose
    private String mDriverId;
    @SerializedName("m_driver_vendor")
    @Expose
    private String mDriverVendor;
    @SerializedName("m_driver_name")
    @Expose
    private String mDriverName;
    @SerializedName("m_driver_mobile")
    @Expose
    private String mDriverMobile;
    @SerializedName("m_driver_img")
    @Expose
    private String mDriverImg;
    @SerializedName("m_driver_otp")
    @Expose
    private String mDriverOtp;
    @SerializedName("m_driver_dlno")
    @Expose
    private String mDriverDrivelic;
   /* @SerializedName("m_driver_drivelic")
    @Expose
    private String mDriverDrivelic;
    @SerializedName("m_driver_drivelic_expdate")
    @Expose
    private String mDriverDrivelicExpdate;  */

    @SerializedName("m_driver_dl_exdate")
    @Expose
    private String mDriverDrivelicExpdate;
    @SerializedName("m_driver_police_verify")
    @Expose
    private String mDriverPoliceVerify;
    @SerializedName("m_driver_state")
    @Expose
    private String mDriverState;
    @SerializedName("m_driver_city")
    @Expose
    private String mDriverCity;
    @SerializedName("m_driver_status")
    @Expose
    private String mDriverStatus;
    @SerializedName("m_driver_addedon")
    @Expose
    private String mDriverAddedon;
    @SerializedName("m_driver_updatedon")
    @Expose
    private String mDriverUpdatedon;

    @NonNull
    @Override
    public String toString() {
        return mDriverName;

    }

    public DriverListModel(String mDriverName) {
        this.mDriverName = mDriverName;
    }

    public String getmDriverId() {
        return mDriverId;
    }

    public void setmDriverId(String mDriverId) {
        this.mDriverId = mDriverId;
    }

    public String getmDriverVendor() {
        return mDriverVendor;
    }

    public void setmDriverVendor(String mDriverVendor) {
        this.mDriverVendor = mDriverVendor;
    }

    public String getmDriverName() {
        return mDriverName;
    }

    public void setmDriverName(String mDriverName) {
        this.mDriverName = mDriverName;
    }

    public String getmDriverMobile() {
        return mDriverMobile;
    }

    public void setmDriverMobile(String mDriverMobile) {
        this.mDriverMobile = mDriverMobile;
    }

    public String getmDriverImg() {
        return mDriverImg;
    }

    public void setmDriverImg(String mDriverImg) {
        this.mDriverImg = mDriverImg;
    }

    public String getmDriverOtp() {
        return mDriverOtp;
    }

    public void setmDriverOtp(String mDriverOtp) {
        this.mDriverOtp = mDriverOtp;
    }

    public String getmDriverDrivelic() {
        return mDriverDrivelic;
    }

    public void setmDriverDrivelic(String mDriverDrivelic) {
        this.mDriverDrivelic = mDriverDrivelic;
    }

    public String getmDriverDrivelicExpdate() {
        return mDriverDrivelicExpdate;
    }

    public void setmDriverDrivelicExpdate(String mDriverDrivelicExpdate) {
        this.mDriverDrivelicExpdate = mDriverDrivelicExpdate;
    }

    public String getmDriverPoliceVerify() {
        return mDriverPoliceVerify;
    }

    public void setmDriverPoliceVerify(String mDriverPoliceVerify) {
        this.mDriverPoliceVerify = mDriverPoliceVerify;
    }

    public String getmDriverState() {
        return mDriverState;
    }

    public void setmDriverState(String mDriverState) {
        this.mDriverState = mDriverState;
    }

    public String getmDriverCity() {
        return mDriverCity;
    }

    public void setmDriverCity(String mDriverCity) {
        this.mDriverCity = mDriverCity;
    }

    public String getmDriverStatus() {
        return mDriverStatus;
    }

    public void setmDriverStatus(String mDriverStatus) {
        this.mDriverStatus = mDriverStatus;
    }

    public String getmDriverAddedon() {
        return mDriverAddedon;
    }

    public void setmDriverAddedon(String mDriverAddedon) {
        this.mDriverAddedon = mDriverAddedon;
    }

    public String getmDriverUpdatedon() {
        return mDriverUpdatedon;
    }

    public void setmDriverUpdatedon(String mDriverUpdatedon) {
        this.mDriverUpdatedon = mDriverUpdatedon;
    }
}
