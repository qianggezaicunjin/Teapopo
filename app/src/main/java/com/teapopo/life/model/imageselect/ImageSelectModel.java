package com.teapopo.life.model.imageselect;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.google.gson.JsonObject;
import com.teapopo.life.model.BaseModel;
import com.teapopo.life.model.event.UploadImageEvent;
import com.teapopo.life.util.BitmapUtils;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.util.DataUtils;
import com.teapopo.life.util.rx.RxResultHelper;
import com.teapopo.life.util.rx.RxSubscriber;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/7/14.
 */
public class ImageSelectModel extends BaseModel {

    private boolean hasFolderGened = false;
    private static final int LOADER_ALL = 0;
    private static final int LOADER_CATEGORY = 1;
    private List<Folder> folderList = new ArrayList<>();

    private List<Image> imageList = new ArrayList<>();

    public ImageSelectModel(Context context) {
        super(context);
    }


    /**
     * 上传多张图片
     * @param imagePaths
     */
    public void upLoadMutiImage(List<String> imagePaths) {
        Timber.d("上传图片");
        Observable.from(imagePaths)
                .observeOn(Schedulers.io())
                .flatMap(new Func1<String, Observable<JsonObject>>() {
                    @Override
                    public Observable<JsonObject> call(String s) {
                        Timber.d("开始压缩图片");
                        String base64 = DataUtils.imgToBase64(BitmapUtils.comp(s));
                        Response<JsonObject> response = null;
                        try {
                            Timber.d("开始上传图片");
                            response = mDataManager.uploadImage("0",base64).execute();
                            Timber.d("上传图片返回的结果:%s",response.body().toString());
                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                        return Observable.just(response.body());
                    }
                })
                .compose(RxResultHelper.<JsonObject>handleResult())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<JsonObject>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        ModelAction modelAction = new ModelAction();
                        modelAction.action = Action.ImageSelectModel_UploadMutiImage;
                        mRequestView.onRequestSuccess(modelAction);
                    }

                    @Override
                    public void _onNext(JsonObject jsonObject) {
                        ModelAction modelAction = new ModelAction();
                        modelAction.action = Action.ImageSelectModel_UploadImage;
                        modelAction.t = jsonObject.get("id").getAsString();
                        mRequestView.onRequestSuccess(modelAction);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });


    }
    /**
     * 上传图片
     * @param imagePaths 图片的路径
     */
    public void upLoadImage(final String imagePaths) {
        Timber.d("上传图片");
        Observable.just(imagePaths)
                .observeOn(Schedulers.io())
                .flatMap(new Func1<String, Observable<JsonObject>>() {
                    @Override
                    public Observable<JsonObject> call(String s) {
                        Timber.d("开始压缩图片");
                        String base64 = DataUtils.imgToBase64(BitmapUtils.comp(s));
                        Response<JsonObject> response = null;
                        try {
                            Timber.d("开始上传图片");
                             response = mDataManager.uploadImage("0",base64).execute();
                            Timber.d("上传图片返回的结果:%s",response.body().toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return Observable.just(response.body());
                    }
                })
                .compose(RxResultHelper.<JsonObject>handleResult())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<JsonObject>() {
                    @Override
                    public void _onNext(JsonObject jsonObject) {
                        ModelAction modelAction = new ModelAction();
                        modelAction.action = Action.ImageSelectModel_UploadImage;
                        UploadImageEvent uploadImage = new UploadImageEvent();
                        uploadImage.imageIdFromServer = jsonObject.get("id").getAsString();
                        uploadImage.iamgePath = imagePaths;
                        uploadImage.isUploadSuccess = true;
                        modelAction.t = uploadImage;
                        mRequestView.onRequestSuccess(modelAction);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });


    }
    public void getPhoneImageData(){
        ((SupportActivity)mContext).getSupportLoaderManager().initLoader(LOADER_ALL, null, mLoaderCallback);
    }

    /**
     *
     * @param currentFolderName 当前的文件夹名称
     */
    public void getFolderList(final String currentFolderName){
        Observable<Image> observable = Observable.from(imageList);
        observable
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Folder folder = new Folder();
                        folder.name = "全部照片";
                        folder.path = "all";
                        folder.coverPath = imageList.get(0).path;
                        folder.images = (ArrayList<Image>) imageList;
                        if(currentFolderName.equals(folder.name)){
                            Timber.d("当前选中的文件夹为:%s",currentFolderName);
                            folder.isSelected = true;
                        }
                        if(!folderList.contains(folder)){
                            folderList.add(folder);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<Image, Observable<Folder>>() {
                    @Override
                    public Observable<Folder> call(Image image) {
                        String path = image.path;
                        File imageFile = new File(path);
                        Timber.d("图片路径为:%s",imageFile.getAbsolutePath());
                        File folderFile = imageFile.getParentFile();
                        Folder folder = new Folder();
                        folder.name = folderFile.getName();
                        folder.path = folderFile.getAbsolutePath();
                        folder.coverPath = image.path;
                        if(currentFolderName.equals(folder.name)){
                            Timber.d("当前选中的文件夹为:%s",currentFolderName);
                            folder.isSelected = true;
                        }
                        if (!folderList.contains(folder)) {
                            List<Image> imageList = new ArrayList<>();
                            imageList.add(image);
                            folder.images = (ArrayList<Image>) imageList;
                        } else {
                            Folder f = folderList.get(folderList.indexOf(folder));
                            if(!f.images.contains(image)){
                                f.images.add(image);
                            }
                        }
                        return Observable.just(folder);
                    }
                })
                .filter(new Func1<Folder, Boolean>() {
                    @Override
                    public Boolean call(Folder folder) {
                        return !folderList.contains(folder);
                    }
                })
                .subscribe(new RxSubscriber<Folder>() {
                    @Override
                    public void onCompleted() {
                        ModelAction action = new ModelAction();
                        action.action = Action.ImageSelectModel_GetFolderList;
                        action.t = folderList;
                        mRequestView.onRequestSuccess(action);
                    }

                    @Override
                    public void _onNext(Folder folder) {
                        folderList.add(folder);
                    }

                    @Override
                    public void _onError(String s) {
                        mRequestView.onRequestErroInfo(s);
                    }
                });

    }
    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {

        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media._ID};

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            if (id == LOADER_ALL) {
                CursorLoader cursorLoader =
                        new CursorLoader(mContext,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                                null, null, IMAGE_PROJECTION[2] + " DESC");
                return cursorLoader;
            } else if (id == LOADER_CATEGORY) {
                CursorLoader cursorLoader = new CursorLoader(mContext,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        IMAGE_PROJECTION[0] + " like '%" + args.getString("path") + "%'", null, IMAGE_PROJECTION[2] + " DESC");
                return cursorLoader;
            }

            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (data != null) {
                int count = data.getCount();
                if (count > 0) {
                    List<Image> tempImageList = new ArrayList<>();
                    data.moveToFirst();
                    do {
                        String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                        String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                        long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                        Image image = new Image(path, name, dateTime);
                        tempImageList.add(image);
                    } while (data.moveToNext());
                    imageList.clear();
                    imageList.addAll(tempImageList);
                    ModelAction modelAction = new ModelAction();
                    modelAction.action = Action.ImageSelectModel_GetImageData;
                    modelAction.t = imageList;
                    mRequestView.onRequestSuccess(modelAction);
                    hasFolderGened = true;
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };
}
