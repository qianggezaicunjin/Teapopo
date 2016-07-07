package com.teapopo.life.viewModel.welfare;

import android.databinding.Bindable;
import android.databinding.BindingConversion;

import com.teapopo.life.BR;
import com.teapopo.life.model.AuthorInfo;
import com.teapopo.life.model.comment.Comment;
import com.teapopo.life.model.welfare.EventGoodsInfo;
import com.teapopo.life.model.welfare.GoodsDetail.GoodsDetailModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;
import com.teapopo.life.viewModel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/7/5.
 */
public class GoodsDetailViewModel extends BaseRecyclerViewModel {

    private GoodsDetailModel mModel;

    @Bindable
    public EventGoodsInfo goodsInfo = new EventGoodsInfo();

    @Bindable
    public List<AuthorInfo> collectList = new ArrayList<>();

    public void setGoodsInfo(EventGoodsInfo eventGoodsInfo){
        goodsInfo = eventGoodsInfo;
    }

    public void setCollectList(List<AuthorInfo> list){
        collectList = list;
    }
    public GoodsDetailViewModel(GoodsDetailModel model){
        mModel = model;
        mModel.setView(this);
    }

    public void getGoodsInfo(String id){
        mModel.getGoodsDetail(id);
    }

    public void getCollectList(String id){
        mModel.getCollectList(id);
    }

    public void getCommentList(String goods_id) {
        mModel.getCommentList(goods_id,"goods");
    }
    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action == Action.GoodsDetailModel_GetGoodsInfo){
            goodsInfo = (EventGoodsInfo) data.t;
            notifyPropertyChanged(BR.goodsInfo);
        }else if (action == Action.GoodsDetailModel_GetCollectList){
            List<AuthorInfo> list = (List<AuthorInfo>) data.t;
            Timber.d("收藏数为:%d",list.size());
            collectList.addAll(list);
            notifyPropertyChanged(BR.collectList);
        }else if(action == Action.GoodsDetailModel_GetCommentList){
            List<Comment> commentList = (List<Comment>) data.t;
            super.data.addAll(commentList);
            notifyPropertyChanged(BR.data);
        }
    }


}
