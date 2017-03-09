package com.example.databindexam;

import android.content.Context;

import com.example.databindexam.databinding.ActivityMainBinding;

/**
 * Created by kth919 on 2017-03-07.
 */

public class ListPresenterImpl implements listPresenter, listInteractor.Interactor {

    private mainView mainView;
    private listInteractor mlistInteractor;
    private ListModel listModel;

    protected ListPresenterImpl(mainView mmainView, ActivityMainBinding binding) {
        this.mainView = mainView;
        listModel = new ListModel(this, binding);
    }

    @Override
    public void ListCall() {
        listModel.getList();
    }


    @Override
    public void successCallData( ) {
        mainView.recyclerHandler();
    }

    @Override
    public void failedCallData() {

    }
}
