package com.teapopo.life.model.recommendarticle;

import android.content.Context;
import android.os.Looper;

import com.teapopo.life.MyApplication;
import com.teapopo.life.data.DataManager;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.util.DialogFactory;
import com.teapopo.life.view.adapter.recyclerview.RecommendArticleAdapter;
import com.teapopo.life.view.customView.RequestView;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by louiszgm-pc on 2016/5/2.
 */
public class RecommendArticleModel  {
    private DataManager mDataManager;
    private Context mContext;
    private int mPage = 0;
    private RequestView mRequestView;
    public RecommendArticleModel(Context context){
        this.mContext = context;
        mDataManager = MyApplication.get(mContext).getComponent().dataManager();
    }

    public void setView(RequestView<BaseEntity> requestView) {
        this.mRequestView = requestView;
    }

    public void getContents(){
        getContents(mPage);
    }

    public void getContents(int page) {
        Timber.d("请求第几页数据%d",page);
        mDataManager.getRecommendArticle(page+1)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<Recommend, List<RecommendArticle>>() {

                    @Override
                    public List<RecommendArticle> call(Recommend recommend) {
                        List<RecommendArticle> lists=recommend.data.recommendArticles;
                         mPage = recommend.page;
                        for(int i=0;i<lists.size();i++){
                            RecommendArticle article = lists.get(i);
                            article.articleImage = getAriticleImage(article,recommend.data);
                            article.nickname = getArticleAuthorInfo(article,recommend.data).nickname;
                            article.avatarUrl = getArticleAuthorInfo(article,recommend.data).avatarUrl;
                            lists.set(lists.indexOf(article),article);
                        }
                        return lists;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<RecommendArticle>>() {
                    @Override
                    public void onCompleted() {
                        mRequestView.onRequestFinished();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Timber.e("There was a problem loading the article " + e);
                        DialogFactory.createSimpleOkErrorDialog(
                                mContext,
                                "There was a problem loading the article"
                        ).show();
                    }

                    @Override
                    public void onNext(List<RecommendArticle> articles) {
                        if(articles!=null){
                            mRequestView.onRequestSuccess(articles);
                        }
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
