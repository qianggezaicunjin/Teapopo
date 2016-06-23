package com.teapopo.life.model.comment;

import android.content.Context;

import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by louiszgm on 2016/6/4.
 */
public class CommentModel extends BaseModel {
    public CommentModel(Context context) {
        super(context);
    }

    public void replyComment(String id,int type,String content){
        Observable<JsonObject>  observable = mDataManager.replyComment(id,type,content);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .subscribe(new RxSubscriber<JsonObject>() {
                    @Override
                    public void _onNext(JsonObject jsonObject) {
                        ModelAction action = new ModelAction();
                        action.action = Action.CommentModel_ReplyComment;
                        mRequestView.onRequestSuccess(action);
                    }

                    @Override
                    public void _onError(String s) {

                    }
                });
    }

    public void clickLikeCommentOrNot(String type , String commentId){
        Observable<JsonObject> observable = mDataManager.clickLikeComment(type,commentId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .subscribe(new RxSubscriber<JsonObject>() {
                    @Override
                    public void _onNext(JsonObject jsonObject) {
                        ModelAction modelAction = new ModelAction();
                        modelAction.action = Action.CommentModel_ClickLikeComment;
                        mRequestView.onRequestSuccess(modelAction);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }
}
