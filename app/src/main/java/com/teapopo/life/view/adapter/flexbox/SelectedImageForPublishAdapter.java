package com.teapopo.life.view.adapter.flexbox;

/**
 * Created by louiszgm on 2016/7/22.
 */

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.ItemFlexboxSelectedImageBinding;
import com.teapopo.life.model.imageselect.Image;
import com.teapopo.life.view.adapter.LBaseAdapter;


import timber.log.Timber;


/**
 * Created by louiszgm on 2016/7/19.
 */
public class SelectedImageForPublishAdapter extends LBaseAdapter<Image,LBaseAdapter.BaseViewHolder> {

    public SelectedImageForPublishAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder createViewHolder(int position, ViewGroup parent) {
        return SelectedImageForPublishViewHolder.createViewHolder(ItemFlexboxSelectedImageBinding.inflate(LayoutInflater.from(getContext())));
    }

    @Override
    protected void bindViewHolder(BaseViewHolder holder, int position,  Image data) {
        SelectedImageForPublishViewHolder selectedImageViewHolder = (SelectedImageForPublishViewHolder)holder;
        final ItemFlexboxSelectedImageBinding binding = (ItemFlexboxSelectedImageBinding) selectedImageViewHolder.getBinding();
        final Image image = data;
        binding.imgDeleteSelectedimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("post a  image");
                removeData(image);
            }
        });
        selectedImageViewHolder.setImage(image);
    }



    public static class SelectedImageForPublishViewHolder extends BaseViewHolder{
        private ViewDataBinding mBinding;
        public static SelectedImageForPublishViewHolder createViewHolder(ViewDataBinding binding){
            return new SelectedImageForPublishViewHolder(binding.getRoot(),binding);
        }
        public SelectedImageForPublishViewHolder(View itemView, ViewDataBinding binding) {
            super(itemView);
            mBinding = binding;
        }

        public ViewDataBinding getBinding(){
            return mBinding;
        }
        public void setImage(Image image){
            ItemFlexboxSelectedImageBinding binding = (ItemFlexboxSelectedImageBinding) mBinding;
            Timber.d("setImage");
            binding.setImage(image);
            binding.executePendingBindings();
        }
    }

}

