package com.teapopo.life.model.article.categoryarticle;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.DataUtils;
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
public class CategoryArticleModel extends BaseModel{
    private int mPage = 0;

    public CategoryArticleModel(Context context) {
        super(context);
    }

    /**
     *
     * @param category 参数值为:发现/新滋
     */
    public void getArticle(String category){
       getArticle(category,mPage);
    }

    /**
     *
     * @param category
     * @param p 页码
     */
    public void getArticle(String category,int p){
        //实际页码数为p+1
        Observable<JsonObject> observable = mDataManager.getCategoryArticle(category,p+1);
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

    private List<CategoryArticle> handleArticleJson(JsonObject jsonObject) throws Exception{
        List<CategoryArticle> results = new ArrayList<>();
        //页码
        mPage = jsonObject.get("page").getAsInt();

        JsonObject data = jsonObject.getAsJsonObject("data");
        //文章
        JsonArray posts = data.getAsJsonArray("posts");
        //文章附加的属性
        JsonObject members = data.getAsJsonObject("members");
        JsonObject images = data.getAsJsonObject("images");
        JsonObject tags = data.getAsJsonObject("tags");
        //解析json,将所有属性整合到一个CategoryArticle对象里面
        for(JsonElement post:posts){
            CategoryArticle article = LoganSquare.parse(post.toString(),CategoryArticle.class);
            //取得文章的会员信息
            JsonObject memberInfo = members.getAsJsonObject(article.member_id);
            article.avatarUrl = memberInfo.get("avatar").getAsString();
            article.nickname = memberInfo.get("nickname").getAsString();
            //取得文章的图片url地址
            if(images!=null){
                JsonArray imageUrlsArray = images.getAsJsonArray(article.articleId);
                if(imageUrlsArray!=null){
                    List<String> imageUrls = LoganSquare.parseList(imageUrlsArray.toString(),String.class);
                    article.imageUrls = imageUrls;
                }
            }
            //取得文章的标签信息
            if(tags!=null){
                JsonArray tagsArray = tags.getAsJsonArray(article.articleId);
                if(tagsArray!=null){
                    List<String> tagNames = LoganSquare.parseList(tagsArray.toString(),String.class);
                    article.tags = tagNames;
                }
            }
            //将新封装好的article对象加进results
            results.add(article);
        }
        return results;
    }
}
