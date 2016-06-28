package com.teapopo.life.view.fragment.welfare;

import android.databinding.Bindable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.model.welfare.Event;
import com.teapopo.life.view.adapter.recyclerview.EventGoodsListAdapter;
import com.teapopo.life.view.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by louiszgm on 2016/6/28.
 */
public class EventGoodsListFragment extends BaseFragment {

    private Event event;
    private View mContentView;

    @Bind(R.id.rv_eventgoodlist)
    RecyclerView recyclerView;

    public static EventGoodsListFragment newInstance(Event event){
        EventGoodsListFragment fragment = new EventGoodsListFragment();
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
        mContentView = inflater.inflate(R.layout.fragment_eventgoods_list,null);
        ButterKnife.bind(mContentView);
        return mContentView;
    }

    @Override
    public void setUpView() {
        setUpEventGoodsList();
    }

    private void setUpEventGoodsList() {
        EventGoodsListAdapter adapter = new EventGoodsListAdapter(_mActivity,event.goods);
        recyclerView.setAdapter(adapter);
    }
}
