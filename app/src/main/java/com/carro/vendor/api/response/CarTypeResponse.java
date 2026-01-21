package com.carro.vendor.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.carro.vendor.api.response.commonResponse.BaseResponse;
import com.carro.vendor.model.CarTypeModel;

import java.util.List;

public class CarTypeResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private List<CarTypeModel> data;

    public List<CarTypeModel> getData() {
        return data;
    }

    public void setData(List<CarTypeModel> data) {
        this.data = data;
    }
}
