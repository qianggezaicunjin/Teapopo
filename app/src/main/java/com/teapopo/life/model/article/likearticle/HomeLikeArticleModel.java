package com.teapopo.life.model.article.likearticle;

import android.content.Context;

import com.google.gson.JsonObject;
import com.teapopo.life.model.article.ArticleModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by louiszgm on 2016/5/30.
 */
public class HomeLikeArticleModel extends ArticleModel {

    public HomeLikeArticleModel(Context context) {
        super(context);
    }

    public void getArticle(boolean isHomeArticle){
        Observable<JsonObject> observable = mDataManager.getLikeArticle(isHomeArticle,mPage);
        observable.subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .flatMap(new Func1<JsonObject, Observable< List<HomeLikeArticle>>>() {
                    @Override
                    public Observable< List<HomeLikeArticle>> call(JsonObject jsonObject) {
                        List<HomeLikeArticle> articleList = new ArrayList<HomeLikeArticle>();
                        try {
                            articleList = handleArticleJson(jsonObject);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return Observable.just(articleList);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<List<HomeLikeArticle>>() {
                    @Override
                    public void _onNext(List<HomeLikeArticle> homeLikeArticles) {
                        ModelAction<List<HomeLikeArticle>> action = new ModelAction<List<HomeLikeArticle>>();
                        action.action = Action.HomeLikeArticleModel_GetArticle;
                        action.t = homeLikeArticles;
                        mRequestView.onRequestSuccess(action);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }

}
