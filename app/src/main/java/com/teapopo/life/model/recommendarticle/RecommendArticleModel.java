package com.teapopo.life.model.recommendarticle;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.Tag.Tag;
import com.teapopo.life.model.article.categoryarticle.CategoryArticleModel;
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
public class RecommendArticleModel  extends CategoryArticleModel{

    public RecommendArticleModel(Context context){
        super(context);
    }

//    //获取分类标签
//    public void getCategory(){
//        Observable<JsonObject> observable = mDataManager.getCategorys();
//        observable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(RxResultHelper.<JsonObject>handleResult())
//                .subscribe(new RxSubscriber<JsonObject>() {
//                    @Override
//                    public void _onNext(JsonObject jsonObject) {
//                        CategoryList categoryList = null;
//                        try {
//                            categoryList = LoganSquare.parse(jsonObject.toString(),CategoryList.class);
//                            ModelAction<List<Tag>> action = new ModelAction<List<Tag>>();
//                            action.action = Action.RecommendArticleModel_GetCategory;
//                            action.t = categoryList.data.categoryList;
//                            mRequestView.onRequestSuccess(action);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void _onError(String s) {
//                        mRequestView.onRequestErroInfo(s);
//                    }
//                });
//    }
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
}
