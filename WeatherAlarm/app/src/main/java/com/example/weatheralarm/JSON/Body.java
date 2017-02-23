package com.example.weatheralarm.JSON;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kth919 on 2017-02-23.
 */

public class Body {

    @SerializedName("items")
    private Items items;

    public Items getItems() {
        return items;
    }
}
