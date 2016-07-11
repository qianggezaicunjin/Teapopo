package com.teapopo.life.model.article.publisharticle;

import android.content.Context;

import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.util.BitmapUtils;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;
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
                .filter(new Func1<JsonObject, Boolean>() {
                    @Override
                    public Boolean call(JsonObject jsonObject) {
                        if (imagePaths.size()>0){
                            //上传图片
                            //发布文章成功,取得文章ID ，将文章图片上传至服务器
                            String articleId = jsonObject.get("id").getAsString();
                            Timber.d("上传图片");
                            upLoadImage(articleId,imagePaths);
                            return false;
                        }
                        return true;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<JsonObject>() {
                    @Override
                    public void _onNext(JsonObject jsonObject) {
                            //没有图片需要上传，直接通知发布文章成功
                            ModelAction  action = new ModelAction();
                            action.action = Action.PublishArticleModel_PublishWithoutImage;
                             mRequestView.onRequestSuccess(action);
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
        Timber.d("上传图片的个数:%d",imagePaths.size());
        Observable.from(imagePaths)
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Timber.d("开始压缩图片");
                        String base64 = DataUtils.imgToBase64(BitmapUtils.comp(s));
                        try {
                            Timber.d("开始上传图片");
                            Response<JsonObject>  response = mDataManager.uploadImage(articleId,base64).execute();
                            Timber.d("上传图片返回的结果:%s",response.body().toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<String>() {
                    @Override
                    public void _onNext(String s) {

                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        Timber.d("上传图片成功");
                        ModelAction modelAction = new ModelAction();
                        modelAction.action = Action.PublishArticleModel_PublishWithImage;
                        mRequestView.onRequestSuccess(modelAction);
                    }
                });


    }

}
