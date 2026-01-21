package com.carro.vendor.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.carro.vendor.api.response.commonResponse.BaseResponse;
import com.carro.vendor.model.CarBrandModel;

import java.util.List;

public class CarBrandResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private List<CarBrandModel> data;

    public List<CarBrandModel> getData() {
        return data;
    }

    public void setData(List<CarBrandModel> data) {
        this.data = data;
    }
}
