package com.teapopo.life.view.fragment.welfare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.FragmentShoppingcartBinding;
import com.teapopo.life.view.activity.GoodsHandleActivity;
import com.teapopo.life.view.adapter.recyclerview.ShoppingCartListAdapter;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.welfare.CartListViewModel;

import javax.inject.Inject;

/**
 * Created by louiszgm on 2016/7/7.
 */
public class ShoppingCartListFragment extends SwipeBackBaseFragment {

    private FragmentShoppingcartBinding mBinding;

    @Inject
    CartListViewModel mViewModel;
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
        setUpCartList();
    }

    private void setUpCartList() {
        ShoppingCartListAdapter adapter = new ShoppingCartListAdapter(_mActivity,mViewModel.data);
        mBinding.rvShoppingcart.setAdapter(adapter);

        mViewModel.getCartList();
    }
}
