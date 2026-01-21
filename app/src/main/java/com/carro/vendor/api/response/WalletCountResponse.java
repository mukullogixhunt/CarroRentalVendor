package com.carro.vendor.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.carro.vendor.api.response.commonResponse.BaseResponse;

public class WalletCountResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private Integer data;

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }
}
