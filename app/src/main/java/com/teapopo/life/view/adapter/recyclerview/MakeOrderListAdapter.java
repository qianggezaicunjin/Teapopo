package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import com.teapopo.life.databinding.ItemRecyclerviewGoodsMakeorderBinding;
import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.view.customView.EditText.DrawableClickablEdT;
import com.teapopo.life.view.customView.Interface.DrawableClickListener;
import com.teapopo.life.viewModel.welfare.ItemMakeOrderGoodsViewModel;

import java.util.List;

/**
 * Created by louiszgm on 2016/7/1.
 */
public class MakeOrderListAdapter extends BaseRecyclerViewAdapter<EventGoods,MakeOrderListAdapter.SettleMentGoodsListViewHolder> {
    public MakeOrderListAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public SettleMentGoodsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return SettleMentGoodsListViewHolder.createViewHolder(ItemRecyclerviewGoodsMakeorderBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(SettleMentGoodsListViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final ItemMakeOrderGoodsViewModel viewModel = new ItemMakeOrderGoodsViewModel();
        final EventGoods goods = data.get(position);
        viewModel.goods = goods;
        ItemRecyclerviewGoodsMakeorderBinding binding = (ItemRecyclerviewGoodsMakeorderBinding) holder.itemView.getTag();
        binding.buyNum.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void drawableClick(int type) {
                viewModel.changeBuyNum(type);
            }
        });
        holder.setViewModel(viewModel);
    }

    public static class SettleMentGoodsListViewHolder extends RecyclerView.ViewHolder{

        public static SettleMentGoodsListViewHolder createViewHolder(ViewDataBinding binding){
            return new SettleMentGoodsListViewHolder(binding.getRoot(),binding);
        }
        public SettleMentGoodsListViewHolder(View itemView,ViewDataBinding binding) {
            super(itemView);
            itemView.setTag(binding);
        }
        public void setViewModel(ItemMakeOrderGoodsViewModel viewModel){
            ItemRecyclerviewGoodsMakeorderBinding binding = (ItemRecyclerviewGoodsMakeorderBinding) itemView.getTag();
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }

    }
}
