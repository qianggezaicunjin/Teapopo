package com.teapopo.life.view.fragment.PublishArticle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Toast;

import com.teapopo.life.R;
import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.databinding.FragmentSelectImageBinding;
import com.teapopo.life.model.event.OpenCameraEvent;
import com.teapopo.life.model.imageselect.Image;
import com.teapopo.life.model.imageselect.ImageConfig;
import com.teapopo.life.model.imageselect.ImageSelectModel;
import com.teapopo.life.util.RxUtils;
import com.teapopo.life.util.Utils;
import com.teapopo.life.view.activity.PublishArticleActivity;
import com.teapopo.life.view.adapter.gridview.ImageAdapter;
import com.teapopo.life.view.adapter.recyclerview.SelectedImageAdapter;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.publisharticle.ImageSelectViewModel;

import com.yancy.imageselector.utils.FileUtils;

import java.io.File;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * ImageSelectorFragment
 * Created by Yancy on 2015/12/2.
 */
public class ImageSelectorFragment extends SwipeBackBaseFragment {
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    public static final int REQUEST_CAMERA = 100;
    public static final int IMAGE_CROP_CODE = 200;
    private FragmentSelectImageBinding mBinding;
    private ImageAdapter imageAdapter;


    private File tempFile;

    private Context context;

    private ImageConfig imageConfig;
    ImageSelectViewModel mViewModel;
    @Inject
    RxBus mRxBus;
    public static ImageSelectorFragment newInstance(ImageConfig.Builder builder){
        ImageSelectorFragment fragment = new ImageSelectorFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("builder",builder);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreateBinding() {
        ((PublishArticleActivity)_mActivity).getFragmentComponent().inject(this);
        ImageConfig.Builder builder = (ImageConfig.Builder) getArguments().getSerializable("builder");
        imageConfig = builder.build();

        ObserverRxBusEvent();
    }

    private void ObserverRxBusEvent() {
        Observable<OpenCameraEvent>  observable = mRxBus.toObserverable(OpenCameraEvent.class);
        compositeSubscription.add(observable.doOnNext(new Action1<OpenCameraEvent>() {
            @Override
            public void call(OpenCameraEvent openCameraEvent) {
                showCameraAction();
            }
        }).subscribe());
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentSelectImageBinding.inflate(inflater);
        mViewModel = new ImageSelectViewModel(new ImageSelectModel(_mActivity));
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        context = getActivity();
        setUpToolBar();
        setUpGridImage(imageConfig);
        setUpSelectedImage();
//        setUpFolder(imageConfig);
//        time_text.setVisibility(View.GONE);
    }

    private void setUpToolBar() {
        mBinding.toolbarImageSelector.inflateMenu(R.menu.menu_edit);
        mBinding.toolbarImageSelector.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mViewModel.getFolderList();
                return true;
            }
        });
    }

    private void setUpSelectedImage() {
        SelectedImageAdapter adapter = new SelectedImageAdapter(_mActivity,mViewModel.data);
        mBinding.rvSelectedImage.setAdapter(adapter);
        mBinding.rvSelectedImage.setOrientation(RecyclerView.HORIZONTAL);
    }

