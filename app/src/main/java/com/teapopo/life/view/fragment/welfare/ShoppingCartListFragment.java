package com.teapopo.life.view.fragment.welfare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.teapopo.life.R;
import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.databinding.FragmentShoppingcartBinding;
import com.teapopo.life.model.event.SelectALLEvent;
import com.teapopo.life.model.welfare.CartGoods;
import com.teapopo.life.util.rx.RxSubscriber;
import com.teapopo.life.view.activity.GoodsHandleActivity;
import com.teapopo.life.view.adapter.recyclerview.ShoppingCartListAdapter;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.welfare.CartListViewModel;

import java.io.Serializable;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/7/7.
 */
public class ShoppingCartListFragment extends SwipeBackBaseFragment {

    private FragmentShoppingcartBinding mBinding;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    @Inject
    CartListViewModel mViewModel;
    @Inject
    RxBus mRxBus;
    private ShoppingCartListAdapter adapter;

    public static ShoppingCartListFragment newInstance(){
        return new ShoppingCartListFragment();
    }
    @Override
    public void onCreateBinding() {
        ((GoodsHandleActivity)_mActivity).getFragmentComponent().inject(this);
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentShoppingcartBinding.inflate(inflater);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        setUpToolBar();
        setUpCartList();
        observerCartsGoods();
        selectALLGoods();
    }

    private void setUpToolBar() {
        mBinding.toolbarShoppingcart.inflateMenu(R.menu.menu_edit);
        mBinding.toolbarShoppingcart.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                return true;
            }
        });
    }

    private void selectALLGoods() {
        mBinding.checkBoxSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SelectALLEvent selectALLEvent = new SelectALLEvent();
                selectALLEvent.isSelected = isChecked;
                Timber.d("是否全选:%s",selectALLEvent.isSelected);
                mRxBus.post(selectALLEvent);
            }
        });
    }

    private void observerCartsGoods() {
        Observable<CartGoods> observable = mRxBus.toObserverable(CartGoods.class);
        compositeSubscription.add(
                observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<CartGoods>() {
                    @Override
                    public void _onNext(CartGoods cartGoods) {
                        mViewModel.calculateCartGoodsOverView(cartGoods);
                    }

                    @Override
                    public void _onError(String s) {

                    }
                })
        );
    }

    private void setUpCartList() {
        adapter = new ShoppingCartListAdapter(_mActivity,mViewModel.data);
        mBinding.rvShoppingcart.setAdapter(adapter);
        mViewModel.getCartList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.compositeSubscription.unsubscribe();
        compositeSubscription.unsubscribe();
    }
}
