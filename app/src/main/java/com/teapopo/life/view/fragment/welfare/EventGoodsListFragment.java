package com.teapopo.life.view.fragment.welfare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentEventgoodsListBinding;
import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.model.welfare.EventGoodsList.EventGoodsListModel;
import com.teapopo.life.view.adapter.recyclerview.EventGoodsListAdapter;
import com.teapopo.life.view.adapter.recyclerview.base.BaseRecyclerViewAdapter;
import com.teapopo.life.view.fragment.BaseFragment;
import com.teapopo.life.viewModel.welfare.EventGoodsListViewModel;

import me.yokeyword.fragmentation.SupportActivity;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/28.
 */
public class EventGoodsListFragment extends BaseFragment implements BaseRecyclerViewAdapter.OnItemClickListener {

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

        adapter.setOnItemClickListener(this);
        mViewModel.getGoodsList(eventId,type);
    }

    @Override
    public void onItemClick(View view, int position) {

        EventGoods eventGoods = (EventGoods) mViewModel.data.get(position);
        ((SupportActivity)_mActivity).start(GoodsDetailFragment.newInstance(eventGoods.goods_id,eventGoods.id));
    }
}
