package com.teapopo.life.viewModel.articleinfo;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ViewDataBinding;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;

import com.teapopo.life.BR;
import com.teapopo.life.R;
import com.teapopo.life.databinding.FragmentArticleinfoBinding;
import com.teapopo.life.model.articleinfo.ArticleInfo;
import com.teapopo.life.model.articleinfo.ArticleInfoModel;
import com.teapopo.life.model.toparticle.TopArticle;
import com.teapopo.life.util.Constans.Action;
import com.teapopo.life.util.Constans.ModelAction;
import com.teapopo.life.view.adapter.viewpager.ArticleInfoImageAdapter;
import com.teapopo.life.view.adapter.viewpager.TopArticleAdapter;
import com.teapopo.life.view.customView.HackyViewPager;
import com.teapopo.life.view.customView.RequestView;

import timber.log.Timber;

/**
 * Created by louiszgm on 2016/6/1.
 */
public class ArticleInfoViewModel extends BaseObservable implements RequestView<ModelAction> {
    private Context mContext;
    private ArticleInfoModel mModel;
    public String articleId;
    private FragmentArticleinfoBinding mBinding;
    @Bindable
    public ArticleInfo articleInfo;
    public ArticleInfoViewModel(Context context, ArticleInfoModel model, ViewDataBinding binding){
        mBinding = (FragmentArticleinfoBinding) binding;
        mContext = context;
        mModel = model;
        mModel.setView(this);
        ArticleInfoImageAdapter adapter = new ArticleInfoImageAdapter(mContext,articleInfo.articleImageUrls);
        mBinding.viewpagerArticleinfoImage.setAdapter(adapter);
    }


    public void requestData(String articleId){
        mModel.getArticleInfo(articleId);
    }

    public View.OnClickListener getOnClickListener(){
        return  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.img_likeornot:
                        break;
                }
            }
        };
    }


    @Override
    public void onRequestFinished() {

    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        Action action = data.action;
        if(action == Action.ArticleInfoModel_GetInfo){
            this.articleInfo = (ArticleInfo) data.t;

            if(articleInfo.articleImageUrls!=null){


            }
            notifyPropertyChanged(BR.articleInfo);

        }
    }

    private void addImages(HackyViewPager viewPager) {

        mBinding.linearlayoutArticleinfo.addView(viewPager,3);
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {

    }
}
