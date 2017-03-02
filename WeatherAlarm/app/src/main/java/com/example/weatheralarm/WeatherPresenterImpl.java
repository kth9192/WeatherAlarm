package com.example.weatheralarm;

import java.util.HashMap;

/**
 * Created by kth919 on 2017-02-24.
 */

public class WeatherPresenterImpl implements weatherPresenter, weatherInteractor.weatherUiInteractor{

    private String TAG = WeatherPresenterImpl.class.getName();

    private weatherView mweatherView;
    private WeatherModel weatherModel;

    public WeatherPresenterImpl(weatherView mweatherView){
        this.mweatherView = mweatherView;
        weatherModel = new WeatherModel(this);
    }


    @Override
    public void checkWeather() {
        weatherModel.checkWeatherData();
    }

    @Override
    public void CallWeatherUIforSunny(HashMap<String, String> viewMap) {
        mweatherView.initUIforSunny(viewMap);
    }

    @Override
    public void CallWeatherUIforRainy(HashMap<String, String> viewMap) {
        mweatherView.initUIforRainy(viewMap);
    }

    @Override
    public void CallWeatherUIforSnow(HashMap<String, String> viewMap) {
        mweatherView.initUIforSnow(viewMap);

    }
}
