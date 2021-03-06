package com.teapopo.life.model.article.categoryarticle;

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
 * Created by louiszgm on 2016/5/26.
 */
public class CategoryArticleModel extends ArticleModel{


    public CategoryArticleModel(Context context) {
        super(context);
    }

    public void getArticle(String category){
        Observable<JsonObject> observable = mDataManager.getCategoryArticle(category,mPage);
        observable.subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .flatMap(new Func1<JsonObject, Observable<List<CategoryArticle>>>() {
                    @Override
                    public Observable<List<CategoryArticle>> call(JsonObject jsonObject) {
                        List<CategoryArticle> list =  new ArrayList<CategoryArticle>();
                        try {
                            list = handleArticleJson(jsonObject);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return Observable.just(list);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<List<CategoryArticle>>() {
                    @Override
                    public void _onNext(List<CategoryArticle> categoryArticles) {
                        ModelAction<List<CategoryArticle>> action = new ModelAction<List<CategoryArticle>>();
                        action.action = Action.CategoryArticleModel_GetArticle;
                        action.t = categoryArticles;
                        mRequestView.onRequestSuccess(action);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }

}
