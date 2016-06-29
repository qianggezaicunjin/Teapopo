package com.teapopo.life.view.fragment.welfare;

import android.databinding.Bindable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentEventgoodsListBinding;
import com.teapopo.life.model.welfare.Event;
import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.model.welfare.EventGoodsListModel;
import com.teapopo.life.view.adapter.recyclerview.EventGoodsListAdapter;
import com.teapopo.life.view.fragment.BaseFragment;
import com.teapopo.life.viewModel.welfare.EventGoodsListViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/28.
 */
public class EventGoodsListFragment extends BaseFragment {

    private String eventId;
    private int type;
   private FragmentEventgoodsListBinding mBinding;

    EventGoodsListViewModel mViewModel;

    /**
     *
     * @param eventId
     * @param type 活动商品列表的类型
     *             1.全部活动商品  2.积分兑换 3.热门 4.最新
     * @return
     */
    public static EventGoodsListFragment newInstance(String eventId,int type){
        EventGoodsListFragment fragment = new EventGoodsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("eventId",eventId);
        bundle.putInt("type",type);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreateBinding() {
        if(getArguments()!=null){
            eventId = getArguments().getString("eventId");
            type = getArguments().getInt("type");
        }
        Timber.d("oncreate  type %d",type);
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentEventgoodsListBinding.inflate(inflater);
        mViewModel = new EventGoodsListViewModel(new EventGoodsListModel(_mActivity));
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        setUpEventGoodsList();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Timber.d("onHiddenChanged");
    }

    private void setUpEventGoodsList() {
        EventGoodsListAdapter adapter = new EventGoodsListAdapter(_mActivity,mViewModel.data);
        mBinding.rvEventgoodlist.setAdapter(adapter);
        mViewModel.getGoodsList(eventId,type);
    }
}
