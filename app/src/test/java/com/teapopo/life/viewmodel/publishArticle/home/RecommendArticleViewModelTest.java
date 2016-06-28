package com.teapopo.life.viewmodel.publishArticle.home;

import com.teapopo.life.BuildConfig;
import com.teapopo.life.model.article.categoryarticle.RecommendArticleModel;
import com.teapopo.life.util.CustomShadowTestRunner;
import com.teapopo.life.util.TestUtils;
import com.teapopo.life.util.databinding.DBRecyclerView;
import com.teapopo.life.util.databinding.ShadowDBRecyclerView;
import com.teapopo.life.viewModel.home.RecomendArticleViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.annotation.Config;
import org.robolectric.internal.ShadowExtractor;
import org.robolectric.shadows.ShadowLog;

import static org.mockito.Mockito.mock;

/**
 * Created by louiszgm-pc on 2016/6/25.
 */
@RunWith(CustomShadowTestRunner.class)
@Config(constants = BuildConfig.class,shadows = {ShadowDBRecyclerView.class})
public class RecommendArticleViewModelTest {

    private ShadowDBRecyclerView dbRecyclerView;

    private RecomendArticleViewModel mViewModel;
    @Before
    public void setup() {
        TestUtils.setupDagger();
        DBRecyclerView db = new DBRecyclerView();
        dbRecyclerView = (ShadowDBRecyclerView) ShadowExtractor.extract(db);

        ShadowDBRecyclerView mock = Mockito.mock(ShadowDBRecyclerView.class);


    }
    @Test
    public void test1(){

    }
}
