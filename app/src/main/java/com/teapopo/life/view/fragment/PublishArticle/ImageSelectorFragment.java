package com.teapopo.life.view.fragment.PublishArticle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

import android.support.v7.widget.ListPopupWindow;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentSelectImageBinding;
import com.teapopo.life.model.imageselect.Folder;
import com.teapopo.life.model.imageselect.Image;
import com.teapopo.life.model.imageselect.ImageConfig;
import com.teapopo.life.view.adapter.FolderAdapter;
import com.teapopo.life.view.adapter.gridview.ImageAdapter;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.yancy.imageselector.ImageSelector;


import com.yancy.imageselector.utils.DeviceUtils;
import com.yancy.imageselector.utils.FileUtils;
import com.yancy.imageselector.utils.TimeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ImageSelectorFragment
 * Created by Yancy on 2015/12/2.
 */
public class ImageSelectorFragment extends SwipeBackBaseFragment {


    private static final int REQUEST_CAMERA = 100;

    private ArrayList<String> resultList = new ArrayList<>();


    private List<Image> imageList = new ArrayList<>();

    private Callback callback;

    private FragmentSelectImageBinding mBinding;
    private ImageAdapter imageAdapter;
    private FolderAdapter folderAdapter;

    private ListPopupWindow folderPopupWindow;

    private TextView time_text;
    private TextView category_button;
    private View popupAnchorView;
    private GridView grid_image;


    private int gridWidth, gridHeight;


    private File tempFile;

    private Context context;

    private ImageConfig imageConfig;
    private int mScreenWidth;
    private int mScreenHeight;


