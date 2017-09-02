package com.example.databindexam;

import android.content.Context;

import com.example.databindexam.databinding.ActivityMainBinding;

import java.util.ArrayList;

/**
 * Created by kth919 on 2017-03-07.
 */

public class ListPresenterImpl implements listPresenter, listInteractor.Interactor {

    private mainView mainView;
    private listInteractor mlistInteractor;
    private ListModel listModel;

    protected ListPresenterImpl(mainView mainView) {
        this.mainView = mainView;
        listModel = new ListModel(this);
    }

    @Override
    public void ListCall() {
        listModel.getList();
    }


    @Override
    public void successCallData(ArrayList<PictureInfo> mPictureInfo ) {
        mainView.recyclerHandler(mPictureInfo);
    }

    @Override
    public void failedCallData() {

    }
}
