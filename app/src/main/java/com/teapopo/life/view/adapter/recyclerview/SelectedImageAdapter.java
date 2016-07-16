package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.ItemRecyclerviewSelectedImageBinding;
import com.teapopo.life.model.imageselect.Image;
import com.teapopo.life.view.adapter.LBaseAdapter;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by louiszgm on 2016/7/15.
 */
public class SelectedImageAdapter extends BaseRecyclerViewAdapter<Image,SelectedImageAdapter.SelectedImageViewHolder> {
    public SelectedImageAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public SelectedImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return  SelectedImageViewHolder.createViewHolder(ItemRecyclerviewSelectedImageBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(SelectedImageViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.setImage(data.get(position));
    }

    public static class SelectedImageViewHolder extends RecyclerView.ViewHolder{

        public static SelectedImageViewHolder createViewHolder(ViewDataBinding binding){
            return new SelectedImageViewHolder(binding.getRoot(),binding);
        }
        public SelectedImageViewHolder(View itemView,ViewDataBinding binding) {
            super(itemView);
            itemView.setTag(binding);
        }

        public void setImage(Image image){
            ItemRecyclerviewSelectedImageBinding binding = (ItemRecyclerviewSelectedImageBinding) itemView.getTag();
            binding.setImage(image);
            binding.executePendingBindings();
        }
    }
}
