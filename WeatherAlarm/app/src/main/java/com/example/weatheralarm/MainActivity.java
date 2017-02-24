package com.example.weatheralarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.weatheralarm.JSON.Item;
import com.example.weatheralarm.JSON.ResponseJson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static String TAG = MainActivity.class.getName();
    private ArrayList<ArrayList<Item>> source;
    private ArrayList<Item> data;
    private HashMap<String, Double> fcstMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://newsky2.kma.go.kr")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherJsonInterface service = retrofit.create(weatherJsonInterface.class);
        Call<ResponseJson> call = service.getResponse();
        call.enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {

                if (response.isSuccessful()){
                    source = new ArrayList<>(Collections.singletonList(response.body().getJsonList().getBody().getItems().getItem()));
                    data = source.get(0);

                    for (int i = 0; i<data.size(); i++){
//                        Log.d(TAG, data.get(i).getCategory());

                        switch (data.get(i).getCategory()){
                            case "POP": //강수확률
                                 fcstMap.put(data.get(i).getCategory(), data.get(i).getFcstValue());
                                Log.d(TAG , "강수확률" + fcstMap.get(data.get(i).getCategory()));
                                break;

                            case "PTY": //강수형태
                                fcstMap.put(data.get(i).getCategory(), data.get(i).getFcstValue());
                                Log.d(TAG , "강수형태" + fcstMap.get(data.get(i).getCategory()));
                                break;

                            case "SKY": //하늘상태
                                fcstMap.put(data.get(i).getCategory(), data.get(i).getFcstValue());
                                Log.d(TAG , "하늘상태" + fcstMap.get(data.get(i).getCategory()));
                                break;

                            case "T3H": //3시간 기온
                                fcstMap.put(data.get(i).getCategory(), data.get(i).getFcstValue());

                                Log.d(TAG , "3시간 기온" + fcstMap.get(data.get(i).getCategory()));
                                break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseJson> call, Throwable t) {
                Log.d(TAG, "콜실패" + t.toString());
            }
        });

    }
}
