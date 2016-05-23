package com.teapopo.life.model.recommendarticle;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.category.Category;
import com.teapopo.life.model.category.CategoryList;
import com.teapopo.life.model.event.AddHeaderEvent;
import com.teapopo.life.model.toparticle.TopArticle;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by louiszgm-pc on 2016/5/2.
 *
 */
public class RecommendArticleModel  extends BaseModel{

    private int mPage = 0;
    public RecommendArticleModel(Context context){
        super(context);
    }

    public void getContents(){
        getContents(mPage);
    }

    //获取分类标签
    public void getCategory(){
        Observable<JsonObject> observable = mDataManager.getCategorys();
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .subscribe(new RxSubscriber<JsonObject>() {
                    @Override
                    public void _onNext(JsonObject jsonObject) {
                        CategoryList categoryList = null;
                        try {
                            categoryList = LoganSquare.parse(jsonObject.toString(),CategoryList.class);
                            ModelAction<List<Category>> action = new ModelAction<List<Category>>();
                            action.action = Action.RecommendArticleModel_GetCategory;
                            action.t = categoryList.data.categoryList;
                            mRequestView.onRequestSuccess(action);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
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
                            Timber.d("TopArticle的JSONOBJECT为:%s",jsonArray.toString());
                           List<TopArticle> topArticleList = LoganSquare.parseList(jsonArray.toString(),TopArticle.class);
                            ModelAction<List<TopArticle>> action = new ModelAction<List<TopArticle>>();
                            action.action = Action.RecommendArticleModel_GetTopArticle;
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
    //获取文章内容
    public void getContents(int page) {
        Timber.d("请求第几页数据%d",page);
        mDataManager.getRecommendArticle(page+1)
                .subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .observeOn(mDataManager.getScheduler())
                //操作服务器返回来的数据
                .map(new Func1<JsonObject, List<RecommendArticle>>() {

                    @Override
                    public List<RecommendArticle> call(JsonObject jsonObject) {
                        Recommend recommend  = new Recommend();
                        try {
                           recommend = LoganSquare.parse(jsonObject.toString(),Recommend.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        List<RecommendArticle> lists=recommend.data.recommendArticles;
                         mPage = recommend.page;
                        for(int i=0;i<lists.size();i++){
                            RecommendArticle article = lists.get(i);
                            //找到该文章对应的文章图片,昵称，封面图片等信息
                            article.articleImage = getAriticleImage(article,recommend.data);
                            article.nickname = getArticleAuthorInfo(article,recommend.data).nickname;
                            article.avatarUrl = getArticleAuthorInfo(article,recommend.data).avatarUrl;
                            lists.set(lists.indexOf(article),article);
                        }
                        return lists;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<List<RecommendArticle>>() {
                    @Override
                    public void _onNext(List<RecommendArticle> recommendArticles) {
                        ModelAction<List<RecommendArticle>> action = new ModelAction<List<RecommendArticle>>();
                        action.action = Action.RecommendArticleModel_GetContents;
                        action.t = recommendArticles;
                        mRequestView.onRequestSuccess(action);
                        mRxBus.post(new AddHeaderEvent());
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });

    }

    /**
     * 获得文章的图片
     * @param article
     * @param data
     * @return
     */
    private ArticleImage getAriticleImage(RecommendArticle article, RecommendData data) {
        String articleId=article.articleId;
        for(int i=0;i<data.articleImages.size();i++){
            String id = data.articleImages.get(i).articleId;
            if(articleId.equals(id)){
                return data.articleImages.get(i);
            }
        }
        return  null;
    }

    /**
     * 获取文章的作者信息
     * @param article
     * @param data
     * @return
     */
    private ArticleAuthorInfo getArticleAuthorInfo(RecommendArticle article, RecommendData data){
        String authorId=article.member_id;
        for(int i=0;i<data.articleAuthorInfos.size();i++){
            String id = data.articleAuthorInfos.get(i).authorId;
            if(authorId.equals(id)){
                return data.articleAuthorInfos.get(i);
            }
        }
        return null;
    }
}
