package com.carro.vendor.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarTypeModel {

    @SerializedName("m_ctype_id")
    @Expose
    private String mCtypeId;
    @SerializedName("m_ctype_title")
    @Expose
    private String mCtypeTitle;
    @SerializedName("m_ctype_img")
    @Expose
    private String mCtypeImg;


    @NonNull
    @Override
    public String toString() {
        return mCtypeTitle;

    }

    public CarTypeModel(String mCtypeTitle) {
        this.mCtypeTitle = mCtypeTitle;
    }

    public String getmCtypeId() {
        return mCtypeId;
    }

    public void setmCtypeId(String mCtypeId) {
        this.mCtypeId = mCtypeId;
    }

    public String getmCtypeTitle() {
        return mCtypeTitle;
    }

    public void setmCtypeTitle(String mCtypeTitle) {
        this.mCtypeTitle = mCtypeTitle;
    }

    public String getmCtypeImg() {
        return mCtypeImg;
    }

    public void setmCtypeImg(String mCtypeImg) {
        this.mCtypeImg = mCtypeImg;
    }
}
