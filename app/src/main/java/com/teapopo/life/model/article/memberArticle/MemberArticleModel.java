package com.teapopo.life.model.article.memberArticle;

import android.content.Context;

import com.google.gson.JsonObject;
import com.teapopo.life.model.article.ArticleModel;
import com.teapopo.life.model.article.categoryarticle.CategoryArticle;
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
 * Created by louiszgm on 2016/7/20.
 */
public class MemberArticleModel extends ArticleModel {
    public MemberArticleModel(Context context) {
        super(context);
    }

    public void getArticle(String memberId){
        Observable<JsonObject> observable = mDataManager.getMemberArticle(memberId,mPage);
        observable.subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .flatMap(new Func1<JsonObject, Observable<List<MemberArticle>>>() {
                    @Override
                    public Observable<List<MemberArticle>> call(JsonObject jsonObject) {
                        List<MemberArticle> list =  new ArrayList<MemberArticle>();
                        try {
                            list = handleArticleJson(jsonObject);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return Observable.just(list);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<List<MemberArticle>>() {
                    @Override
                    public void _onNext(List<MemberArticle> memberArticles) {
                        ModelAction<List<MemberArticle>> action = new ModelAction<List<MemberArticle>>();
                        action.action = Action.MemberArticleModel_GetArticle;
                        action.t = memberArticles;
                        mRequestView.onRequestSuccess(action);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }
}
