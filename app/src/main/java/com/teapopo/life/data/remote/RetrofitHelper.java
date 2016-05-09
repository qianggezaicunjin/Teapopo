package com.teapopo.life.data.remote;

import android.content.Context;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;
import com.teapopo.life.MyApplication;
import com.teapopo.life.data.remote.cookie.PersistentCookieStore;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import timber.log.Timber;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class RetrofitHelper {
    PersistentCookieStore mPersistentCookieStore;
    public RetrofitHelper(PersistentCookieStore persistentCookieStore){
        mPersistentCookieStore = persistentCookieStore;
    }
    public  NetWorkService netWorkService(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .cookieJar(new CookiesManager())
                .build();

        Retrofit client = new Retrofit.Builder()
                .baseUrl(NetWorkService.ENDPOINT)
                .client(okHttpClient)
                .addConverterFactory(LoganSquareConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return client.create(NetWorkService.class);
    }

    private class CookiesManager implements CookieJar{

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    Timber.d("保存的cookie为:%s,网址为:%s",cookies.toString(),url.toString());
                    mPersistentCookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = mPersistentCookieStore.get(url);
            Timber.d("用cookies去请求的url为:%s,cookie为:%s",url.toString(),cookies.toString());
            return cookies;
        }
    }
}
