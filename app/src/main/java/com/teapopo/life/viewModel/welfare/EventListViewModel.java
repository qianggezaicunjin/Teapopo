package com.teapopo.life.viewModel.welfare;

import com.teapopo.life.BR;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.welfare.EventList.EventListModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;

import java.util.List;

/**
 * Created by louiszgm on 2016/6/22.
 */
public class EventListViewModel extends BaseRecyclerViewModel{

    private EventListModel mModel;

    public EventListViewModel(EventListModel model){
        mModel = model;
        mModel.setView(this);
    }

    public void getEventList(){
        mModel.getEventList();
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action == Action.EventListModel_GetEventList){
            List<BaseEntity> eventlist = (List<BaseEntity>) data.t;

            super.data.addAll(eventlist);
            notifyPropertyChanged(BR.data);
        }
    }
}
