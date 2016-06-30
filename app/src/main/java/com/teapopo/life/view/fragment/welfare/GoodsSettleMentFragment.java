package com.teapopo.life.view.fragment.welfare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentSettlementBinding;
import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.view.activity.GoodsSettleMentActivity;
import com.teapopo.life.view.customView.ClickAbleTextView;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.welfare.GoodsSettleMentViewModel;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/30.
 */
public class GoodsSettleMentFragment extends SwipeBackBaseFragment{

    private FragmentSettlementBinding mBinding;

    private EventGoods eventGoods;

    @Inject
    GoodsSettleMentViewModel mViewModel;
    public static GoodsSettleMentFragment newInstance(EventGoods eventGoods){
        GoodsSettleMentFragment fragment = new GoodsSettleMentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("goods",eventGoods);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreateBinding() {
        eventGoods = getArguments().getParcelable("goods");
        ((GoodsSettleMentActivity)_mActivity).getFragmentComponent().inject(this);
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentSettlementBinding.inflate(inflater);
        mViewModel.goods = eventGoods;
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {

    }

}
