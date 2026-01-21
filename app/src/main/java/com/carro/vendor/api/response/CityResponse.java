package com.carro.vendor.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.carro.vendor.api.response.commonResponse.BaseResponse;
import com.carro.vendor.model.CityModel;


import java.util.List;

public class CityResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private List<CityModel> data;

    public List<CityModel> getData() {
        return data;
    }

    public void setData(List<CityModel> data) {
        this.data = data;
    }
}
