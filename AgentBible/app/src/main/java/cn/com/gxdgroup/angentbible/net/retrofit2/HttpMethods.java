package cn.com.gxdgroup.angentbible.net.retrofit2;

import cn.com.gxdgroup.angentbible.domain.MoviesBean;
import cn.com.gxdgroup.angentbible.net.api.ServiceApi;
import retrofit2.Retrofit;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Ivy on 2016/11/2.
 *
 * @description:
 */

public class HttpMethods {
    private Retrofit mRetrofit;
    private ServiceApi mServiceApi;

    //构造方法私有
    private HttpMethods() {
        mRetrofit = Retrofit2Utils.getRetrofit();
        mServiceApi = Retrofit2Utils.getServiceApi();
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }


    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//
    //                                                                            //
    //                                                                            //
    //                             以下是所有请求                                  //
    //                                                                            //
    //                                                                            //
    //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx//


    public void getTopMovie(Subscriber<MoviesBean> subscriber, int start, int count) {
        mServiceApi.getTopMovie(start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
