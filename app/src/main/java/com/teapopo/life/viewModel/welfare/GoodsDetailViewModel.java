package com.teapopo.life.viewModel.welfare;

import android.databinding.Bindable;

import com.teapopo.life.BR;
import com.teapopo.life.model.welfare.EventGoodsInfo;
import com.teapopo.life.model.welfare.GoodsDetail.GoodsDetailModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.viewModel.BaseViewModel;

/**
 * Created by louiszgm on 2016/7/5.
 */
public class GoodsDetailViewModel extends BaseViewModel {

    private GoodsDetailModel mModel;

    @Bindable
    public EventGoodsInfo goodsInfo = new EventGoodsInfo();

    public void setGoodsInfo(EventGoodsInfo eventGoodsInfo){
        goodsInfo = eventGoodsInfo;
    }
    public GoodsDetailViewModel(GoodsDetailModel model){
        mModel = model;
        mModel.setView(this);
    }

    public void getGoodsInfo(String id){
        mModel.getGoodsDetail(id);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action == Action.GoodsDetailModel_GetGoodsInfo){
            goodsInfo = (EventGoodsInfo) data.t;
            notifyPropertyChanged(BR.goodsInfo);
        }else if (action == Action.GoodsDetailModel_GetCollectList){

        }else if(action == Action.GoodsDetailModel_GetCommentList){

        }
    }
}
