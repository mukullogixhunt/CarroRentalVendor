package com.carro.vendor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecommendedModel {
    @SerializedName("m_wallet_amt")
    @Expose
    private String mWalletAmt;

    public String getmWalletAmt() {
        return mWalletAmt;
    }

    public void setmWalletAmt(String mWalletAmt) {
        this.mWalletAmt = mWalletAmt;
    }
}
