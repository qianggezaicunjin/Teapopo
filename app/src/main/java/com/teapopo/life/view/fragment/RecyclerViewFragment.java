package com.teapopo.life.view.fragment;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.MyApplication;
import com.teapopo.life.R;
import com.teapopo.life.data.DataManager;
import com.teapopo.life.model.recommendarticle.Recommend;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.util.DialogFactory;
import com.teapopo.life.view.adapter.recyclerview.RecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class RecyclerViewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshWidget;

    private View mContentView;
    private int mPage=0;//当前的页码
    private int mPages;//总共有多少页
    private int mLastVisibleItem;//RecyclerView当前最后可见item的位置
    private boolean mIsLoading=false;//是否正在加载
    private RecyclerViewAdapter mAdapter;
    private DataManager mDataManager;
    private CompositeSubscription mSubscriptions;

    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();
        mDataManager = MyApplication.get(getActivity()).getComponent().dataManager();
        mAdapter = new RecyclerViewAdapter(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mContentView==null){
            mContentView = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        }
        ViewGroup parent = (ViewGroup) container.getParent();
        if(parent != null){
            parent.removeView(mContentView);
        }
        ButterKnife.bind(this, mContentView);
        setupRecyclerView();
        loadContentsIfNetworkConnected();
        return mContentView;
    }




    private void loadContentsIfNetworkConnected() {
        if (DataUtils.isNetworkAvailable(getActivity())) {
            getContents();
        }
    }

    private void getContents() {
        Timber.d("当前的页码为:%d",mPage);
        mSubscriptions.add(mDataManager.getRecommendArticle(mPage+1)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(mDataManager.getScheduler())
                        .subscribe(new Subscriber<Recommend>() {
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
                            public void onNext(Recommend recommend) {

                                mAdapter.addItem(recommend.data);
                                mPage= recommend.page;
                                mPages=recommend.pages;

                            }
                        })
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
        ButterKnife.unbind(this);
    }

    private void setupRecyclerView() {
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter.setItems(null);
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
