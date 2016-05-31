package com.teapopo.life.model.articleinfo;

import android.content.Context;

import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by louiszgm-pc on 2016/5/31.
 */
public class ArticleInfoModel extends BaseModel {
    public ArticleInfoModel(Context context) {
        super(context);
    }

    public void getArticleInfo(String articleId){
        Observable<JsonObject> observable = mDataManager.getArticleInfo(articleId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .subscribe(new RxSubscriber<JsonObject>() {
                    @Override
                    public void _onNext(JsonObject jsonObject) {
                        JsonObject post = jsonObject.getAsJsonObject("posts");
                        mRequestView.onRequestSuccess(post.get("content").getAsString());
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }
}
