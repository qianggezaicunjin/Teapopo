package com.teapopo.life.model.article.publisharticle;

import android.content.Context;

import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
    public void publishArticle(String title, String content, List<PhotoInfo> photoInfos){
        //第一张图片就是该篇文章的封面
        String coverImage = photoInfos.get(0).getImageName();
        //图片的路径
        final List<String> imagePaths = new ArrayList<>();
        //图片名字
       List<String> images = new ArrayList<>();
        for (PhotoInfo photoInfo:photoInfos){
            String imageName = photoInfo.getImageName();
            String path = photoInfo.getPhotoPath();
            images.add(imageName);
            imagePaths.add(path);
        }

        Observable<JsonObject> observable = mDataManager.publishArticle(title,content,coverImage, (String[]) images.toArray());
        observable.subscribeOn(Schedulers.io())
                .observeOn(mDataManager.getScheduler())
                .compose(RxResultHelper.<JsonObject>handleResult())
                .subscribe(new RxSubscriber<JsonObject>() {
                    @Override
                    public void _onNext(JsonObject jsonObject) {
                        //发布文章成功,取得文章ID ，将文章图片上传至服务器
                        String articleId = jsonObject.get("id").getAsString();
                        if(imagePaths.size() == 0){
                            //没有图片需要上传，直接通知发布文章成功
                        }else {
                            //上传图片
                            upLoadImage(articleId,imagePaths);
                        }

                    }

                    @Override
                    public void _onError(String s) {

                    }
                });
    }

    /**
     * 上传图片
     * @param articleId 文章id
     * @param imagePaths 图片的路径
     */
    private void upLoadImage(String articleId, List<String> imagePaths) {
        for (String path:imagePaths){
            String base64 = DataUtils.imgToBase64(path);
            Observable<JsonObject> observable = mDataManager.uploadImage(articleId,base64);
            observable.subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            //发出通知正在加载
//                            mRxBus.post();
                        }
                    })
                    .compose(RxResultHelper.<JsonObject>handleResult())
                    .subscribe(new RxSubscriber<JsonObject>() {
                        @Override
                        public void _onNext(JsonObject jsonObject) {

                        }

                        @Override
                        public void _onError(String s) {
                            mRequestView.onRequestErroInfo(s);
                        }
                    });
        }

    }
}
