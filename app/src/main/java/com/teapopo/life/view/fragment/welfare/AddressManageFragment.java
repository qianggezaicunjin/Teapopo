package com.teapopo.life.view.fragment.welfare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.FragmentAddressManageBinding;
import com.teapopo.life.view.activity.GoodsHandleActivity;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.welfare.AddressManageViewModel;

import javax.inject.Inject;

/**
 * Created by louiszgm on 2016/7/1.
 */
public class AddressManageFragment extends SwipeBackBaseFragment {

    private FragmentAddressManageBinding mBinding;
    @Inject
    AddressManageViewModel mViewModel;
    @Override
    public void onCreateBinding() {
        ((GoodsHandleActivity)_mActivity).getFragmentComponent().inject(this);
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentAddressManageBinding.inflate(inflater);
        mBinding.setViewmodel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        setUpAddressList();
    }

    private void setUpAddressList() {

    }


}
