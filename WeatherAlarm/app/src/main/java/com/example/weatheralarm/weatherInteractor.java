package com.example.weatheralarm;

import java.util.HashMap;

/**
 * Created by kth919 on 2017-02-24.
 */

public interface weatherInteractor {

    void checkWeatherData();

    interface  weatherUiInteractor{
        void CallWeatherUIforSunny(HashMap<String, String > viewMap);
        void CallWeatherUIforRainy(HashMap<String, String > viewMap);

    }

}
