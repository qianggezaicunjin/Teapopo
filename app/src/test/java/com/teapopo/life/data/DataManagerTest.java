package com.teapopo.life.data;

import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teapopo.life.BuildConfig;
import com.teapopo.life.data.remote.NetWorkService;
import com.teapopo.life.model.memberLikes.MemberLike;
import com.teapopo.life.model.memberLikes.MemberLikeDataOverView;
import com.teapopo.life.model.welfare.EventGoods;
import com.teapopo.life.model.welfare.EventGoodsComparator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by louiszgm on 2016/5/25.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DataManagerTest {
    private String JSON_ROOT_PATH = "/json/";
    private String jsonFullPath;
    NetWorkService mockNetWorkService;
    @Before
    public void setUp() throws URISyntaxException{
        //输出日志
        ShadowLog.stream = System.out;
        //获取测试json文件地址
        jsonFullPath = getClass().getResource(JSON_ROOT_PATH).toURI().getPath();

        //定义Http Client,并添加拦截器
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new MockInterceptor(jsonFullPath))
                .build();

        //设置Http Client
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetWorkService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        mockNetWorkService = retrofit.create(NetWorkService.class);
    }

    @Test
    public void getArticleTest()throws Exception{

    }
    @Test
    public void getEventGoodsListTest()throws Exception{
        JsonObject jsonObject = mockNetWorkService.test().execute().body();
        for(MemberLike memberLike:handleMemberLikesJson(jsonObject)){
            Timber.d("data为:%s",memberLike.date);
            for(MemberLikeDataOverView memberLikeDataOverView:memberLike.dataOverViewList){
                Timber.d("文章的ID:%s",memberLikeDataOverView.id);
            }
        }
    }

    public List<MemberLike> handleMemberLikesJson(JsonObject jsonObject){
        List<MemberLike> memberLikeList = new ArrayList<>();
        JsonObject data = jsonObject.getAsJsonObject("data");
        JsonObject likes = data.getAsJsonObject("likes");
        JsonObject like_count = data.getAsJsonObject("like_count");
        for(Map.Entry entry:likes.entrySet()){
            MemberLike memberLike = new MemberLike();
            memberLike.date = entry.getKey().toString();
            try {
                memberLike.dataOverViewList = LoganSquare.parseList(entry.getValue().toString(),MemberLikeDataOverView.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            memberLike.likeCounts = like_count.get(memberLike.date).getAsString();
            memberLikeList.add(memberLike);
        }
        return memberLikeList;
    }
}
