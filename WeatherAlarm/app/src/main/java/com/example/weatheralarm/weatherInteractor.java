package com.example.weatheralarm;

import java.util.HashMap;

/**
 * Created by kth919 on 2017-02-24.
 */

public interface weatherInteractor {

    void checkWeatherData();

    interface  weatherUiInteractor{

            void CallWeatherUI(HashMap<String, String> viewMap);

    }

}
