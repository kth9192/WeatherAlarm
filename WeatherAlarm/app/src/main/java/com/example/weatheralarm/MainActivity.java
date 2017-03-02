package com.example.weatheralarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements weatherView , beaconInfo{

    public static String TAG = MainActivity.class.getName();

    private WeatherPresenterImpl weatherPresenterImpl;

    private BeaconManager beaconManager;
    private Region region;

    private TextView tvId;
    int flag;

    private boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvId = (TextView) findViewById(R.id.tvId);

        weatherPresenterImpl = new WeatherPresenterImpl(this);

        beaconSearch();

    }



    public void initUIforSunny(HashMap<String , String> viewMap){

        RelativeLayout relativeLayout;

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

    public void  initUIforSnow(HashMap<String , String> viewMap){
        RelativeLayout relativeLayout;

        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
        relativeLayout.setBackgroundResource(R.drawable.snow_bg);

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

        Glide.with(this).load(R.drawable.snow).into(weatherImg);
    }

    public void beaconSearch() {

        flag = 0;
        beaconManager = new BeaconManager(this);

        // add this below:
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    Beacon nearestBeacon = list.get(0);
                    Log.d(TAG, "비콘 수신감도: " + nearestBeacon.getRssi());
                    tvId.setText(nearestBeacon.getRssi() + "");

                    if(!isConnected && nearestBeacon.getRssi() > -75 ){
                        isConnected = true;
                        weatherPresenterImpl.checkWeather();

                    }
                    else if(nearestBeacon.getRssi() < -90){
                        flag = 0;
                        Toast.makeText(MainActivity.this, "연결종료", Toast.LENGTH_SHORT).show();
                        isConnected = false;
                    }

                }
            }
        });

        region = new Region("ranged region",
                UUID.fromString(beaconID), MajorCode , MinorCode); // 본인이 연결할 Beacon의 ID와 Major / Minor Code를 알아야 한다.
    }

    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }

    @Override
    protected void onPause() {
//        beaconManager.stopRanging(region);
        super.onPause();
    }



}
