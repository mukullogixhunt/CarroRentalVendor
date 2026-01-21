package com.carro.vendor.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.carro.vendor.api.response.commonResponse.BaseResponse;
import com.carro.vendor.model.UserDetailModel;

import java.util.List;

public class UserDetailResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private List<UserDetailModel> data;

    public List<UserDetailModel> getData() {
        return data;
    }

    public void setData(List<UserDetailModel> data) {
        this.data = data;
    }
}
