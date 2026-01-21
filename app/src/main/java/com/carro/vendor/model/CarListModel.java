package com.carro.vendor.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarListModel {

    @SerializedName("m_car_id")
    @Expose
    private String mCarId;
    @SerializedName("m_car_name")
    @Expose
    private String mCarName;
    @SerializedName("m_car_number")
    @Expose
    private String mCarNumber;
    @SerializedName("m_car_type")
    @Expose
    private String mCarType;
    @SerializedName("m_ctype_title")
    @Expose
    private String mCarTypeName;
    @SerializedName("m_car_brand")
    @Expose
    private String mCarBrand;
    @SerializedName("m_car_model")
    @Expose
    private String mCarModel;
    @SerializedName("m_car_vendor")
    @Expose
    private String mCarVendor;
    @SerializedName("m_car_fuel")
    @Expose
    private String mCarFuel;
    @SerializedName("m_car_img")
    @Expose
    private String mCarImg;
    @SerializedName("m_car_seat")
    @Expose
    private String mCarSeat;
    @SerializedName("m_car_luggage")
    @Expose
    private String mCarLuggage;
    @SerializedName("m_car_ac")
    @Expose
    private String mCarAc;
    @SerializedName("m_car_drivetype")
    @Expose
    private String mCarDrivetype;
    @SerializedName("m_car_reg_certi")
    @Expose
    private String mCarRegCerti;
    @SerializedName("m_car_iss_certi")
    @Expose
    private String mCarIssCerti;
    @SerializedName("m_car_permit")
    @Expose
    private String mCarPermit;
    @SerializedName("m_car_ownercar_img")
    @Expose
    private String mCarOwnercarImg;
    @SerializedName("m_car_status")
    @Expose
    private String mCarStatus;
    @SerializedName("m_car_addedon")
    @Expose
    private String mCarAddedon;
    @SerializedName("m_car_updatedon")
    @Expose
    private String mCarUpdatedon;


    public String getmCarId() {
        return mCarId;
    }

    public void setmCarId(String mCarId) {
        this.mCarId = mCarId;
    }

    public String getmCarName() {
        return mCarName;
    }

    public void setmCarName(String mCarName) {
        this.mCarName = mCarName;
    }


    public String getmCarTypeName() {
        return mCarTypeName;
    }

    public void setmCarTypeName(String mCarTypeName) {
        this.mCarTypeName = mCarTypeName;
    }

    public String getmCarNumber() {
        return mCarNumber;
    }

    public void setmCarNumber(String mCarNumber) {
        this.mCarNumber = mCarNumber;
    }

    public String getmCarType() {
        return mCarType;
    }

    public void setmCarType(String mCarType) {
        this.mCarType = mCarType;
    }

    public String getmCarBrand() {
        return mCarBrand;
    }

    public void setmCarBrand(String mCarBrand) {
        this.mCarBrand = mCarBrand;
    }

    public String getmCarModel() {
        return mCarModel;
    }

    public void setmCarModel(String mCarModel) {
        this.mCarModel = mCarModel;
    }

    public String getmCarVendor() {
        return mCarVendor;
    }

    public void setmCarVendor(String mCarVendor) {
        this.mCarVendor = mCarVendor;
    }

    public String getmCarFuel() {
        return mCarFuel;
    }

    public void setmCarFuel(String mCarFuel) {
        this.mCarFuel = mCarFuel;
    }

    public String getmCarImg() {
        return mCarImg;
    }

    public void setmCarImg(String mCarImg) {
        this.mCarImg = mCarImg;
    }

    public String getmCarSeat() {
        return mCarSeat;
    }

    public void setmCarSeat(String mCarSeat) {
        this.mCarSeat = mCarSeat;
    }

    public String getmCarLuggage() {
        return mCarLuggage;
    }

    public void setmCarLuggage(String mCarLuggage) {
        this.mCarLuggage = mCarLuggage;
    }

    public String getmCarDrivetype() {
        return mCarDrivetype;
    }

    public void setmCarDrivetype(String mCarDrivetype) {
        this.mCarDrivetype = mCarDrivetype;
    }

    public String getmCarRegCerti() {
        return mCarRegCerti;
    }

    public void setmCarRegCerti(String mCarRegCerti) {
        this.mCarRegCerti = mCarRegCerti;
    }

    public String getmCarIssCerti() {
        return mCarIssCerti;
    }

    public void setmCarIssCerti(String mCarIssCerti) {
        this.mCarIssCerti = mCarIssCerti;
    }

    public String getmCarPermit() {
        return mCarPermit;
    }

    public void setmCarPermit(String mCarPermit) {
        this.mCarPermit = mCarPermit;
    }

    public String getmCarOwnercarImg() {
        return mCarOwnercarImg;
    }

    public void setmCarOwnercarImg(String mCarOwnercarImg) {
        this.mCarOwnercarImg = mCarOwnercarImg;
    }

    public String getmCarStatus() {
        return mCarStatus;
    }

    public void setmCarStatus(String mCarStatus) {
        this.mCarStatus = mCarStatus;
    }

    public String getmCarAddedon() {
        return mCarAddedon;
    }

    public void setmCarAddedon(String mCarAddedon) {
        this.mCarAddedon = mCarAddedon;
    }

    public String getmCarUpdatedon() {
        return mCarUpdatedon;
    }

    public void setmCarUpdatedon(String mCarUpdatedon) {
        this.mCarUpdatedon = mCarUpdatedon;
    }

    public String getmCarAc() {
        return mCarAc;
    }

    public void setmCarAc(String mCarAc) {
        this.mCarAc = mCarAc;
    }

    @NonNull
    @Override
    public String toString() {
        return mCarName;

    }

    public CarListModel(String mCarName) {
        this.mCarName = mCarName;
    }


}
