package com.carro.vendor.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.carro.vendor.api.response.commonResponse.BaseResponse;
import com.carro.vendor.model.DriverListModel;

import java.util.List;

public class DriverListResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private List<DriverListModel> data;

    public List<DriverListModel> getData() {
        return data;
    }

    public void setData(List<DriverListModel> data) {
        this.data = data;
    }
}
