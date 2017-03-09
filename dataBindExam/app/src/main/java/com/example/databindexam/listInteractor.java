package com.example.databindexam;

import com.example.databindexam.databinding.ActivityMainBinding;

/**
 * Created by kth919 on 2017-03-07.
 */

public interface listInteractor {

    void getList();

    interface Interactor{
        void successCallData( );
        void failedCallData();
    }
}
