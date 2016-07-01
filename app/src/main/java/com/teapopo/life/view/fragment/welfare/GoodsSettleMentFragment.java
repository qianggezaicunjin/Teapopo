package com.teapopo.life.view.fragment.welfare;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.FragmentSettlementBinding;
import com.teapopo.life.databinding.LayoutReceiveAddressBinding;
import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.view.activity.GoodsHandleActivity;
import com.teapopo.life.view.adapter.recyclerview.SettleMentGoodsListAdapter;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.welfare.GoodsSettleMentViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by louiszgm on 2016/6/30.
 */
public class GoodsSettleMentFragment extends SwipeBackBaseFragment{

    private FragmentSettlementBinding mBinding;

    private ArrayList<EventGoods> eventGoodsList;

    @Inject
    GoodsSettleMentViewModel mViewModel;
    private LayoutReceiveAddressBinding binding_addressinfo;

    public static GoodsSettleMentFragment newInstance(ArrayList<Parcelable> eventGoodsList){
        GoodsSettleMentFragment fragment = new GoodsSettleMentFragment();
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
        mBinding = FragmentSettlementBinding.inflate(inflater);
        binding_addressinfo = LayoutReceiveAddressBinding.inflate(inflater);
        mViewModel.data = eventGoodsList;
        mBinding.setViewModel(mViewModel);
        binding_addressinfo.setHandler(this);
        return mBinding.getRoot();
    }


    @Override
    public void setUpView() {
        setUpGoodsList();
        setUpAddressInfo();
    }

    private void setUpAddressInfo() {
        mBinding.rvGoodsSettlement.addHeader(binding_addressinfo.getRoot());
    }

    private void setUpGoodsList() {
        SettleMentGoodsListAdapter adapter = new SettleMentGoodsListAdapter(_mActivity,mViewModel.data);
        mBinding.rvGoodsSettlement.setAdapter(adapter);
    }

    public void clickAddress(View view){
        startForResult(AddressManageFragment.newInstance(),1);
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
    }
}
