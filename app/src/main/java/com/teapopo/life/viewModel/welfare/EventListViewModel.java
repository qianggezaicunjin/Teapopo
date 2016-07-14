package com.teapopo.life.viewModel.welfare;

import android.databinding.Bindable;

import com.teapopo.life.BR;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.toparticle.TopArticle;
import com.teapopo.life.model.welfare.EventList.EventListModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.viewModel.BaseRecyclerViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louiszgm on 2016/6/22.
 */
public class EventListViewModel extends BaseRecyclerViewModel{

    private EventListModel mModel;

    @Bindable
    public List<BaseEntity> welfare_topArticleList = new ArrayList<>();

    public EventListViewModel(EventListModel model){
        mModel = model;
        mModel.setView(this);
    }

    public void getEventList(){
        mModel.getEventList();
    }

    public void getTopArticle(){
        mModel.getTopArticle("welfare");
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action == Action.EventListModel_GetEventList){
            List<BaseEntity> eventlist = (List<BaseEntity>) data.t;

            super.data.addAll(eventlist);
            notifyPropertyChanged(BR.data);
        }else if(action ==Action.EventListModel_GetTopAriticle){
            List<TopArticle> topAriticle = (List<TopArticle>) data.t;
            welfare_topArticleList.addAll(topAriticle);
            notifyPropertyChanged(BR.welfare_topArticleList);
        }
    }


}
