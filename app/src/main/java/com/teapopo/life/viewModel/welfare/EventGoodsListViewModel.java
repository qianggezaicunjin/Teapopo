package com.teapopo.life.viewModel.welfare;

import com.teapopo.life.BR;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.welfare.EventGoodsList.EventGoodsListModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;

import java.util.List;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/29.
 */
public class EventGoodsListViewModel extends BaseRecyclerViewModel {

    private EventGoodsListModel mModel;
    public EventGoodsListViewModel(EventGoodsListModel model){
        mModel = model;
        mModel.setView(this);
    }

    public void getGoodsList(String eventId,int type){
        mModel.getEventGoodsListModel(eventId,type);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action == Action.EventGoodsListModel_GetGoodsList){
            List<BaseEntity> list = (List<BaseEntity>) data.t;
            Timber.d("onRequestSuccess  大小为:%d",list.size());
            super.data.addAll(list);
            notifyPropertyChanged(BR.data);
        }
    }
}
