package com.example.databindexam;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.databindexam.databinding.ItemRecycleBinding;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by kth919 on 2017-03-09.
 */

class DataBindingAdapter  extends RecyclerView.Adapter<DataBindingAdapter.BindingHolder> {
    private static String TAG = DataBindingAdapter.class.getName();

    private List<PictureInfo> mPictureInfo;

    public DataBindingAdapter( List<PictureInfo> mPictureInfo){
        this.mPictureInfo = mPictureInfo;

    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle, parent,  false);

        return new BindingHolder(v);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {

        PictureInfo item = mPictureInfo.get(position);

        holder.getBinding().setRecyclerItem(item);

        holder.getBinding().executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return mPictureInfo.size();
    }

    public class BindingHolder extends RecyclerView.ViewHolder {

        private ItemRecycleBinding binding;

        public BindingHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public ItemRecycleBinding getBinding() {
            return binding;
        }
    }
}
