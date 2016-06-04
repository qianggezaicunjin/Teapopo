package com.teapopo.life.model.comment;

import android.content.Context;

import com.google.gson.JsonObject;
import com.teapopo.life.model.AuthorInfo;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by louiszgm on 2016/6/4.
 */
public class CommentModel extends BaseModel {
    public CommentModel(Context context) {
        super(context);
    }

    public void addComment(String id,int type,String content){
        Observable<JsonObject> observable = mDataManager.addComment(id,type,content);
        observable.subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .flatMap(new Func1<JsonObject, Observable<Comment>>() {
                    @Override
                    public Observable<Comment> call(JsonObject jsonObject) {
                        return null;
                    }
                })
                .subscribe(new RxSubscriber<Comment>() {
                    @Override
                    public void _onNext(Comment comment) {

                    }

                    @Override
                    public void _onError(String s) {

                    }
                });
    }

    private Comment handleCommentResponseJson(JsonObject jsonObject){
        Comment comment = new Comment();
        AuthorInfo authorInfo = new AuthorInfo();
        authorInfo.avatar = jsonObject.get("avatar").getAsString();
        authorInfo.nickname = jsonObject.get("nickname").getAsString();
        return null;
    }
}
