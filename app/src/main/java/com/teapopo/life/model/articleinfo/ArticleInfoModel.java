package com.teapopo.life.model.articleinfo;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teapopo.life.model.AuthorInfo;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.article.Article;
import com.teapopo.life.model.sharedpreferences.RxSpf_Html;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<ArticleInfo>() {
                    @Override
                    public void _onNext(ArticleInfo articleInfo) {
                        ModelAction<ArticleInfo> action = new ModelAction<ArticleInfo>();
                        action.action = Action.ArticleInfoModel_GetInfo;
                        action.t = articleInfo;
                        mRequestView.onRequestSuccess(action);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });

    }

    private ArticleInfo handleArticleInfoJson(JsonObject jsonObject) throws IOException {
        JsonObject post = jsonObject.getAsJsonObject("posts");
        JsonObject members = jsonObject.getAsJsonObject("members");

        ArticleInfo articleInfo = LoganSquare.parse(post.toString(), ArticleInfo.class);

        JsonArray images = jsonObject.getAsJsonArray("images");
        if(images.size()>0){
            for(JsonElement image:images){
                String url = ((JsonObject)image).get("filename").getAsString();
                articleInfo.articleImageUrls.add(url);
            }
        }
        //文章作者的个人信息
        JsonObject member = members.getAsJsonObject(articleInfo.member_id);
        AuthorInfo authorInfo = LoganSquare.parse(member.toString(),AuthorInfo.class);
        articleInfo.authorInfo = authorInfo;
        //文章的标签
        for(JsonElement tag:jsonObject.getAsJsonArray("tags")){
            articleInfo.tags.add(tag.getAsString());
        }
        //添加喜欢的人的头像
        JsonArray likes = jsonObject.getAsJsonArray("likes");
        if(likes.size()>0){
            for(JsonElement jsonElement:likes){
                String member_id = jsonElement.getAsJsonObject().get("member_id").getAsString();
                JsonObject member_like = members.getAsJsonObject(member_id);
                AuthorInfo like_authorInfo = LoganSquare.parse(member_like.toString(),AuthorInfo.class);
                articleInfo.member_like.add(like_authorInfo);
            }
        }
        return articleInfo;
    }
}
