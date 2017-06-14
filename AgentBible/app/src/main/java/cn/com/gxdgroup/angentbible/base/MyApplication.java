package cn.com.gxdgroup.angentbible.base;

import android.app.Application;
import android.os.Handler;

import com.activeandroid.ActiveAndroid;
import com.baidu.mapapi.SDKInitializer;

import cn.com.gxdgroup.angentbible.net.client.OkHttp3Utils;
import cn.com.gxdgroup.angentbible.utils.L;
import cn.com.gxdgroup.angentbible.utils.UIUtils;
import okhttp3.OkHttpClient;

/**
 * Created by Ivy on 2016/10/11.
 *
 * @description:
 */

public class MyApplication extends Application {
    private static MyApplication mContext;
    private static Handler handler;
    private static int mainTid;

    @Override
    public void onCreate() {
        super.onCreate();
        //SDKInitializer.initialize(this);
//        注意：在SDK各功能组件使用之前都需要调用，因此我们建议该方法放在Application的初始化方法中
        mContext = this;
        handler = new Handler();
        mainTid = android.os.Process.myTid();
        initOkHttp();//初始化OkHttp

        ActiveAndroid.initialize(this);
        L.v("--" + UIUtils.getAppInfo());

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    /**
     * 初始化单例OkHttpClient对象
     */
    private void initOkHttp() {
        OkHttpClient client = OkHttp3Utils.getOkHttpClient();
    }

    public static MyApplication getContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainTid() {
        return mainTid;
    }

}
