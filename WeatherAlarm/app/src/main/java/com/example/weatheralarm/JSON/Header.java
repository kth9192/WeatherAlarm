package com.example.weatheralarm.JSON;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kth919 on 2017-02-28.
 */

public class Header {

    @SerializedName("resultCode")
    private String resultCode;

    public String getResultCode() {
        return resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    @SerializedName("resultMsg")
    private String resultMsg;



}
