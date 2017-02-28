package com.example.weatheralarm;

import java.util.HashMap;

/**
 * Created by kth919 on 2017-02-24.
 */

public interface weatherView {

    void initUIforSunny(HashMap<String , String> viewMap);
    void initUIforRainy(HashMap<String , String> viewMap);

}
