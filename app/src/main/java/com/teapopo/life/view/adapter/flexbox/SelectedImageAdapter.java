package com.teapopo.life.view.adapter.flexbox;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.ItemFlexboxSelectedImageBinding;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.imageselect.Image;
import com.teapopo.life.view.adapter.LBaseAdapter;
import com.teapopo.life.view.adapter.LBaseAdapter.BaseViewHolder;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/7/19.
 */
public class SelectedImageAdapter extends LBaseAdapter<BaseEntity,BaseViewHolder> {

    public SelectedImageAdapter(Context context) {
        super(context);
    }

    @Override
    protected BaseViewHolder createViewHolder(int position, ViewGroup parent) {
        return SelectedImageViewHolder.createViewHolder(ItemFlexboxSelectedImageBinding.inflate(LayoutInflater.from(getContext())));
    }

    @Override
    protected void bindViewHolder(BaseViewHolder holder, int position,  BaseEntity data) {
        SelectedImageViewHolder selectedImageViewHolder = (SelectedImageViewHolder)holder;
        ItemFlexboxSelectedImageBinding binding = (ItemFlexboxSelectedImageBinding) selectedImageViewHolder.getBinding();
        final Image image = (Image) data;
        binding.imgDeleteSelectedimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("post a  image");
                image.isSelected = true;
                ComponentHolder.getAppComponent().rxbus().post(image);
                removeData(image);
            }
        });
        selectedImageViewHolder.setImage(image);
    }

    public static class SelectedImageViewHolder extends BaseViewHolder{
        private ViewDataBinding mBinding;
        public static SelectedImageViewHolder createViewHolder(ViewDataBinding binding){
            return new SelectedImageViewHolder(binding.getRoot(),binding);
        }
        public SelectedImageViewHolder(View itemView, ViewDataBinding binding) {
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
