package com.carro.vendor.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.carro.vendor.api.response.commonResponse.BaseResponse;
import com.carro.vendor.model.DriverOtpModel;

public class DriverOtpResponse extends BaseResponse {

    @SerializedName("user")
    @Expose
    private DriverOtpModel user;

    public DriverOtpModel getUser() {
        return user;
    }

    public void setUser(DriverOtpModel user) {
        this.user = user;
    }
}
