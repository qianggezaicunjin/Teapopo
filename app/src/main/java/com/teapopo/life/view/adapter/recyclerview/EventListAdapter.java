package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.ItemRecyclerviewWelfareEventBinding;
import com.teapopo.life.model.welfare.Event;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * Created by louiszgm on 2016/6/22.
 */
public class EventListAdapter extends BaseRecyclerViewAdapter<Event,EventListAdapter.EventListViewHolder> {
    public EventListAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public EventListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return EventListViewHolder.createViewHolder(ItemRecyclerviewWelfareEventBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(EventListViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Event event = data.get(position);
        holder.setEvent(event);
    }

    public static class EventListViewHolder extends RecyclerView.ViewHolder{

        public static EventListViewHolder createViewHolder(ViewDataBinding binding){
            return new EventListViewHolder(binding.getRoot(),binding);
        }
        public EventListViewHolder(View itemView,ViewDataBinding binding) {
            super(itemView);
            itemView.setTag(binding);
        }

        public void setEvent(Event event){
            ItemRecyclerviewWelfareEventBinding binding = (ItemRecyclerviewWelfareEventBinding) itemView.getTag();
            binding.setEvent(event);
            binding.executePendingBindings();
        }
    }
}
