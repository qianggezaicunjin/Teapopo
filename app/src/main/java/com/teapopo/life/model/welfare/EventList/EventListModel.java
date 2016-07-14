package com.teapopo.life.model.welfare.EventList;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teapopo.life.R;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.toparticle.TopArticle;
import com.teapopo.life.model.welfare.Event;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/22.
 */
public class EventListModel extends BaseModel {
    private int mPage = 0;
    private int mPages = 1;
    public EventListModel(Context context) {
        super(context);
    }

    //获取头部滚动文章
    public void getTopArticle(String classify){
        Observable<JsonArray> observable = mDataManager.getTopArticle(classify);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxResultHelper.<JsonArray>handleResult())
                .subscribe(new RxSubscriber<JsonArray>() {
                    @Override
                    public void _onNext(JsonArray jsonArray) {
                        try {
                            List<TopArticle> topArticleList = LoganSquare.parseList(jsonArray.toString(),TopArticle.class);
                            ModelAction<List<TopArticle>> action = new ModelAction<List<TopArticle>>();
                            action.action = Action.EventListModel_GetTopAriticle;
                            action.t = topArticleList;
                            mRequestView.onRequestSuccess(action);
                        } catch (IOException e) {
                            Timber.e(e.toString());
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }

    public void getEventList(){
        mPage+=1;
        if(mPage<=mPages){
            Observable<JsonObject> observable = mDataManager.getEventList(mPage);
            observable.subscribeOn(Schedulers.io())
                    .compose(RxResultHelper.<JsonObject>handleResult())
                    .flatMap(new Func1<JsonObject, Observable<List<Event>>>() {
                        @Override
                        public Observable<List<Event>> call(JsonObject jsonObject) {
                            List<Event> eventList = handleEventListJson(jsonObject);
                            return Observable.just(eventList);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new RxSubscriber<List<Event>>() {
                        @Override
                        public void _onNext(List<Event> events) {
                            ModelAction modelAction = new ModelAction();
                            modelAction.action = Action.EventListModel_GetEventList;
                            modelAction.t = events;
                            mRequestView.onRequestSuccess(modelAction);
                        }

                        @Override
                        public void _onError(String s) {
                            mRequestView.onRequestErroInfo(s);
                        }
                    });

        }else {
            mRequestView.onRequestErroInfo("没有更多数据啦");
        }

    }

    private List<Event> handleEventListJson(JsonObject jsonObject){
        List<Event> eventList = new ArrayList<>();
        JsonObject data = jsonObject.getAsJsonObject("data");
        JsonArray events = data.getAsJsonArray("events");
        mPages = jsonObject.get("pages").getAsInt();
        for(JsonElement event:events){
            try {
                Event eventObject = LoganSquare.parse(event.toString(),Event.class);
                eventObject.coverImg = setWebImageSize(R.dimen.eventgoods_coverImg_width,R.dimen.eventgoods_coverImg_height,eventObject.coverImg);
                eventObject.bannerImg = setWebImageSize(R.dimen.eventgoods_coverImg_width,R.dimen.eventgoods_coverImg_height,eventObject.bannerImg);
                eventList.add(eventObject);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return eventList;
    }
}
