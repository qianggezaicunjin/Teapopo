package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.ItemRecyclerviewEventgoodsBinding;
import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.util.navigator.Navigator;
import com.teapopo.life.view.activity.GoodsHandleActivity;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.viewModel.welfare.ItemEventGoodsViewModel;

import java.util.ArrayList;
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
        final EventGoods eventGoods = data.get(position);
        ItemRecyclerviewEventgoodsBinding binding = (ItemRecyclerviewEventgoodsBinding) holder.itemView.getTag();
        ItemEventGoodsViewModel viewModel = new ItemEventGoodsViewModel();
        viewModel.eventGoods = eventGoods;
        holder.setViewModel(viewModel);
        binding.welfareSpecialBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Parcelable> list = new ArrayList<Parcelable>();
                list.add(eventGoods);
                Navigator.getInstance().start(mContext, GoodsHandleActivity.getStartIntent(mContext,list,1));
            }
        });
    }


    public static  class EventGoodsViewHolder extends RecyclerView.ViewHolder{

        public static EventGoodsViewHolder createViewHolder(ViewDataBinding binding){
            return new EventGoodsViewHolder(binding.getRoot(),binding);
        }
        public EventGoodsViewHolder(View itemView,ViewDataBinding binding) {
            super(itemView);
            itemView.setTag(binding);
        }
        public void setViewModel(ItemEventGoodsViewModel viewModel){
            ItemRecyclerviewEventgoodsBinding binding = (ItemRecyclerviewEventgoodsBinding) itemView.getTag();
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }
    }
}
