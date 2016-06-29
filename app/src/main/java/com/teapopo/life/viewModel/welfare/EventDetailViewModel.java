package com.teapopo.life.viewModel.welfare;

import com.teapopo.life.model.welfare.Event;
import com.teapopo.life.model.welfare.EventGoodsListModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.viewModel.BaseViewModel;

/**
 * Created by louiszgm on 2016/6/28.
 */
public class EventDetailViewModel extends BaseViewModel {

    public Event event;
    public EventDetailViewModel(EventGoodsListModel model){

    }

    public void attachGoodsOnEvent(Event event){

    }
    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action == Action.EventGoodsListModel_GetGoodsList){

        }
    }
}
