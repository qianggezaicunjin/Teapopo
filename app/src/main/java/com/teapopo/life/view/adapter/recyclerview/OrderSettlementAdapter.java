package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.ItemRecyclerviewOrderSettlementBinding;
import com.teapopo.life.model.welfare.GoodsOverview;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.viewModel.welfare.ItemSettlementViewModel;

import java.util.List;

/**
 * Created by lhq on 2016/7/5.
 */

public class OrderSettlementAdapter extends BaseRecyclerViewAdapter<GoodsOverview,OrderSettlementAdapter.OrderSettlementViewHolder> {
    public OrderSettlementAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public OrderSettlementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return OrderSettlementViewHolder.createViewHolder(ItemRecyclerviewOrderSettlementBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(OrderSettlementViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final ItemSettlementViewModel viewModel = new ItemSettlementViewModel();
        final GoodsOverview goods = data.get(position);
        viewModel.goodsOverview = goods;
        holder.setViewModel(viewModel);
    }

    public static class OrderSettlementViewHolder extends RecyclerView.ViewHolder {

        public OrderSettlementViewHolder(View itemView, ViewDataBinding binding) {
            super(itemView);
            itemView.setTag(binding);
        }
        public static OrderSettlementViewHolder createViewHolder(ViewDataBinding binding){
            return new OrderSettlementViewHolder(binding.getRoot(),binding);
        }
        public void setViewModel(ItemSettlementViewModel viewModel){
            ItemRecyclerviewOrderSettlementBinding binding = (ItemRecyclerviewOrderSettlementBinding) itemView.getTag();
            binding.setViewmodel(viewModel);
            binding.executePendingBindings();
        }
    }
}