    public static ImageSelectorFragment newInstance(ImageConfig.Builder builder){
        ImageSelectorFragment fragment = new ImageSelectorFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("builder",builder);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreateBinding() {
        ImageConfig.Builder builder = (ImageConfig.Builder) getArguments().getSerializable("builder");
        imageConfig = builder.build();
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentSelectImageBinding.inflate(inflater);
        DisplayMetrics dm = DeviceUtils.getScreenPix(getActivity());
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        context = getActivity();
        setUpGridImage(imageConfig);
        setUpFolder(imageConfig);
        time_text.setVisibility(View.GONE);
    }

    private void setUpFolder(ImageConfig imageConfig) {
        folderAdapter = new FolderAdapter(context, imageConfig);
        category_button.setText(com.yancy.imageselector.R.string.all_folder);
        category_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (folderPopupWindow == null) {
                    createPopupFolderList(gridWidth, gridHeight);
                }

                if (folderPopupWindow.isShowing()) {
                    folderPopupWindow.dismiss();
                } else {
                    folderPopupWindow.show();
                    int index = folderAdapter.getSelectIndex();
                    index = index == 0 ? index : index - 1;
                    folderPopupWindow.getListView().setSelection(index);
                }
            }
        });
    }

    private void setUpGridImage(ImageConfig imageConfig) {
        //设置选中的图片
        if (resultList != null && resultList.size() > 0) {
            imageAdapter.setDefaultSelected(resultList);
        }
        imageAdapter = new ImageAdapter(context, imageList, imageConfig,mScreenWidth);
        imageAdapter.setShowCamera(imageConfig.isShowCamera());
        imageAdapter.setShowSelectIndicator(imageConfig.isMutiSelect());
        mBinding.gridImage.setAdapter(imageAdapter);
        attachGridImageListener();
    }



    private void attachGridImageListener() {

        resultList = imageConfig.getPathList();
        grid_image.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onGlobalLayout() {

                final int width = grid_image.getWidth();
                final int height = grid_image.getHeight();

                gridWidth = width;
                gridHeight = height;

                final int desireSize = getResources().getDimensionPixelOffset(com.yancy.imageselector.R.dimen.image_size);
                final int numCount = width / desireSize;
                final int columnSpace = getResources().getDimensionPixelOffset(com.yancy.imageselector.R.dimen.space_size);
                int columnWidth = (width - columnSpace * (numCount - 1)) / numCount;
                imageAdapter.setItemSize(columnWidth);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    grid_image.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    grid_image.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
        grid_image.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    time_text.setVisibility(View.GONE);
                } else if (scrollState == SCROLL_STATE_FLING) {
                    time_text.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (time_text.getVisibility() == View.VISIBLE) {
                    int index = firstVisibleItem + 1 == view.getAdapter().getCount() ? view.getAdapter().getCount() - 1 : firstVisibleItem + 1;
                    Image image = (Image) view.getAdapter().getItem(index);
                    if (image != null) {
                        time_text.setText(TimeUtils.formatPhotoDate(image.path));
                    }
                }
            }
        });



        grid_image.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (imageAdapter.isShowCamera()) {
                    if (i == 0) {
                        showCameraAction();
                    } else {
                        Image image = (Image) adapterView.getAdapter().getItem(i);
                        selectImageFromGrid(image, imageConfig.isMutiSelect(),view);
                    }
                } else {
                    // 正常操作
                    Image image = (Image) adapterView.getAdapter().getItem(i);
                    selectImageFromGrid(image, imageConfig.isMutiSelect(),view);
                }
            }
        });


    }

    /**
     * 创建弹出的ListView
     */
    private void createPopupFolderList(int width, int height) {
        folderPopupWindow = new ListPopupWindow(getActivity());
        folderPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        folderPopupWindow.setAdapter(folderAdapter);
        folderPopupWindow.setContentWidth(width);
        folderPopupWindow.setWidth(width);
        folderPopupWindow.setHeight(height * 5 / 8);
        folderPopupWindow.setAnchorView(popupAnchorView);
        folderPopupWindow.setModal(true);
        folderPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                folderAdapter.setSelectIndex(i);

                final int index = i;
                final AdapterView v = adapterView;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        folderPopupWindow.dismiss();

                        if (index == 0) {
                            category_button.setText(com.yancy.imageselector.R.string.all_folder);
                            if (imageConfig.isShowCamera()) {
                                imageAdapter.setShowCamera(true);
                            } else {
                                imageAdapter.setShowCamera(false);
                            }
                        } else {
                            Folder folder = (Folder) v.getAdapter().getItem(index);
                            if (null != folder) {
                                imageList.clear();
                                imageList.addAll(folder.images);
                                imageAdapter.notifyDataSetChanged();

                                category_button.setText(folder.name);
                                // 设定默认选择
                                if (resultList != null && resultList.size() > 0) {
                                    imageAdapter.setDefaultSelected(resultList);
                                }
                            }
                            imageAdapter.setShowCamera(false);
                        }

                        // 滑动到最初始位置
                        grid_image.smoothScrollToPosition(0);
                    }
                }, 100);

            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (folderPopupWindow != null) {
            if (folderPopupWindow.isShowing()) {
                folderPopupWindow.dismiss();
            }
        }
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 选择相机
     */
    private void showCameraAction() {
        // 跳转到系统照相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // 设置系统相机拍照后的输出路径
            // 创建临时文件
            tempFile = FileUtils.createTmpFile(getActivity(), imageConfig.getFilePath());
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        } else {
            Toast.makeText(context, com.yancy.imageselector.R.string.msg_no_camera, Toast.LENGTH_SHORT).show();
        }
    }

    private void selectImageFromGrid(Image image, boolean isMulti,View view) {
        if (image != null) {
            if (isMulti) {
                ImageAdapter.ViewHolder holder = (ImageAdapter.ViewHolder) view.getTag();
                if (resultList.contains(image.path)) {
                    resultList.remove(image.path);
                    holder.photo_check.setImageResource(com.yancy.imageselector.R.mipmap.imageselector_select_uncheck);
                    if (callback != null) {
                        callback.onImageUnselected(image.path);
                    }
                } else {
                    if (imageConfig.getMaxSize() == resultList.size()) {
                        Toast.makeText(context, com.yancy.imageselector.R.string.msg_amount_limit, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    holder.photo_check.setImageResource(com.yancy.imageselector.R.mipmap.imageselector_select_checked);
                    resultList.add(image.path);
                    if (callback != null) {
                        callback.onImageSelected(image.path);
                    }
                }
                imageAdapter.select(image);
            } else {
                if (callback != null) {
                    callback.onSingleImageSelected(image.path);
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                if (tempFile != null) {
                    if (callback != null) {
                        callback.onCameraShot(tempFile);
                    }
                }
            } else {
                if (tempFile != null && tempFile.exists()) {
                    tempFile.delete();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public interface Callback {
        void onSingleImageSelected(String path);

        void onImageSelected(String path);

        void onImageUnselected(String path);

        void onCameraShot(File imageFile);
    }

}