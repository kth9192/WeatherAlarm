package com.example.databindexam;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by kth919 on 2017-03-07.
 */

public class PictureInfo extends BaseObservable {

    private String title;
    private String desc;
    private String image;

    public PictureInfo(String title, String desc, String image) {
        this.title = title;
        this.desc = desc;
        this.image = image;
    }


    @Bindable
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
