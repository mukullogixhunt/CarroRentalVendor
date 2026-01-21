package com.carro.vendor.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarNameModel {

    @SerializedName("m_model_id")
    @Expose
    private String mModelId;
    @SerializedName("m_model_title")
    @Expose
    private String mModelTitle;
    @SerializedName("m_model_brand")
    @Expose
    private String mModelBrand;

    @NonNull
    @Override
    public String toString() {
        return mModelTitle;

    }

    public CarNameModel(String mModelTitle) {
        this.mModelTitle = mModelTitle;
    }

    public String getmModelId() {
        return mModelId;
    }

    public void setmModelId(String mModelId) {
        this.mModelId = mModelId;
    }

    public String getmModelTitle() {
        return mModelTitle;
    }

    public void setmModelTitle(String mModelTitle) {
        this.mModelTitle = mModelTitle;
    }

    public String getmModelBrand() {
        return mModelBrand;
    }

    public void setmModelBrand(String mModelBrand) {
        this.mModelBrand = mModelBrand;
    }
}
