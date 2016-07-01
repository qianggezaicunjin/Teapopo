package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.ItemRecyclerviewGoodsSettlementBinding;
import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.viewModel.welfare.ItemSettleMentGoodsViewModel;

import java.util.List;

/**
 * Created by louiszgm on 2016/7/1.
 */
public class SettleMentGoodsListAdapter extends BaseRecyclerViewAdapter<EventGoods,SettleMentGoodsListAdapter.SettleMentGoodsListViewHolder> {
    public SettleMentGoodsListAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public SettleMentGoodsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return SettleMentGoodsListViewHolder.createViewHolder(ItemRecyclerviewGoodsSettlementBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(SettleMentGoodsListViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemSettleMentGoodsViewModel viewModel = new ItemSettleMentGoodsViewModel();
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
        public void setViewModel(ItemSettleMentGoodsViewModel viewModel){
            ItemRecyclerviewGoodsSettlementBinding binding = (ItemRecyclerviewGoodsSettlementBinding) itemView.getTag();
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }

    }
}
