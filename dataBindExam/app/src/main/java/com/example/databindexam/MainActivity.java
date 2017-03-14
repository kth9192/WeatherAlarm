package com.example.databindexam;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.databindexam.databinding.ActivityMainBinding;
import com.example.databindexam.databinding.ItemRecycleBinding;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements mainView {

    private ListPresenterImpl mlistPresenterImpl;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMain(this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        mlistPresenterImpl = new ListPresenterImpl(this);

        mlistPresenterImpl.ListCall();
    }

    public void onButtonClick(View view){
        Toast.makeText(this,"Button Click",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void recyclerHandler(ArrayList<PictureInfo> mPictureInfo ) {
          DataBindingAdapter adapter = new DataBindingAdapter(mPictureInfo);
          binding.recycler.setAdapter(adapter);
    }


}
