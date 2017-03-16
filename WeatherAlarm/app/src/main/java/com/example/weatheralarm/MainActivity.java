package com.example.weatheralarm;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.CollapsibleActionView;
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
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements weatherView , beaconInfo{

    public static String TAG = MainActivity.class.getName();

    private WeatherPresenterImpl weatherPresenterImpl;

    private BeaconManager beaconManager;
    private Region region;

    @BindView(R.id.tvId)
    TextView tvId;

    int flag;

    private boolean isConnected;

    @BindView(R.id.Temperature)
    TextView temp;

   @BindView(R.id.tempTxt)
    TextView tempTxt;

    @BindView(R.id.rainPer)
    TextView rainPer;

    @BindView(R.id.rainTxt)
    TextView rainTxt;

    @BindView(R.id.cloudTxt)
    TextView cloudTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart(){
        super.onStart();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        weatherPresenterImpl = new WeatherPresenterImpl(this);

        beaconSearch();
    }

    public void initUI(HashMap<String , String> viewMap){
        RelativeLayout relativeLayout;

        relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);

        if (viewMap.containsValue("비가올 확률이 높습니다.")){
            relativeLayout.setBackgroundResource(R.drawable.rain_bg);
        }
        else if (viewMap.containsValue("눈이 오고 있습니다.")){
            relativeLayout.setBackgroundResource(R.drawable.snow_bg);
        }
        else {
            relativeLayout.setBackgroundResource(R.drawable.sunny_bg);
        }

        temp.setText(viewMap.get("T3H") + " °C");

        tempTxt.setText(viewMap.get("TempTxt"));

        rainPer.setText("강수확률 : " + viewMap.get("POP") + "%");

        rainTxt.setText(viewMap.get("rainState"));

        cloudTxt.setText(viewMap.get("SkyState"));

        ImageView weatherImg;
        weatherImg = (ImageView) findViewById(R.id.weatherImg);

        if (viewMap.containsValue("비가올 확률이 높습니다.")){
            Glide.with(this).load(R.drawable.rain).into(weatherImg);
        }
        else if (viewMap.containsValue("눈이 오고 있습니다.")){
            Glide.with(this).load(R.drawable.snow).into(weatherImg);
        }
        else {
            Glide.with(this).load(R.drawable.sunny).into(weatherImg);
        }
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
                    else if(nearestBeacon.getRssi() < -100){
                        flag = 0;
//                        Toast.makeText(MainActivity.this, "연결종료", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        isConnected = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isConnected = false;
    }

        @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}
