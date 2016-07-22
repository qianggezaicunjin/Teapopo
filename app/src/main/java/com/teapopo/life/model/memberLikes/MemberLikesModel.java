package com.teapopo.life.model.memberLikes;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by louiszgm on 2016/7/22.
 */
public class MemberLikesModel extends BaseModel {

    public MemberLikesModel(Context context) {
        super(context);
    }

    public void getMemberLikes(final String memberId){
        Observable<JsonObject> observable = mDataManager.getMemberLikes(memberId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .flatMap(new Func1<JsonObject, Observable<List<MemberLike>>>() {
                    @Override
                    public Observable<List<MemberLike>> call(JsonObject jsonObject) {
                        List<MemberLike> memberLikeList = handleMemberLikesJson(jsonObject);
                        return Observable.just(memberLikeList);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<List<MemberLike>>() {
                    @Override
                    public void _onNext(List<MemberLike> memberLikes) {
                        ModelAction modelAction = new ModelAction();
                        modelAction.action = Action.MemberLikesModel_GetMemberLikes;
                        modelAction.t = memberLikes;
                        mRequestView.onRequestSuccess(modelAction);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }
    private List<MemberLike> handleMemberLikesJson(JsonObject jsonObject){
        List<MemberLike> memberLikeList = new ArrayList<>();
        JsonObject data = jsonObject.getAsJsonObject("data");
        JsonObject likes = data.getAsJsonObject("likes");
        JsonObject like_count = data.getAsJsonObject("like_count");
        for(Map.Entry entry:likes.entrySet()){
            MemberLike memberLike = new MemberLike();
            memberLike.date = entry.getKey().toString();
            try {
                memberLike.dataOverViewList = LoganSquare.parseList(entry.getValue().toString(),MemberLikeDataOverView.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            memberLike.likeCounts = like_count.get(memberLike.date).getAsString();
            memberLikeList.add(memberLike);
        }
        return memberLikeList;
    }
}
