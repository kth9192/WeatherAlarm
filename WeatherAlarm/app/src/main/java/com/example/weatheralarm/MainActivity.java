package com.example.weatheralarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weatheralarm.JSON.Item;
import com.example.weatheralarm.JSON.ResponseJson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements weatherView{

    public static String TAG = MainActivity.class.getName();

    private WeatherPresenterImpl weatherPresenterImpl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        weatherPresenterImpl = new WeatherPresenterImpl(this);

        weatherPresenterImpl.checkWeather();

    }

    public void initUIforSunny(HashMap<String , String> viewMap){

        RelativeLayout relativeLayout;

        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
        relativeLayout.setBackgroundResource(R.drawable.sunny_bg);

        TextView temp = (TextView) findViewById(R.id.Temperature);
        TextView tempTxt = (TextView) findViewById(R.id.tempTxt);
        TextView rainPer = (TextView) findViewById(R.id.rainPer);
        TextView rainTxt = (TextView) findViewById(R.id.rainTxt);
        TextView cloudTxt = (TextView) findViewById(R.id.cloudTxt);

        temp.setText("온도 : " + viewMap.get("T3H"));

        tempTxt.setText(viewMap.get("TempTxt"));

        rainPer.setText("강수확률 : " + viewMap.get("POP"));

        rainTxt.setText(viewMap.get("rainState"));

        cloudTxt.setText(viewMap.get("SkyState"));

        ImageView weatherImg;
        weatherImg = (ImageView) findViewById(R.id.weatherImg);

        Glide.with(this).load(R.drawable.sunny).into(weatherImg);
    }

    public void initUIforRainy(HashMap<String , String> viewMap){

        RelativeLayout relativeLayout;

        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
        relativeLayout.setBackgroundResource(R.drawable.rain_bg);

        TextView temp = (TextView) findViewById(R.id.Temperature);
        TextView tempTxt = (TextView) findViewById(R.id.tempTxt);
        TextView rainPer = (TextView) findViewById(R.id.rainPer);
        TextView rainTxt = (TextView) findViewById(R.id.rainTxt);
        TextView cloudTxt = (TextView) findViewById(R.id.cloudTxt);

        temp.setText("온도 : " + viewMap.get("T3H"));

        tempTxt.setText(viewMap.get("TempTxt"));

        rainPer.setText("강수확률 : " + viewMap.get("POP"));

        rainTxt.setText(viewMap.get("rainState"));

        cloudTxt.setText(viewMap.get("SkyState"));

        ImageView weatherImg;
        weatherImg = (ImageView) findViewById(R.id.weatherImg);

        Glide.with(this).load(R.drawable.rain).into(weatherImg);
    }
}
