package com.carro.vendor.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckBlockModel {

	@SerializedName("response")
	private String response;

	@SerializedName("message")
	private String message;

	@SerializedName("user")
	private List<UserDetailModel> user;

	public String getResponse(){
		return response;
	}

	public String getMessage(){
		return message;
	}

	public List<UserDetailModel> getUser(){
		return user;
	}
}