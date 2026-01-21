package com.carro.vendor.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarBrandModel {
    @SerializedName("m_brand_id")
    @Expose
    private String mBrandId;
    @SerializedName("m_brand_title")
    @Expose
    private String mBrandTitle;

    @NonNull
    @Override
    public String toString() {
        return mBrandTitle;

    }

    public CarBrandModel(String mBrandTitle) {
        this.mBrandTitle = mBrandTitle;
    }

    public String getmBrandId() {
        return mBrandId;
    }

    public void setmBrandId(String mBrandId) {
        this.mBrandId = mBrandId;
    }

    public String getmBrandTitle() {
        return mBrandTitle;
    }

    public void setmBrandTitle(String mBrandTitle) {
        this.mBrandTitle = mBrandTitle;
    }
}
