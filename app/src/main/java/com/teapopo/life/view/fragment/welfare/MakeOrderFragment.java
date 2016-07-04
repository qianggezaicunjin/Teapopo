package com.teapopo.life.view.fragment.welfare;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.teapopo.life.R;
import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.databinding.FragmentMakeorderBinding;
import com.teapopo.life.databinding.LayoutReceiveAddressBinding;
import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.view.activity.GoodsHandleActivity;
import com.teapopo.life.view.adapter.recyclerview.MakeOrderListAdapter;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.welfare.MakeOrderViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by louiszgm on 2016/6/30.
 */
public class MakeOrderFragment extends SwipeBackBaseFragment{

    private FragmentMakeorderBinding mBinding;

    private ArrayList<EventGoods> eventGoodsList;

    @Inject
    MakeOrderViewModel mViewModel;

    @Inject
    RxBus mRxBus;
    private LayoutReceiveAddressBinding binding_addressinfo;

    public static MakeOrderFragment newInstance(ArrayList<Parcelable> eventGoodsList){
        MakeOrderFragment fragment = new MakeOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("goodslist",eventGoodsList);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreateBinding() {
        eventGoodsList = getArguments().getParcelableArrayList("goodslist");
        ((GoodsHandleActivity)_mActivity).getFragmentComponent().inject(this);
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentMakeorderBinding.inflate(inflater);
        binding_addressinfo = LayoutReceiveAddressBinding.inflate(inflater);
        mViewModel.data = eventGoodsList;
        mBinding.setViewModel(mViewModel);
        mBinding.setHandler(this);
        binding_addressinfo.setHandler(this);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        setUpGoodsList();
        setUpAddressInfo();
        setToolBar();
    }

    private void setUpAddressInfo() {
        mBinding.rvGoodsSettlement.addHeader(binding_addressinfo.getRoot());
    }

    private void setUpGoodsList() {
        MakeOrderListAdapter adapter = new MakeOrderListAdapter(_mActivity,mViewModel.data);
        mBinding.rvGoodsSettlement.setAdapter(adapter);
    }

    public void clickAddress(View view){
        startForResult(AddressManageFragment.newInstance(),1);
    }

    public void clickSettleMent(View view){
//        mViewModel.makeOrder();
        start(OrderSettleMentFragment.newInstance("352"));
    }
    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
    }

    //设置返回按钮监听
    public void setToolBar(){
        Toolbar toolbar=mBinding.toolbarMakeorder;
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
                Toast.makeText(_mActivity,"111",0).show();
            }
        });
    }
}
