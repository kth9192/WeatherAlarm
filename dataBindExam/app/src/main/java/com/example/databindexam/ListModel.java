package com.example.databindexam;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.util.Log;

import com.example.databindexam.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kth919 on 2017-03-07.
 */

public class ListModel implements listInteractor {

    private static String TAG = ListModel.class.getName();

    private listInteractor.Interactor mInteractor;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private DatabaseReference bindingInstance = myRef.child("imgMeta");

    Context context;

    private ArrayList<String> listName = new ArrayList<>();
    private ArrayList<String> listUrl = new ArrayList<>();
    private ArrayList<String> listDesc = new ArrayList<>();
    ActivityMainBinding binding;

    public ListModel(listInteractor.Interactor mInteractor , ActivityMainBinding binding) {
        this.mInteractor = mInteractor;
        this.binding = binding;
    }

    @Override
    public void getList() {
        bindingInstance.addValueEventListener(new ValueEventListener() {

            int i =0;
             List<PictureInfo> mPictureInfo = new ArrayList<PictureInfo>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot jsonData : dataSnapshot.getChildren()){

                    listName.add(i, (String) jsonData.child("name").getValue());
                    listDesc.add(i, (String) jsonData.child("desc").getValue());
                    listUrl.add(i, (String) jsonData.child("url").getValue());

                    Log.d(TAG, (String) jsonData.child("name").getValue());
                    Log.d(TAG, (String) jsonData.child("desc").getValue());
                    Log.d(TAG, (String) jsonData.child("url").getValue());

                    mPictureInfo.add(new PictureInfo(listName.get(i), listDesc.get(i), listUrl.get(i)));
                    i++;
                }

                DataBindingAdapter adapter = new DataBindingAdapter(mPictureInfo);
                binding.recycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "firebase call error" + String.valueOf(databaseError));
            }
        });
    }

}
