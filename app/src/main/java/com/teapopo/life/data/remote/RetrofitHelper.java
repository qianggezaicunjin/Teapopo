package com.teapopo.life.data.remote;

import android.content.Context;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;
import com.teapopo.life.MyApplication;
import com.teapopo.life.data.remote.cookie.PersistentCookieStore;
import com.teapopo.life.util.DataUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by Administrator on 2016/4/7 0007.
 */
public class RetrofitHelper {
    public Context mContext;
    public RetrofitHelper(Context context){
        this.mContext = context;
    }
    public  NetWorkService netWorkService(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //设置缓存路径
        File httpCacheDirectory = new File(mContext.getCacheDir(), "responses");
        //设置缓存 10M
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
//                .addNetworkInterceptor(new CacheInterceptor())
//                .cache(cache)
                .cookieJar(new CookiesManager())
                .build();

        Retrofit client = new Retrofit.Builder()
                .baseUrl(NetWorkService.ENDPOINT)
                .client(okHttpClient)
                .addConverterFactory(LoganSquareConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return client.create(NetWorkService.class);
    }

    /**
     * 设置okhttp3的缓存
     */
    private class CacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if(!DataUtils.isNetworkAvailable(mContext)){
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Timber.i("no network");
            }
            Response response = chain.proceed(request);

            if (DataUtils.isNetworkAvailable(mContext)) {
                int maxAge = 0 * 60; // 有网络时 设置缓存超时时间0个小时
                Timber.i("has network maxAge=%d",maxAge);
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build();
            } else {
                Timber.i("network error");
                int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
                Timber.i("has maxStale=%d",maxStale);
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
                Timber.i("response build maxStale=%d",maxStale);
            }
            return response;
        }
    }

    /**
     * 设置okhttpclient的cookie
     */
    private class CookiesManager implements CookieJar{
        PersistentCookieStore persistentCookieStore = new PersistentCookieStore(mContext);
        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    Timber.d("保存的cookie为:%s,网址为:%s",cookies.toString(),url.toString());
//                    if(url.toString().equals("http://www.teapopo.com/api/members/login")){
//
//                    }
                    persistentCookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = persistentCookieStore.get(url);
            Timber.d("用cookies去请求的url为:%s,cookie为:%s",url.toString(),cookies.toString());
            return cookies;
        }
    }
}
