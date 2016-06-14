package com.teapopo.life.model.article.publisharticle;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.event.TellUpLoadDoneOrNot;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.util.BitmapUtils;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;
import rx.Observable;
import rx.functions.Action0;
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
     * @param photoInfos  发布的图片
     */
    public void publishArticle(String title, String content, List<PhotoInfo> photoInfos,String tags){
        String coverImage = "";
        String[] imagesArray = new String[]{};
        List<String> imagePaths = new ArrayList<>();
        if(photoInfos!=null){
            //第一张图片就是该篇文章的封面
             coverImage = photoInfos.get(0).getImageName();
            //图片名字
            List<String> images = new ArrayList<>();
            for (PhotoInfo photoInfo:photoInfos){
                String imageName = photoInfo.getImageName();
                String path = photoInfo.getPhotoPath();
                images.add(imageName);
                imagePaths.add(path);
            }
            //转换成String数组
            images.toArray(imagesArray);
        }
        Observable<JsonObject> observable = mDataManager.publishArticle(title,content,coverImage,imagesArray ,tags);
        subcribePublish(observable,imagePaths);
    }

    private void subcribePublish(Observable<JsonObject> observable, final List<String> imagePaths) {
        mCompositeSubscription.add(observable.subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .subscribe(new RxSubscriber<JsonObject>() {
                    @Override
                    public void _onNext(JsonObject jsonObject) {
                        //发布文章成功,取得文章ID ，将文章图片上传至服务器
                        String articleId = jsonObject.get("id").getAsString();
                        if(imagePaths.size() == 0){
                            //没有图片需要上传，直接通知发布文章成功
                            ModelAction  action = new ModelAction();
                            action.action = Action.PublishArticleModel_PublishWithoutImage;
                             mRequestView.onRequestSuccess(action);
                        }else {
                            //上传图片
                            Timber.d("上传图片");
                            upLoadImage(articleId,imagePaths);
                        }

                    }

                    @Override
                    public void _onError(String s) {
                        Timber.e(s);
                    }
                }));
    }

    /**
     * 上传图片
     * @param articleId 文章id
     * @param imagePaths 图片的路径
     */
    private void upLoadImage(final String articleId, List<String> imagePaths) {
        for (final String path:imagePaths){
            Observable.just(path)
                    .observeOn(Schedulers.io())
                    .flatMap(new Func1<String, Observable<String>>() {
                        @Override
                        public Observable<String> call(String s) {
                            //压缩图片，并转换成base64编码
                            String base64 = DataUtils.imgToBase64(BitmapUtils.comp(path));
                            return Observable.just(base64);
                        }
                    })
                    .subscribe(new RxSubscriber<String>() {
                        @Override
                        public void _onNext(String s) {
                            Observable<JsonObject> observable = mDataManager.uploadImage(articleId,s);
                            subscribeUpload(observable,path);
                        }

                        @Override
                        public void _onError(String s) {
                            Timber.e(s);
                        }
                    });
        }

    }

    /**
     * 观察文件上传的进度，发出通知
     * @param observable
     * @param path
     */
    private void subscribeUpload(Observable<JsonObject> observable, final String path) {
        mCompositeSubscription.add(observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        TellUpLoadDoneOrNot tellUpLoadDoneOrNot = new TellUpLoadDoneOrNot();
                        tellUpLoadDoneOrNot.isDone = false;
                        tellUpLoadDoneOrNot.id = path;
                        mRxBus.post(tellUpLoadDoneOrNot);
                    }
                })
                .compose(RxResultHelper.<JsonObject>handleResult())
                .subscribe(new RxSubscriber<JsonObject>() {
                    @Override
                    public void _onNext(JsonObject jsonObject) {
                        TellUpLoadDoneOrNot tellUpLoadDoneOrNot = new TellUpLoadDoneOrNot();
                        tellUpLoadDoneOrNot.isDone = true;
                        tellUpLoadDoneOrNot.id = path;
                        mRxBus.post(tellUpLoadDoneOrNot);
                    }

                    @Override
                    public void _onError(String s) {
                        Timber.e(s);
                    }
                }));

    }

}
