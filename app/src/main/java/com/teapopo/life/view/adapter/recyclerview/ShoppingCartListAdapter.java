package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.teapopo.life.databinding.ItemRecyclerviewShoppingcartBinding;
import com.teapopo.life.model.welfare.CartGoods;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.view.customView.Interface.DrawableClickListener;
import com.teapopo.life.viewModel.welfare.ItemShoppingCartViewModel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by louiszgm on 2016/7/7.
 */
public class ShoppingCartListAdapter extends BaseRecyclerViewAdapter<CartGoods,ShoppingCartListAdapter.ShoppingCartViewHolder> {

    public ShoppingCartListAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public ShoppingCartListAdapter.ShoppingCartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ShoppingCartViewHolder.createViewHolder(ItemRecyclerviewShoppingcartBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(ShoppingCartListAdapter.ShoppingCartViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        CartGoods cartGoods = (CartGoods) data.get(position);
        final ItemShoppingCartViewModel viewModel = new ItemShoppingCartViewModel();
        viewModel.cartGoods = cartGoods;
        ItemRecyclerviewShoppingcartBinding binding = (ItemRecyclerviewShoppingcartBinding) holder.itemView.getTag();
        binding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               viewModel.selectCartGoodsOrNot(isChecked);
            }
        });

        binding.buyNum.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void drawableClick(int type) {
                viewModel.changeBuyNum(type);
            }
        });
        holder.setviewmodel(viewModel);
    }

    public static class ShoppingCartViewHolder extends RecyclerView.ViewHolder{

        public static ShoppingCartViewHolder createViewHolder(ViewDataBinding binding){
            return new ShoppingCartViewHolder(binding.getRoot(),binding);
        }
        public ShoppingCartViewHolder(View itemView,ViewDataBinding binding) {
            super(itemView);
            itemView.setTag(binding);
        }
        public void setviewmodel(ItemShoppingCartViewModel viewModel){
            ItemRecyclerviewShoppingcartBinding binding = (ItemRecyclerviewShoppingcartBinding) itemView.getTag();
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }
    }
}
