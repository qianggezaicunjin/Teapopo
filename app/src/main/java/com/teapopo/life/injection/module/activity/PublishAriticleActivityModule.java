package com.teapopo.life.injection.module.activity;

import com.teapopo.life.R;
import com.teapopo.life.injection.scope.PerActivity;
import com.teapopo.life.model.imageselect.ImageConfig;
import com.teapopo.life.view.customView.PicassoLoader;


import dagger.Module;
import dagger.Provides;

/**
 * Created by louiszgm on 2016/6/12.
 */
@Module
public class PublishAriticleActivityModule {

    @Provides
    @PerActivity
    ImageConfig.Builder provideImageConfigBuilder(){
        ImageConfig.Builder build
                = new ImageConfig.Builder()
                // 如果在 4.4 以上，则修改状态栏颜色 （默认黑色）
//                .steepToolBarColor(getResources().getColor(R.color.blue))
                // 标题的背景颜色 （默认黑色）
//                .titleBgColor(getResources().getColor(R.color.blue))
                // 提交按钮字体的颜色  （默认白色）
//                .titleSubmitTextColor(getResources().getColor(R.color.white))
                // 标题颜色 （默认白色）
//                .titleTextColor(getResources().getColor(R.color.white))
                // 开启多选   （默认为多选）  (单选 为 singleSelect)
//                        .singleSelect()
                .crop()
                // 多选时的最大数量   （默认 9 张）
                .mutiSelectMaxSize(9)
                // 拍照后存放的图片路径（默认 /temp/picture）
                .filePath("/ImageSelector/Pictures")
                // 开启拍照功能 （默认开启）
                .showCamera();


        return build;
    }
}
