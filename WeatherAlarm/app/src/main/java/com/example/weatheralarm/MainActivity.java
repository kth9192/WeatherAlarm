package com.example.weatheralarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.weatheralarm.JSON.Item;
import com.example.weatheralarm.JSON.ResponseJson;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static String TAG = MainActivity.class.getName();
    private ArrayList<ArrayList<Item>> data;

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
                    data = new ArrayList<>(Arrays.asList(response.body().getJsonList().getBody().getItems().getItem()));
                    for (int i = 0; i<10; i++){
                        Log.d(TAG, data.get(0).get(i).getCategory());
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
