package com.teapopo.life.model.article.categoryarticle;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonArray;
import com.teapopo.life.model.toparticle.TopArticle;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/31.
 */
public class XinZiArticleModel extends CategoryArticleModel {

    public XinZiArticleModel(Context context) {
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
                            action.action = Action.XinZiArticleModel_GetTopArticle;
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
}
