package com.teapopo.life.data;

import com.google.gson.JsonObject;
import com.teapopo.life.BuildConfig;
import com.teapopo.life.data.remote.NetWorkService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.net.URISyntaxException;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
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
                .client(okHttpClient)
                .build();

        mockNetWorkService = retrofit.create(NetWorkService.class);
    }

    @Test
    public void getArticleTest()throws Exception{
//        retrofit2.Response<JsonObject> response = mockNetWorkService.getArticle("发现").execute();
//        JsonObject jsonObject = response.body();
//        JsonObject data = jsonObject.getAsJsonObject("data");
//        JsonObject members = data.getAsJsonObject("members");
//        JsonObject item = members.getAsJsonObject("44252");
//        Timber.d(item.toString());
    }
}
