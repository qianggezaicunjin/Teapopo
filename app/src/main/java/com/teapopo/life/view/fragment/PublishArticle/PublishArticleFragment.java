package com.teapopo.life.view.fragment.PublishArticle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teapopo.life.databinding.FragmentPublisharticleBinding;
import com.teapopo.life.view.customView.Dynamicgrid.listener.UILPauseOnScrollListener;
import com.teapopo.life.view.customView.Dynamicgrid.loader.UILImageLoader;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.PauseOnScrollListener;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by louiszgm on 2016/6/8.
 */
public class PublishArticleFragment extends SwipeBackBaseFragment {

    private FragmentPublisharticleBinding mBinding;
    @Override
    public void onCreateBinding(Bundle savedInstanceState) {

    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentPublisharticleBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        setUpGallery();
    }

    private void setUpGallery() {
        GalleryFinal.init(getGalleryConfig());
    }

    /**
     * 获得图片选择器的设置
     * @return
     */
    private CoreConfig getGalleryConfig(){
        FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
        //设置ImagerLoader
        cn.finalteam.galleryfinal.ImageLoader imageLoader;
        PauseOnScrollListener pauseOnScrollListener = null;
        imageLoader = new UILImageLoader();
        pauseOnScrollListener = new UILPauseOnScrollListener(false, true);
        //设置多选的图片个数
        functionConfigBuilder.setMutiSelectMaxSize(10);
        //是否可以编辑
        functionConfigBuilder.setEnableEdit(true);
        //旋转
        functionConfigBuilder.setEnableRotate(true);
        //旋转覆盖原图
        functionConfigBuilder.setRotateReplaceSource(true);
        //裁剪
        functionConfigBuilder.setEnableCrop(true);
        //裁剪覆盖原图
        functionConfigBuilder.setCropReplaceSource(true);
        //显示照相机
        functionConfigBuilder.setEnableCamera(true);
        //启动预览
        functionConfigBuilder.setEnablePreview(true);

        CoreConfig coreConfig = new CoreConfig.Builder(_mActivity, imageLoader, ThemeConfig.DEFAULT)
                .setFunctionConfig(functionConfigBuilder.build())
                .setPauseOnScrollListener(pauseOnScrollListener)
                .setNoAnimcation(true)
                .build();
        return coreConfig;
    }
}
