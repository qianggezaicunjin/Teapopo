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
import com.teapopo.life.view.customView.PicassoLoader;
import com.teapopo.life.view.fragment.SwipeBackBaseFragment;
import com.teapopo.life.viewModel.publisharticle.PublishArticleViewModel;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


import me.gujun.android.taggroup.TagGroup;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/8.
 */
public class PublishArticleFragment extends SwipeBackBaseFragment {

    public static final int REQUEST_CODE = 1000;//图片选择器的请求码
    private ArrayList<String> path = new ArrayList<>();
    private FragmentPublisharticleBinding mBinding;
    private PublishArticleFragmentComponent mComponent;
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

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
        mComponent = ((PublishArticleActivity)_mActivity).getComponent().publishArticleFragmentComponent(new PublishArticleFragmentModule(this));
        mComponent.inject(this);
    }

    @Override
    public View getContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void setUpView() {
        //设置热门标签
        setUpHotTags();
        //
        attachOnClickListener();
    }


    private void attachOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.linearlayout_addpublishphoto:
                        openGallery();
                        break;
                    case R.id.btn_publishArticle:
//                        publishArticle();
                        break;
                }
            }
        };
        mBinding.linearlayoutAddpublishphoto.setOnClickListener(listener);
        mBinding.btnPublishArticle.setOnClickListener(listener);
    }

    //发布文章
    private void publishArticle() {
        mBinding.btnPublishArticle.setProgress(50);
        //获取文章标题
        String title = mBinding.etPublishtitle.getText().toString();
        //获取文章内容
        String content = mBinding.etPublishcontent.getText().toString();
        //获取要上传的图片
        //获取该文章的标签
        String[] tags = mBinding.tagGroup.getTags();
//        mViewModel.publishArticle(title,content,mPhotoInfoList,tags);
    }
    //打开图片选择器
    private void openGallery() {
        ImageSelector.open(_mActivity,setUpGalleryConfig());
    }

    private ImageConfig setUpGalleryConfig() {
        ImageConfig imageConfig
                = new ImageConfig.Builder(
                // GlideLoader 可用自己用的缓存库
                new PicassoLoader())
                // 如果在 4.4 以上，则修改状态栏颜色 （默认黑色）
                .steepToolBarColor(getResources().getColor(R.color.blue))
                // 标题的背景颜色 （默认黑色）
                .titleBgColor(getResources().getColor(R.color.blue))
                // 提交按钮字体的颜色  （默认白色）
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                // 标题颜色 （默认白色）
                .titleTextColor(getResources().getColor(R.color.white))
                // 开启多选   （默认为多选）  (单选 为 singleSelect)
//                        .singleSelect()
                .crop()
                // 多选时的最大数量   （默认 9 张）
                .mutiSelectMaxSize(9)
                // 已选择的图片路径
                .pathList(path)
                // 拍照后存放的图片路径（默认 /temp/picture）
                .filePath("/ImageSelector/Pictures")
                // 开启拍照功能 （默认开启）
                .showCamera()
                .requestCode(REQUEST_CODE)
                .build();

        return imageConfig;
    }

    //发布成功时，通知视图更新
    public void refreshPublishDone(){
        mBinding.btnPublishArticle.setProgress(100);
    }


    private void setUpHotTags() {
        TagGroup tagGroup = mBinding.hottagGroup;
        tagGroup.setTags(new String[]{"评测"});
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxUtils.unsubscribeIfNotNull(mCompositeSubscription);
    }
}
