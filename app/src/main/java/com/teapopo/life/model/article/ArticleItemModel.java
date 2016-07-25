package com.teapopo.life.model.article;

import android.content.Context;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonObject;
import com.teapopo.life.model.AuthorInfo;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import java.io.IOException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by louiszgm on 2016/5/27.
 */
public class ArticleItemModel extends BaseModel {
    public ArticleItemModel(Context context) {
        super(context);
    }


    /**
     * 点赞文章或者取消点赞
     * @param isLike
     * @param articleId
     */
    public void likeArticleOrNot(boolean isLike,String articleId){
        Observable<JsonObject> observable = mDataManager.clickLikeArticle(isLike,articleId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .subscribe(new RxSubscriber<JsonObject>() {
                    @Override
                    public void _onNext(JsonObject jsonObject) {
                        ModelAction action = new ModelAction();
                        action.action = Action.ArticleItemModel_likeArticleOrNot;
                        mRequestView.onRequestSuccess(action);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }

    /**
     * 关注会员
     * @param memberId
     */
    public void focusMember( String memberId){
        Observable<JsonObject> observable = mDataManager.focusmember(memberId);
        observable.subscribeOn(Schedulers.io())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<JsonObject>() {
                    @Override
                    public void _onNext(JsonObject jsonObject) {
                        ModelAction modelAction = new ModelAction();
                        modelAction.action = Action.ArticleItemModel_focusMember;
                        mRequestView.onRequestSuccess(modelAction);
                    }

                    @Override
                    public void _onError(String s) {

                    }
                });
    }

    //获取会员信息数据
    public void getMemberInfo(String member_id){
        Observable<JsonObject> observable = mDataManager.getMemberInfo(member_id);
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .flatMap(new Func1<JsonObject, Observable<AuthorInfo>>() {
                    @Override
                    public Observable<AuthorInfo> call(JsonObject jsonObject) {
                        AuthorInfo authorInfo = null;
                        try {
                            authorInfo = LoganSquare.parse(jsonObject.toString(),AuthorInfo.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return Observable.just(authorInfo);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<AuthorInfo>() {
                    @Override
                    public void _onNext(AuthorInfo authorInfo) {
                        ModelAction modelAction=new ModelAction();
                        modelAction.action=Action.ArticleItemModel_GetMemberInfo;
                        modelAction.t=authorInfo;
                        mRequestView.onRequestSuccess(modelAction);
                    }

                    @Override
                    public void _onError(String s) {

                    }
                });
    }
}
