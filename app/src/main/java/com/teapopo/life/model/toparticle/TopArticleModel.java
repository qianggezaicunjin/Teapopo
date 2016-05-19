package com.teapopo.life.model.toparticle;

import android.content.Context;


import com.teapopo.life.MyApplication;
import com.teapopo.life.data.DataManager;
import com.teapopo.life.model.BaseEntity;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.event.AddHeaderEvent;
import com.teapopo.life.model.event.DataEvent;
import com.teapopo.life.view.customView.RequestView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by louiszgm-pc on 2016/5/5.
 */
public class TopArticleModel extends BaseModel{


    public TopArticleModel(Context context){
        super(context);
    }

    public void getContens(String classify){
        Observable<List<TopArticle>> observable = mDataManager.getTopArticle(classify);
        observable.subscribeOn(Schedulers.io())
                    .observeOn(mDataManager.getScheduler())
                    .subscribe(new Subscriber<List<TopArticle>>() {
                        @Override
                        public void onCompleted() {
                            mRxBus.post(new AddHeaderEvent());
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e(e.toString());
                        }

                        @Override
                        public void onNext(List<TopArticle> topArticles) {
                            mRequestView.onRequestSuccess(topArticles);
                        }
                    });
    }
}
