package com.teapopo.life.model.articleinfo;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.article.Article;
import com.teapopo.life.model.sharedpreferences.RxSpf_Html;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import java.io.File;
import java.io.IOException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

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
                .observeOn(mDataManager.getScheduler())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .flatMap(new Func1<JsonObject, Observable<ArticleInfo>>() {
                    @Override
                    public Observable<ArticleInfo> call(JsonObject jsonObject) {
                        ArticleInfo articleInfo = new ArticleInfo();
                        try {
                            articleInfo = handleArticleInfoJson(jsonObject);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return Observable.just(articleInfo);
                    }
                })
                .subscribe(new RxSubscriber<ArticleInfo>() {
                    @Override
                    public void _onNext(ArticleInfo articleInfo) {
                        ModelAction<ArticleInfo> action = new ModelAction<ArticleInfo>();
                        action.action = Action.ArticleInfoModel_GetInfo;
                        action.t = articleInfo;
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });

    }

    private ArticleInfo handleArticleInfoJson(JsonObject jsonObject) throws IOException {
        JsonObject post = jsonObject.getAsJsonObject("posts");

        ArticleInfo articleInfo = LoganSquare.parse(post.toString(), ArticleInfo.class);

        JsonArray images = jsonObject.getAsJsonArray("images");
        if(images.size()>0){
            for(JsonElement image:images){
                String url = ((JsonObject)image).get("filename").getAsString();
                articleInfo.articleImageUrls.add(url);
            }
        }

        return articleInfo;
    }
}
