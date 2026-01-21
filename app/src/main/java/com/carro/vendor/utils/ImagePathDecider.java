package com.carro.vendor.utils;


import com.carro.vendor.BuildConfig;

public class ImagePathDecider {

    public static String getUserImagePath(){
        return BuildConfig.BASE_IMAGE_URL+"vendor/";

    }

    public static String getBannerImagePath(){
        return BuildConfig.BASE_IMAGE_URL+"apps/";}
    public static String getDriverImagePath(){
        return BuildConfig.BASE_IMAGE_URL+"driver/";
    }
    public static String getCarImagePath(){
        return BuildConfig.BASE_IMAGE_URL+"cars/";
    }
    public static String getNotificationImagePath(){
        return BuildConfig.BASE_IMAGE_URL+"Notification/";
    }
    public static String getSliderImagePath(){
        return BuildConfig.BASE_IMAGE_URL+"slider/";
    }

}
