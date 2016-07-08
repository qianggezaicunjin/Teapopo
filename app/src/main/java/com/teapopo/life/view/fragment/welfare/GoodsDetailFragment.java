package com.teapopo.life.view.fragment.welfare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentGoodsdetailBinding;
import com.teapopo.life.model.welfare.GoodsDetail.GoodsDetailModel;
import com.teapopo.life.util.navigator.Navigator;
import com.teapopo.life.view.activity.GoodsHandleActivity;
import com.teapopo.life.view.adapter.flexbox.ArticleFansAdapter;
import com.teapopo.life.view.adapter.recyclerview.CommentListAdapter;
import com.teapopo.life.view.adapter.viewpager.ArticleInfoImageAdapter;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.welfare.GoodsDetailViewModel;

import javax.inject.Inject;

/**
 * Created by louiszgm on 2016/7/5.
 */
public class GoodsDetailFragment extends SwipeBackBaseFragment {


    @Inject
    GoodsDetailViewModel mViewModel;

    private FragmentGoodsdetailBinding mBinding;

    String goods_id;

    String id;
    /**
     *
     * @param goods_id 商品id
     * @param id 活动商品id
     * @return
     */
    public static GoodsDetailFragment newInstance(String goods_id,String id ){
        GoodsDetailFragment fragment = new GoodsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("goods_id",goods_id);
        bundle.putString("id",id);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreateBinding() {
        ((GoodsHandleActivity)_mActivity).getFragmentComponent().inject(this);
        goods_id = getArguments().getString("goods_id");
        id = getArguments().getString("id");
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
        setUpCollectList();
        setUpCommentList();
        setUpToolBar();
    }

    private void setUpCommentList() {
        CommentListAdapter adapter = new CommentListAdapter(_mActivity,mViewModel.data);
        mBinding.rvGoodsdetailCommentlist.setAdapter(adapter);

        mViewModel.getCommentList(goods_id);
    }

    private void setUpCollectList() {
        ArticleFansAdapter adapter = new ArticleFansAdapter(_mActivity);
        adapter.setDataSource(mViewModel.collectList);
        mBinding.flexboxAddcollect.setAdapter(adapter);

        mViewModel.getCollectList(goods_id);
    }

    private void setUpGoodsInfo() {
        ArticleInfoImageAdapter adapter = new ArticleInfoImageAdapter(_mActivity,mViewModel.goodsInfo.wap_images);
        mBinding.viewpager.setAdapter(adapter);
        mBinding.indicatorViewpager.setViewPager(mBinding.viewpager);
        //获取数据
        mViewModel.getGoodsInfo(id);
    }
    //设置标题栏按钮
    public void setUpToolBar(){
        Toolbar toolbar=mBinding.tbGoodsdetail;
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.inflateMenu(R.menu.menu_shoppingcart);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Navigator.getInstance().start(_mActivity, GoodsHandleActivity.getStartIntent(_mActivity,null,GoodsHandleActivity.Navigate_TYPE_ShoppingCart));
                return true;
            }
        });
    }
}
