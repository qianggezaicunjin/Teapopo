package com.teapopo.life.view.fragment.welfare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.FragmentOrderSettlementBinding;
import com.teapopo.life.view.activity.GoodsHandleActivity;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.welfare.OrderSettleMentViewModel;

import javax.inject.Inject;

/**
 * Created by louiszgm on 2016/7/2.
 */
public class OrderSettleMentFragment extends SwipeBackBaseFragment {


    private FragmentOrderSettlementBinding mBinding;
    private String orderId;
    @Inject
    OrderSettleMentViewModel mViewModel;

    public static OrderSettleMentFragment newInstance(String orderId){
        OrderSettleMentFragment fragment = new OrderSettleMentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("orderId",orderId);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreateBinding() {
        ((GoodsHandleActivity)_mActivity).getFragmentComponent().inject(this);

        if(getArguments()!=null){
            orderId = getArguments().getString("orderId");
        }
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentOrderSettlementBinding.inflate(inflater);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        setUpOrderInfo();
    }

    private void setUpOrderInfo() {
        mViewModel.getOrderInfo(orderId);
    }
}
