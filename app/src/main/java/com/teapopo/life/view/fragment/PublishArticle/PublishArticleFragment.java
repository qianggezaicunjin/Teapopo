package com.teapopo.life.view.fragment.PublishArticle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.R;
import com.teapopo.life.data.rx.RxBus;
import com.teapopo.life.databinding.FragmentPublisharticleBinding;
import com.teapopo.life.injection.component.fragment.PublishArticleFragmentComponent;
import com.teapopo.life.injection.module.fragment.PublishArticleFragmentModule;
import com.teapopo.life.util.RxUtils;
import com.teapopo.life.view.activity.PublishArticleActivity;
import com.teapopo.life.view.customView.Dynamicgrid.listener.UILPauseOnScrollListener;
import com.teapopo.life.view.customView.Dynamicgrid.loader.UILImageLoader;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.publisharticle.PublishArticleViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.PauseOnScrollListener;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import me.gujun.android.taggroup.TagGroup;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by louiszgm on 2016/6/8.
 */
public class PublishArticleFragment extends SwipeBackBaseFragment {

    private FragmentPublisharticleBinding mBinding;
    private PublishArticleFragmentComponent mComponent;
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private List<PhotoInfo> photoInfoList = new ArrayList<>();

    @Inject
    PublishArticleViewModel mViewModel;
    @Inject
    RxBus mRxBus;
    public static PublishArticleFragment newInstance(){
        return new PublishArticleFragment();
    }
    @Override
    public void onCreateBinding() {
        mBinding = FragmentPublisharticleBinding.inflate(LayoutInflater.from(_mActivity));
        mComponent = ((PublishArticleActivity)_mActivity).getComponent().publishArticleFragmentComponent(new PublishArticleFragmentModule(mBinding));
        mComponent.inject(this);
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        //设置图片选择器
        setUpGallery();
        //处理在图片选择器中选择的图片
        //设置热门标签
        setUpHotTags();
    }



    private void setUpHotTags() {
        TagGroup tagGroup = mBinding.hottagGroup;
        tagGroup.setTags(new String[]{"评测"});
    }
    //设置第一张图片为封面图片
    private void setCoverImage(){

    }


    //初始化图片选择器
    private void setUpGallery() {

        FunctionConfig functionConfig = getUpFuntionConfig();
        ThemeConfig themeConfig = getThemeConfig();
        //设置ImagerLoader
        cn.finalteam.galleryfinal.ImageLoader imageLoader;
        PauseOnScrollListener pauseOnScrollListener = null;
        imageLoader = new UILImageLoader();
        pauseOnScrollListener = new UILPauseOnScrollListener(false, true);

        CoreConfig coreConfig = new CoreConfig.Builder(_mActivity.getApplicationContext(), imageLoader, themeConfig)
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(pauseOnScrollListener)
                .setNoAnimcation(false)
                .build();
        GalleryFinal.init(coreConfig);
    }

    //设置主题
    private ThemeConfig getThemeConfig() {
        ThemeConfig theme = new ThemeConfig.Builder()
                .setEditPhotoBgTexture(_mActivity.getResources().getDrawable(R.color.white))
                .setPreviewBg(_mActivity.getResources().getDrawable(R.color.white))
                .build();

        return theme;
    }

    //设置功能
    private FunctionConfig getUpFuntionConfig() {
        FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
        //设置多选的图片个数
        functionConfigBuilder.setMutiSelectMaxSize(10);
        //是否可以编辑
        functionConfigBuilder.setEnableEdit(true);
        //旋转
        functionConfigBuilder.setEnableRotate(true);
        //旋转覆盖原图
        functionConfigBuilder.setRotateReplaceSource(false);
        //裁剪
        functionConfigBuilder.setEnableCrop(true);
        //裁剪覆盖原图
        functionConfigBuilder.setCropReplaceSource(false);
        functionConfigBuilder.setCropSquare(true);
        //显示照相机
        functionConfigBuilder.setEnableCamera(true);
        //启动预览
        functionConfigBuilder.setEnablePreview(true);

        functionConfigBuilder.setSelected(photoInfoList);
        return functionConfigBuilder.build();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.add(mViewModel.mModel.mCompositeSubscription);
        RxUtils.unsubscribeIfNotNull(mCompositeSubscription);
        mComponent = null;
    }
}
