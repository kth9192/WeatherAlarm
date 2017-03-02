package com.example.beacontest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;

import java.util.List;
import java.util.UUID;

/**
 * Created by ncl on 2016-11-15.
 */

public class beaconActivity extends AppCompatActivity {

    private String TAG = beaconActivity.class.getName();

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

                    }
                    else if(nearestBeacon.getRssi() < -90){
                        flag = 0;
//                        Toast.makeText(beaconActivity.this, "연결종료", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "연결종료");
                        isConnected = false;
                    }
//                    else {
//                        Toast.makeText(beaconActivity.this, "연결종료", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });

        region = new Region("ranged region",
                UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 3905, 17974); // 본인이 연결할 Beacon의 ID와 Major / Minor Code를 알아야 한다.

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
