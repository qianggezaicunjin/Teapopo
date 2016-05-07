package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.ItemCategoryBinding;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.category.Category;

import java.util.List;

/**
 * Created by louiszgm on 2016/5/7.
 */
public class CategoryAdapter extends BaseRecyclerViewAdapter<BaseEntity,CategoryAdapter.CategoryViewHolder> {

    public CategoryAdapter(Context context, List data) {
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
        Category category = (Category) data.get(position);
        holder.setCategory(category);
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

        public void setCategory(Category category){
            ItemCategoryBinding binding = (ItemCategoryBinding) itemView.getTag();
            mBinding = binding;
            binding.setCategory(category);
            binding.executePendingBindings();
        }
    }
}
