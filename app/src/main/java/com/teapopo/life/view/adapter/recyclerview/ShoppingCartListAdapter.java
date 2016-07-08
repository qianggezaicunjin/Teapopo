package com.teapopo.life.view.adapter.recyclerview;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.teapopo.life.databinding.ItemRecyclerviewShoppingcartBinding;
import com.teapopo.life.injection.component.ComponentHolder;
import com.teapopo.life.model.event.SelectALLEvent;
import com.teapopo.life.model.welfare.CartGoods;
import com.teapopo.life.util.rx.RxSubscriber;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.view.customView.Interface.DrawableClickListener;
import com.teapopo.life.viewModel.welfare.ItemShoppingCartViewModel;

import java.util.HashMap;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/7/7.
 */
public class ShoppingCartListAdapter extends BaseRecyclerViewAdapter<CartGoods,ShoppingCartListAdapter.ShoppingCartViewHolder> {

    public CompositeSubscription compositeSubscription = new CompositeSubscription();
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
        final CartGoods cartGoods = (CartGoods) data.get(position);
        final ItemShoppingCartViewModel viewModel = new ItemShoppingCartViewModel();
        viewModel.setCartGoods(cartGoods);
        ItemRecyclerviewShoppingcartBinding binding = (ItemRecyclerviewShoppingcartBinding) holder.itemView.getTag();
       compositeSubscription.add(ComponentHolder.getAppComponent().rxbus().toObserverable(SelectALLEvent.class)
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new RxSubscriber<SelectALLEvent>() {
                   @Override
                   public void _onNext(SelectALLEvent selectALLEvent) {
                       Timber.d("收到全选事件");
                       viewModel.selectCartGoodsOrNot(selectALLEvent.isSelected);
                   }

                   @Override
                   public void _onError(String s) {

                   }
               }));
       binding.checkBox.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               viewModel.selectCartGoodsOrNot(!cartGoods.isSelected);
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
