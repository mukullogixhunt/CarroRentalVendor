package com.carro.vendor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletTransModel {
    @SerializedName("m_wallet_id")
    @Expose
    private String mWalletId;
    @SerializedName("m_wallet_vendor")
    @Expose
    private String mWalletVendor;
    @SerializedName("m_wallet_amt")
    @Expose
    private String mWalletAmt;
    @SerializedName("m_wallet_cd")
    @Expose
    private String mWalletCd;
    @SerializedName("m_wallet_date")
    @Expose
    private String mWalletDate;
    @SerializedName("m_wallet_time")
    @Expose
    private String mWalletTime;

    public String getmWalletId() {
        return mWalletId;
    }

    public void setmWalletId(String mWalletId) {
        this.mWalletId = mWalletId;
    }

    public String getmWalletVendor() {
        return mWalletVendor;
    }

    public void setmWalletVendor(String mWalletVendor) {
        this.mWalletVendor = mWalletVendor;
    }

    public String getmWalletAmt() {
        return mWalletAmt;
    }

    public void setmWalletAmt(String mWalletAmt) {
        this.mWalletAmt = mWalletAmt;
    }

    public String getmWalletCd() {
        return mWalletCd;
    }

    public void setmWalletCd(String mWalletCd) {
        this.mWalletCd = mWalletCd;
    }

    public String getmWalletDate() {
        return mWalletDate;
    }

    public void setmWalletDate(String mWalletDate) {
        this.mWalletDate = mWalletDate;
    }

    public String getmWalletTime() {
        return mWalletTime;
    }

    public void setmWalletTime(String mWalletTime) {
        this.mWalletTime = mWalletTime;
    }
}
