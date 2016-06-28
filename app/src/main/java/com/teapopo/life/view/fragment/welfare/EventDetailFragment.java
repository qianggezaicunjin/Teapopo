package com.teapopo.life.view.fragment.welfare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.FragmentEventdetailBinding;
import com.teapopo.life.model.welfare.Event;
import com.teapopo.life.model.welfare.EventGoodsListModel;
import com.teapopo.life.view.adapter.viewpager.HomeTabFragmentAdapter;
import com.teapopo.life.view.fragment.Home.HomeLikeArticleFragment;
import com.teapopo.life.view.fragment.Home.RecommendArticleFragment;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.welfare.EventDetailViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louiszgm on 2016/6/28.
 */
public class EventDetailFragment extends SwipeBackBaseFragment {

    private FragmentEventdetailBinding mBinding;

    private EventDetailViewModel mViewModel;
    private Event event;
    public static EventDetailFragment newInstance(Event event){
        EventDetailFragment fragment = new EventDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("event",event);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreateBinding() {
        if(getArguments()!=null){
            event = getArguments().getParcelable("event");
        }
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentEventdetailBinding.inflate(inflater);
        mViewModel = new EventDetailViewModel(new EventGoodsListModel(_mActivity));
        mBinding.setEvent(event);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
//        setUpToolBar();
        setUpTabLayout();
    }



    private void setUpTabLayout() {
        List<String> titles=new ArrayList<>();

        titles.add("全部");
        titles.add("热门");
        titles.add("最新");
        titles.add("积分兑换");

        List<Fragment> fragmentList = new ArrayList<>();
        EventGoodsListFragment allGoods = EventGoodsListFragment.newInstance(event);
        fragmentList.add(allGoods);


        //如果在fragment里面嵌套的viewpager里面再嵌套fragment,则需要getChildFragmentManager()
        HomeTabFragmentAdapter tabAdapter=new HomeTabFragmentAdapter(getChildFragmentManager(),fragmentList,titles);
        mBinding.ucvp.setAdapter(tabAdapter);
        mBinding.tabs.setupWithViewPager(mBinding.ucvp);
    }
}
