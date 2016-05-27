package com.teapopo.life.model.article;

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
 * Created by louiszgm on 2016/5/27.
 */
public class ArticleItemModel extends BaseModel {
    public ArticleItemModel(Context context) {
        super(context);
    }

    public void likeArticleOrNot(boolean isLike,String articleId){
        Observable<JsonObject> observable = mDataManager.likeArticleOrNot(isLike,articleId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .subscribe(new RxSubscriber<JsonObject>() {
                    @Override
                    public void _onNext(JsonObject jsonObject) {
                        ModelAction action = new ModelAction();
                        action.action = Action.ArticleItemModel_likeArticleOrNot;
                        mRequestView.onRequestSuccess(action);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }
}
