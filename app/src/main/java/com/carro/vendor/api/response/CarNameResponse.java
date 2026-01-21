package com.carro.vendor.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.carro.vendor.api.response.commonResponse.BaseResponse;
import com.carro.vendor.model.CarNameModel;

import java.util.List;

public class CarNameResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private List<CarNameModel> data;

    public List<CarNameModel> getData() {
        return data;
    }

    public void setData(List<CarNameModel> data) {
        this.data = data;
    }
}
