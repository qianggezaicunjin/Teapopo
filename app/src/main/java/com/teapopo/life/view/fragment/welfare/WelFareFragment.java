package com.teapopo.life.view.fragment.welfare;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentWelfareBinding;

import com.teapopo.life.model.welfare.Event;
import com.teapopo.life.util.navigator.Navigator;
import com.teapopo.life.view.activity.GoodsHandleActivity;
import com.teapopo.life.view.activity.MainActivity;
import com.teapopo.life.view.adapter.recyclerview.EventListAdapter;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.view.fragment.BaseFragment;
import com.teapopo.life.viewModel.welfare.EventListViewModel;


import javax.inject.Inject;

/**
 * Created by louiszgm-pc on 2016/5/20.
 */
public class WelFareFragment extends BaseFragment implements BaseRecyclerViewAdapter.OnItemClickListener {

    @Inject
    EventListViewModel mViewModel;

    private FragmentWelfareBinding mBinding;
    public static WelFareFragment newInstance(){
        return new WelFareFragment();
    }
    @Override
    public void onCreateBinding() {
        ((MainActivity)_mActivity).getMainFragmentComponent().inject(this);
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentWelfareBinding.inflate(inflater);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        setUpToolBar();
        setUpEventList();
    }

    private void setUpToolBar() {
        mBinding.welfareToolbar.inflateMenu(R.menu.menu_shoppingcart);
        mBinding.welfareToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Navigator.getInstance().start(_mActivity, GoodsHandleActivity.getStartIntent(_mActivity,null,GoodsHandleActivity.Navigate_TYPE_ShoppingCart));
                return true;
            }
        });
    }

    private void setUpEventList() {
        EventListAdapter adapter = new EventListAdapter(_mActivity,mViewModel.data);
        mBinding.rvEventlist.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        mViewModel.getEventList();
    }

    @Override
    public void onItemClick(View view, int position) {
        start(EventDetailFragment.newInstance((Event) mViewModel.data.get(position)));
    }
}
