package com.teapopo.life.view.fragment.welfare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alipay.sdk.app.PayTask;
import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentOrderSettlementBinding;
import com.teapopo.life.view.activity.GoodsHandleActivity;
import com.teapopo.life.view.adapter.recyclerview.OrderSettlementAdapter;
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

    public static OrderSettleMentFragment newInstance(String orderId) {
        OrderSettleMentFragment fragment = new OrderSettleMentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("orderId", orderId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreateBinding() {
        ((GoodsHandleActivity) _mActivity).getFragmentComponent().inject(this);

        if (getArguments() != null) {
            orderId = getArguments().getString("orderId");
        }
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentOrderSettlementBinding.inflate(inflater);
        mBinding.setViewModel(mViewModel);
        mBinding.setHandler(this);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        setUpOrderInfo();
        setUpGoodsList();
        setUpToolBar();
    }

    public void clickPayByAlipay(View view) {
        PayTask payTask = new PayTask(_mActivity);
        mViewModel.pay(payTask);
    }

    private void setUpOrderInfo() {
        mViewModel.getOrderInfo(orderId);
    }

    private void setUpGoodsList() {
        OrderSettlementAdapter adapter=new OrderSettlementAdapter(_mActivity,mViewModel.data);
        mBinding.rvOrderSettlement.setAdapter(adapter);
    }

    //设置标题导航按钮
    public void setUpToolBar(){
        Toolbar toolbar=mBinding.tbOrderSettlement;
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
    }
}
