package com.example.weatheralarm;

import android.util.Log;

import com.estimote.sdk.internal.utils.L;
import com.example.weatheralarm.JSON.Item;
import com.example.weatheralarm.JSON.ResponseJson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kth919 on 2017-02-24.
 */

public class WeatherModel implements weatherInteractor {

    private static String TAG = WeatherModel.class.getName();

    private weatherInteractor.weatherUiInteractor mweatherUiInteractor;

    private ArrayList<ArrayList<Item>> source;
    private ArrayList<Item> data;
    private HashMap<String, Double> fcstMap = new HashMap<>();
    private HashMap<String, String> viewMap = new HashMap<>();

    public WeatherModel(weatherInteractor.weatherUiInteractor mweatherUiInteractor) {
        this.mweatherUiInteractor = mweatherUiInteractor;
    }

    @Override
    public void checkWeatherData() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://newsky2.kma.go.kr")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
        .build();

        //현재 날짜와 시간 계산
        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        SimpleDateFormat YearDateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat HourFormat = new SimpleDateFormat("HH00");
        String strCurDate = YearDateFormat.format(date);
        String strHour = HourFormat.format(date);

        //기상청 예보형식에 맞는 시간변환
        String HourResult;
        if (Integer.parseInt(strHour) % 300 == 0){
            HourResult = Integer.toString(Integer.parseInt(strHour)-100);
        }else if (Integer.parseInt(strHour) % 300 == 100){
            HourResult = Integer.toString(Integer.parseInt(strHour)+100);
        }else {
            HourResult = strHour;
        }

        Log.d(TAG, strCurDate);
        Log.d(TAG, strHour);
        Log.d(TAG, HourResult);

        weatherJsonInterface service = retrofit.create(weatherJsonInterface.class);
        Call<ResponseJson> call = service.getResponse(weatherJsonInterface.ServiceKey ,
                                  strCurDate ,
                                  HourResult,
                                  "53",
                                  "38" ,
                                  "13"  ,
                                  "json");

        call.enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {

                if (response.isSuccessful()) {
                    source = new ArrayList<>(Collections.singletonList(response.body().getJsonList().getBody().getItems().getItem()));
                    data = source.get(0);

                    for (int i = 0; i < data.size(); i++) {
//                        Log.d(TAG, data.get(i).getCategory());

                        switch (data.get(i).getCategory()) {

                            case "POP": //강수확률
                                fcstMap.put(data.get(i).getCategory(), data.get(i).getFcstValue());
                                Log.d(TAG, "강수확률" + fcstMap.get(data.get(i).getCategory()));
                                break;

                            case "PTY": //강수형태
                                fcstMap.put(data.get(i).getCategory(), data.get(i).getFcstValue());
                                Log.d(TAG, "강수형태" + fcstMap.get(data.get(i).getCategory()));
                                break;

                            case "SKY": //하늘상태
                                fcstMap.put(data.get(i).getCategory(), data.get(i).getFcstValue());
                                Log.d(TAG, "하늘상태" + fcstMap.get(data.get(i).getCategory()));
                                break;

                            case "T3H": //3시간 기온
                                fcstMap.put(data.get(i).getCategory(), data.get(i).getFcstValue());
                                Log.d(TAG, "3시간 기온" + fcstMap.get(data.get(i).getCategory()));
                                break;

                            case "WSD": //풍속
                                fcstMap.put(data.get(i).getCategory(), data.get(i).getFcstValue());
                                Log.d(TAG, "풍속" + fcstMap.get(data.get(i).getCategory()));
                                break;
                        }
                    }
                }
                ReturnWeatherResult(fcstMap);

            }

            @Override
            public void onFailure(Call<ResponseJson> call, Throwable t) {
                Log.d(TAG, "콜실패" + t.toString());
            }
        });
    }

    public void ReturnWeatherResult(HashMap<String, Double> fcstMap) {

            viewMap.put("T3H" , String.valueOf(fcstMap.get("T3H")));

            viewMap.put("POP" , String.valueOf(fcstMap.get("POP")));

        Log.d(TAG, String.valueOf(fcstMap.get("POP")));

        //강수확률 조언
            if (fcstMap.get("POP") >= 45.0) {
                viewMap.put("precipitation", "비가올 확률이 높습니다.");
            }

        //호우상태 조언
            if (fcstMap.get("PTY") == 1.0) {
                viewMap.put("rainState", "비가 오고 있습니다.");
            } else if (fcstMap.get("PTY") == 2.0) {
                viewMap.put("rainState", "진눈깨비가 오고 있습니다.");
            } else if (fcstMap.get("PTY") == 3.0) {
                viewMap.put("rainState", "눈이 오고 있습니다.");
            }

        //하늘상태 조언
            if (fcstMap.get("SKY") == 1.0) {
                viewMap.put("SkyState", "오늘은 맑습니다.");
            } else if (fcstMap.get("SKY") == 2.0) {
                viewMap.put("SkyState", "오늘은 구름이 조금 있습니다.");
            } else if (fcstMap.get("SKY") == 3.0) {
                viewMap.put("SkyState", "오늘은 구름이 많습니다.");
            } else if (fcstMap.get("SKY") == 4.0) {
                viewMap.put("SkyState", "오늘은 흐린 날입니다.");
            }

        //기온조언

            if (fcstMap.get("T3H") >= 30.0) {
                viewMap.put("TempTxt", "폭염주의보가 의심됩니다.");
            } else if (fcstMap.get("T3H") >= 20.0) {
                viewMap.put("TempTxt", "오늘은 날씨가 포근합니다.");
            } else if (fcstMap.get("T3H") >= 10.0) {
                viewMap.put("TempTxt", "오늘은 날씨가 서늘합니다.");
            } else {
                viewMap.put("TempTxt", "오늘은 날씨가 쌀쌀합니다.");
            }

        //풍속 조언
            if (fcstMap.get("WSD") <= 9.0) {
                viewMap.put("wind" , "오늘은 바람이 약합니다");

            } else if (fcstMap.get("WSD") <= 14.0) {
                viewMap.put("wind" , "오늘은 바람이 강합니다");

            } else if (fcstMap.get("WSD") > 14.0) {
                viewMap.put("wind" , "오늘은 강풍주의보 입니다!");
            }

        mweatherUiInteractor.CallWeatherUI(viewMap);
        }

    }

