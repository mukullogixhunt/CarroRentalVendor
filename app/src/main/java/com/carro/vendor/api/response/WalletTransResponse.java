package com.carro.vendor.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.carro.vendor.api.response.commonResponse.BaseResponse;
import com.carro.vendor.model.WalletTransModel;

import java.util.List;

public class WalletTransResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private List<WalletTransModel> data;

    public List<WalletTransModel> getData() {
        return data;
    }

    public void setData(List<WalletTransModel> data) {
        this.data = data;
    }
}
