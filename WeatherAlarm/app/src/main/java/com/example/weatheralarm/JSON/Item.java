package com.example.weatheralarm.JSON;

/**
 * Created by kth919 on 2017-02-23.
 */

public class Item {

    private int baseDate;
    private String baseTime;
    private String category;

    private int fcstDate;
    private String fcstTime;
    private double fcstValue;
    private int nx;
    private int ny;

    public int getNy() {
        return ny;
    }

    public void setNy(int ny) {
        this.ny = ny;
    }

    public int getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(int baseDate) {
        this.baseDate = baseDate;
    }

    public String getBaseTime() {
        return baseTime;
    }

    public void setBaseTime(String baseTime) {
        this.baseTime = baseTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getFcstDate() {
        return fcstDate;
    }

    public void setFcstDate(int fcstDate) {
        this.fcstDate = fcstDate;
    }

    public String getFcstTime() {
        return fcstTime;
    }

    public void setFcstTime(String fcstTime) {
        this.fcstTime = fcstTime;
    }

    public double getFcstValue() {
        return fcstValue;
    }

    public void setFcstValue(double fcstValue) {
        this.fcstValue = fcstValue;
    }

    public int getNx() {
        return nx;
    }

    public void setNx(int nx) {
        this.nx = nx;
    }


}
