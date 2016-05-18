package com.teapopo.life.model.category;

import android.content.Context;

import com.teapopo.life.model.BaseModel;

import java.sql.Time;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/7.
 */
public class CategoryModel extends BaseModel {

    public CategoryModel(Context context) {
        super(context);
    }

    public void getContents(){
        Observable<CategoryList> observable = mDataManager.getCategorys();
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .subscribe(new Subscriber<CategoryList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.toString());
                    }

                    @Override
                    public void onNext(CategoryList categories) {
                        Timber.d("返回的Category的个数为:%d",categories.data.categoryList.size());
                        mRequestView.onRequestSuccess(categories.data.categoryList);
                    }
                });
    }
}
