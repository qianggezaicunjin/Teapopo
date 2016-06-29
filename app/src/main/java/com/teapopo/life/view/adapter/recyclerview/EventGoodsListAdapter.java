package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.ItemRecyclerviewEventgoodsBinding;
import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by louiszgm on 2016/6/28.
 */
public class EventGoodsListAdapter extends BaseRecyclerViewAdapter<EventGoods,EventGoodsListAdapter.EventGoodsViewHolder> {
    public EventGoodsListAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public EventGoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return EventGoodsViewHolder.createViewHolder(ItemRecyclerviewEventgoodsBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(EventGoodsViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        EventGoods eventGoods = data.get(position);
        holder.setImageUrl(eventGoods.coverImg);
    }

    public static  class EventGoodsViewHolder extends RecyclerView.ViewHolder{

        public static EventGoodsViewHolder createViewHolder(ViewDataBinding binding){
            return new EventGoodsViewHolder(binding.getRoot(),binding);
        }
        public EventGoodsViewHolder(View itemView,ViewDataBinding binding) {
            super(itemView);
            itemView.setTag(binding);
        }
        public void setImageUrl(String url){
            ItemRecyclerviewEventgoodsBinding binding = (ItemRecyclerviewEventgoodsBinding) itemView.getTag();
            binding.setImageUrl(url);
            binding.executePendingBindings();
        }
    }
}
