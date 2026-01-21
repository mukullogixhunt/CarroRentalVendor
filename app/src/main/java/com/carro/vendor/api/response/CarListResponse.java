package com.carro.vendor.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.carro.vendor.api.response.commonResponse.BaseResponse;
import com.carro.vendor.model.CarListModel;

import java.util.List;

public class CarListResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private List<CarListModel> data;

    public List<CarListModel> getData() {
        return data;
    }

    public void setData(List<CarListModel> data) {
        this.data = data;
    }
}
