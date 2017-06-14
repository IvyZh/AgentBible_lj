package cn.com.gxdgroup.angentbible.net.retrofit2;

import cn.com.gxdgroup.angentbible.net.api.ServiceApi;
import cn.com.gxdgroup.angentbible.net.client.OkHttp3Utils;
import cn.com.gxdgroup.angentbible.net.converter.JsonConverterFactory;
import cn.com.gxdgroup.angentbible.net.converter.StringConverterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ivy on 2016/10/9.
 *
 * @description: 参考文章：http://blog.csdn.net/wlt111111/article/details/51455524
 */

public class Retrofit2Utils {
    private static Retrofit mRetrofit = null;
    private static String BASE_URL = "https://api.douban.com";//https://api.douban.com/v2/movie/top250?start=0&count=10
    private static OkHttpClient mOkHttpClient;
    private static ServiceApi mServiceApi;

    //通用的
    public static Retrofit getRetrofit() {

        if (mRetrofit == null) {
            synchronized (Retrofit2Utils.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = OkHttp3Utils.getOkHttpClient();
                }
                if (mRetrofit == null) {
                    mRetrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            //.addConverterFactory(JsonConverterFactory.create())//添加转换器
                            .addConverterFactory(StringConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(mOkHttpClient)
                            .build();
                }
            }
        }
        return mRetrofit;
    }

    public static ServiceApi getServiceApi() {
        if (mServiceApi == null) {
            synchronized (Retrofit2Utils.class) {
                if (mServiceApi == null) {
                    mServiceApi = getRetrofit().create(ServiceApi.class);
                }
            }
        }
        return mServiceApi;
    }

}
