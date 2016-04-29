package com.teapopo.life.view.fragment.Home;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.MyApplication;
import com.teapopo.life.R;
import com.teapopo.life.data.DataManager;
import com.teapopo.life.model.recommendarticle.ArticleAuthorInfo;
import com.teapopo.life.model.recommendarticle.ArticleImage;
import com.teapopo.life.model.recommendarticle.Recommend;
import com.teapopo.life.model.recommendarticle.RecommendArticle;
import com.teapopo.life.model.recommendarticle.RecommendData;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.util.DialogFactory;
import com.teapopo.life.view.adapter.recyclerview.RecommendArticleAdapter;
import com.teapopo.life.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class RecommendArticleFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshWidget;

    private int mPage=0;//当前的页码
    private int mPages;//总共有多少页
    private int mLastVisibleItem;//RecyclerView当前最后可见item的位置
    private boolean mIsLoading=false;//是否正在加载
    private RecommendArticleAdapter mAdapter;
    private DataManager mDataManager;
    private CompositeSubscription mSubscriptions;

    public static RecommendArticleFragment newInstance() {
        return new RecommendArticleFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();
        mDataManager = MyApplication.get(getActivity()).getComponent().dataManager();
        mAdapter = new RecommendArticleAdapter(getActivity(),new ArrayList<RecommendArticle>());

    }

    @Override
    public View getmContentView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void setUpView() {
        setupRecyclerView();
        loadContentsIfNetworkConnected();
    }


    private void loadContentsIfNetworkConnected() {
        if (DataUtils.isNetworkAvailable(getActivity())) {
            getContents();
        }
    }

    private void getContents() {
        Timber.d("当前的页码为:%d",mPage);
        mSubscriptions.add(mDataManager.getRecommendArticle(mPage+1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .map(new Func1<Recommend, List<RecommendArticle>>() {

                            @Override
                            public List<RecommendArticle> call(Recommend recommend) {
                               List<RecommendArticle> lists=recommend.data.recommendArticles;

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
                                boolean isMainThread= Looper.myLooper() == Looper.getMainLooper();
                                if(isMainThread){
                                    mSwipeRefreshWidget.setRefreshing(false);

                                }
                                //加载数据完毕
                                mIsLoading=false;
                            }

                            @Override
                            public void onError(Throwable e) {

                                Timber.e("There was a problem loading the article " + e);
                                DialogFactory.createSimpleOkErrorDialog(
                                        getActivity(),
                                        "There was a problem loading the article"
                                ).show();
                            }

                            @Override
                            public void onNext(List<RecommendArticle> articles) {

                                if(mAdapter==null){
                                    mAdapter = new RecommendArticleAdapter(getActivity(),articles);
                                }else {
                                    mAdapter.addItem(articles);
                                }

                            }
                        })
        );
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    private void setupRecyclerView() {
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        //设置SwipeRefreshLayout
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshWidget.post(new Runnable() {
            @Override
            public void run() {
              mSwipeRefreshWidget.setRefreshing(true);
            }
        });
        mSwipeRefreshWidget.setOnRefreshListener(this);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.blue);


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                 mLastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                //mLastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载，各位自由选择
                // dy>0 表示向下滑动
                if(mLastVisibleItem+1==mAdapter.getItemCount()){
                    if(!mIsLoading){
                        mSwipeRefreshWidget.setRefreshing(true);
                        loadContentsIfNetworkConnected();
                        mIsLoading=true;
                    }
                }
            }
        });


//        MaterialViewPagerHelper.registerRecyclerView(getActivity(),mRecyclerView,null);
    }

    @Override
    public void onRefresh() {
        Timber.d("触发onRefresh");
        loadContentsIfNetworkConnected();
    }
}
