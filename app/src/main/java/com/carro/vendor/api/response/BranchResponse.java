package com.carro.vendor.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.carro.vendor.api.response.commonResponse.BaseResponse;
import com.carro.vendor.model.BranchModel;


import java.util.List;

public class BranchResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private List<BranchModel> data;

    public List<BranchModel> getData() {
        return data;
    }

    public void setData(List<BranchModel> data) {
        this.data = data;
    }
}
