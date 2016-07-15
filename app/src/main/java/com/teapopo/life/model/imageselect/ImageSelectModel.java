package com.teapopo.life.model.imageselect;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.teapopo.life.model.BaseModel;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;

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

    public void getPhoneImageData(){
        ((SupportActivity)mContext).getSupportLoaderManager().initLoader(LOADER_ALL, null, mLoaderCallback);
    }

    private List<Folder> getFolderList(){
        for(Image image:imageList){
            String path = image.path;
            if (!hasFolderGened) {
                File imageFile = new File(path);
                File folderFile = imageFile.getParentFile();
                Folder folder = new Folder();
                folder.name = folderFile.getName();
                folder.path = folderFile.getAbsolutePath();
                folder.cover = image;
                if (!folderList.contains(folder)) {
                    List<Image> imageList = new ArrayList<>();
                    imageList.add(image);
                    folder.images = imageList;
                    folderList.add(folder);
                } else {
                    Folder f = folderList.get(folderList.indexOf(folder));
                    f.images.add(image);
                }
            }
        }
        return folderList;
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
