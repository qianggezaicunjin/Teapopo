package com.teapopo.life.model.article.publisharticle;

import android.content.Context;
import android.provider.ContactsContract;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.imageselect.Image;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.util.BitmapUtils;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/12.
 */
public class PublishArticleModel extends BaseModel {


    public PublishArticleModel(Context context) {
        super(context);
    }


    /**
     * 发布文章
     * @param title
     * @param content
     * @param images 图片List
     *
     */
    public void publishArticle(String title, String content, List<Image> images, String tags){
        String coverImage = images.get(0).imageId;
        Observable<JsonObject> observable = mDataManager.publishArticle(title,content,coverImage,tags,castListToJsonArrayString(images));
        observable.subscribeOn(Schedulers.io())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<JsonObject>() {
                    @Override
                    public void _onNext(JsonObject jsonObject) {
                        ModelAction modelAction = new ModelAction();
                        modelAction.action = Action.PublishArticleModel_PublishArticle;
                        mRequestView.onRequestSuccess(modelAction);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });
    }

    private String[] castListToJsonArrayString(List<Image> images) {
        String[] array = new String[images.size()];
        for(int i = 0;i<images.size();i++){
            array[i] = images.get(i).imageId;
        }
        Timber.d("上传的图片id的数组为:%s",array.toString());
        return array;
    }


}
