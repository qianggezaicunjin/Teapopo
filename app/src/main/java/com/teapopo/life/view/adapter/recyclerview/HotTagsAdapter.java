package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.ItemCategoryBinding;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.Tag.Tag;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by louiszgm on 2016/5/7.
 */
public class HotTagsAdapter extends BaseRecyclerViewAdapter<BaseEntity,HotTagsAdapter.CategoryViewHolder> {

    public HotTagsAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CategoryViewHolder.createCategoryViewHolder(ItemCategoryBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Tag tag = (Tag) data.get(position);
        holder.setCategory(tag);
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        ViewDataBinding mBinding;
        public static CategoryViewHolder createCategoryViewHolder(ViewDataBinding binding){
            return new CategoryViewHolder(binding.getRoot(),binding);
        }
        public CategoryViewHolder(View itemView,ViewDataBinding binding) {
            super(itemView);
            itemView.setTag(binding);
        }

        public void setCategory(Tag tag){
            ItemCategoryBinding binding = (ItemCategoryBinding) itemView.getTag();
            mBinding = binding;
            binding.setTag(tag);
            binding.executePendingBindings();
        }
    }
}
