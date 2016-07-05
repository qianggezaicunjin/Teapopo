package com.teapopo.life.view.fragment.welfare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teapopo.life.databinding.FragmentGoodsdetailBinding;
import com.teapopo.life.model.welfare.GoodsDetail.GoodsDetailModel;
import com.teapopo.life.view.activity.GoodsHandleActivity;
import com.teapopo.life.view.adapter.viewpager.ArticleInfoImageAdapter;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.welfare.GoodsDetailViewModel;

import javax.inject.Inject;

/**
 * Created by louiszgm on 2016/7/5.
 */
public class GoodsDetailFragment extends SwipeBackBaseFragment {


    GoodsDetailViewModel mViewModel;

    private FragmentGoodsdetailBinding mBinding;

    String goods_id;
    /**
     *
     * @param id 商品id
     * @return
     */
    public static GoodsDetailFragment newInstance(String id){
        GoodsDetailFragment fragment = new GoodsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("goods_id",id);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreateBinding() {
        mViewModel = new GoodsDetailViewModel(new GoodsDetailModel(_mActivity));
        goods_id = getArguments().getString("goods_id");
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentGoodsdetailBinding.inflate(inflater);
        mBinding.setViewmodel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        setUpGoodsInfo();
    }

    private void setUpGoodsInfo() {
        ArticleInfoImageAdapter adapter = new ArticleInfoImageAdapter(_mActivity,mViewModel.goodsInfo.wap_images);
        mBinding.viewpager.setAdapter(adapter);
        mBinding.indicatorViewpager.setViewPager(mBinding.viewpager);
        //获取数据
        mViewModel.getGoodsInfo(goods_id);
    }
}
