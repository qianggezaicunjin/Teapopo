package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.ItemRecyclerviewAddressBinding;
import com.teapopo.life.model.welfare.Address;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.viewModel.welfare.ItemAddressManageViewModel;

import java.util.List;

/**
 * Created by louiszgm on 2016/7/1.
 */
public class AddressManageListAdapter extends BaseRecyclerViewAdapter<Address,AddressManageListAdapter.AddressManageListViewHolder> {
    public AddressManageListAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public AddressManageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AddressManageListViewHolder.createViewHolder(ItemRecyclerviewAddressBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(AddressManageListViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Address address = data.get(position);
        ItemAddressManageViewModel viewModel = new ItemAddressManageViewModel();
        viewModel.address = address;
        holder.setViewModel(viewModel);
    }

    public static class AddressManageListViewHolder extends RecyclerView.ViewHolder{

        public static AddressManageListViewHolder createViewHolder(ViewDataBinding binding){
            return new AddressManageListViewHolder(binding.getRoot(),binding);
        }
        public AddressManageListViewHolder(View itemView,ViewDataBinding binding) {
            super(itemView);
            itemView.setTag(binding);
        }
        public void setViewModel(ItemAddressManageViewModel viewModel){
            ItemRecyclerviewAddressBinding binding = (ItemRecyclerviewAddressBinding) itemView.getTag();
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }
    }
}
