package com.example.weatheralarm.JSON;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kth919 on 2017-02-23.
 */

public class JsonList {

    @SerializedName("body")
    private Body body;

    public Body getBody() {
        return body;
    }

}
