package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.ItemRecyclerviewShoppingcartBinding;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * Created by louiszgm on 2016/7/7.
 */
public class ShoppingCartListAdapter extends BaseRecyclerViewAdapter {
    //用来保存每个item的选中状态, key为item的position
    public HashMap<Integer,Boolean> selectMap = new HashMap<>();
    public ShoppingCartListAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ShoppingCartViewHolder.createViewHolder(ItemRecyclerviewShoppingcartBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    public static class ShoppingCartViewHolder extends RecyclerView.ViewHolder{

        public static ShoppingCartViewHolder createViewHolder(ViewDataBinding binding){
            return new ShoppingCartViewHolder(binding.getRoot(),binding);
        }
        public ShoppingCartViewHolder(View itemView,ViewDataBinding binding) {
            super(itemView);
            itemView.setTag(binding);
        }
    }
}
