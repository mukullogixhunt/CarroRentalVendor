package com.carro.vendor.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HomeData{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("response")
	private String response;

	public List<DataItem> getData(){
		return data;
	}

	public String getResponse(){
		return response;
	}

    public static class DataItem{

        @SerializedName("m_hv_adv_img_sh")
        private String mHvAdvImgSh;

        @SerializedName("m_hv_tagline")
        private String mHvTagline;

        @SerializedName("m_hv_adv_img")
        private String mHvAdvImg;

        @SerializedName("m_hv_icon")
        private String mHvIcon;

        @SerializedName("m_hv_id")
        private String mHvId;

        public String getMHvAdvImgSh(){
            return mHvAdvImgSh;
        }

        public String getMHvTagline(){
            return mHvTagline;
        }

        public String getMHvAdvImg(){
            return mHvAdvImg;
        }

        public String getMHvIcon(){
            return mHvIcon;
        }

        public String getMHvId(){
            return mHvId;
        }
    }
}