//    private void setUpFolder(ImageConfig imageConfig) {
//        folderAdapter = new FolderAdapter(context, imageConfig);
//        category_button.setText(com.yancy.imageselector.R.string.all_folder);
//        category_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (folderPopupWindow == null) {
//                    createPopupFolderList(gridWidth, gridHeight);
//                }
//
//                if (folderPopupWindow.isShowing()) {
//                    folderPopupWindow.dismiss();
//                } else {
//                    folderPopupWindow.show();
//                    int index = folderAdapter.getSelectIndex();
//                    index = index == 0 ? index : index - 1;
//                    folderPopupWindow.getListView().setSelection(index);
//                }
//            }
//        });
//    }

    private void setUpGridImage(ImageConfig imageConfig) {
        //设置选中的图片
        imageAdapter = new ImageAdapter(context,imageConfig,mViewModel.imageList);
        mBinding.gridImage.setAdapter(imageAdapter);
        attachGridImageListener();
        mViewModel.getImageData();
    }



    private void attachGridImageListener() {
        mBinding.gridImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onGlobalLayout() {

                final int width = mBinding.gridImage.getWidth();
                final int height = mBinding.gridImage.getHeight();

//                gridWidth = width;
//                gridHeight = height;

                final int desireSize = getResources().getDimensionPixelOffset(com.yancy.imageselector.R.dimen.image_size);
                final int numCount = width / desireSize;
                final int columnSpace = getResources().getDimensionPixelOffset(com.yancy.imageselector.R.dimen.space_size);
                int columnWidth = (width - columnSpace * (numCount - 1)) / numCount;
                imageAdapter.setItemSize(columnWidth);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mBinding.gridImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mBinding.gridImage.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
//        grid_image.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if (scrollState == SCROLL_STATE_IDLE) {
//                    time_text.setVisibility(View.GONE);
//                } else if (scrollState == SCROLL_STATE_FLING) {
//                    time_text.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (time_text.getVisibility() == View.VISIBLE) {
//                    int index = firstVisibleItem + 1 == view.getAdapter().getCount() ? view.getAdapter().getCount() - 1 : firstVisibleItem + 1;
//                    Image image = (Image) view.getAdapter().getItem(index);
//                    if (image != null) {
//                        time_text.setText(TimeUtils.formatPhotoDate(image.path));
//                    }
//                }
//            }
//        });



        mBinding.gridImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mViewModel.selectImageFromGrid(position,imageConfig);
            }
        });


    }

//    /**
//     * 创建弹出的ListView
//     */
//    private void createPopupFolderList(int width, int height) {
//        folderPopupWindow = new ListPopupWindow(getActivity());
//        folderPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        folderPopupWindow.setAdapter(folderAdapter);
//        folderPopupWindow.setContentWidth(width);
//        folderPopupWindow.setWidth(width);
//        folderPopupWindow.setHeight(height * 5 / 8);
//        folderPopupWindow.setAnchorView(popupAnchorView);
//        folderPopupWindow.setModal(true);
//        folderPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                folderAdapter.setSelectIndex(i);
//
//                final int index = i;
//                final AdapterView v = adapterView;
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        folderPopupWindow.dismiss();
//
//                        if (index == 0) {
//                            category_button.setText(com.yancy.imageselector.R.string.all_folder);
//                            if (imageConfig.isShowCamera()) {
//                                imageAdapter.setShowCamera(true);
//                            } else {
//                                imageAdapter.setShowCamera(false);
//                            }
//                        } else {
//                            Folder folder = (Folder) v.getAdapter().getItem(index);
//                            if (null != folder) {
//                                imageList.clear();
//                                imageList.addAll(folder.images);
//                                imageAdapter.notifyDataSetChanged();
//
//                                category_button.setText(folder.name);
//                                // 设定默认选择
//                                if (resultList != null && resultList.size() > 0) {
//                                    imageAdapter.setDefaultSelected(resultList);
//                                }
//                            }
//                            imageAdapter.setShowCamera(false);
//                        }
//
//                        // 滑动到最初始位置
//                        grid_image.smoothScrollToPosition(0);
//                    }
//                }, 100);
//
//            }
//        });
//    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mViewModel.handleTakePhotoDone(requestCode,resultCode,tempFile);
        super.onActivityResult(requestCode, resultCode, data);
    }

//    private void crop(String imagePath, int aspectX, int aspectY, int outputX, int outputY) {
//        File file;
//        if (Utils.existSDCard()) {
//            file = new File(Environment.getExternalStorageDirectory() + imageConfig.getFilePath(), Utils.getImageName());
//        } else {
//            file = new File(_mActivity.getCacheDir(), Utils.getImageName());
//        }
//
//
//        cropImagePath = file.getAbsolutePath();
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(Uri.fromFile(new File(imagePath)), "image/*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", aspectX);
//        intent.putExtra("aspectY", aspectY);
//        intent.putExtra("outputX", outputX);
//        intent.putExtra("outputY", outputY);
//        intent.putExtra("return-data", false);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
//        startActivityForResult(intent, IMAGE_CROP_CODE);
//    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeSubscription.add(imageAdapter.compositeSubscription);
        RxUtils.unsubscribeIfNotNull(compositeSubscription);
    }
}