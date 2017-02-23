package com.example.weatheralarm.JSON;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by kth919 on 2017-02-23.
 */

public class Items {

    @SerializedName("item")
    private ArrayList<Item> item;

    public ArrayList<Item> getItem() {
        return item;
    }

}
