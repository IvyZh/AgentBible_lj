package cn.com.gxdgroup.angentbible.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.Map;

import cn.com.gxdgroup.angentbible.base.MyApplication;

public class UIUtils {
    public static Toast mToast;

    public static void showToast(final String msg) {
        if (mToast == null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mToast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
                }
            });
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mToast.setText(msg);
                mToast.show();
            }
        });
    }


    public static Context getContext() {
        return MyApplication.getContext();
    }

    public static Resources getResources() {
        return MyApplication.getContext().getResources();
    }


    public static void runOnUiThread(Runnable runnable, long delayMillis) {
        if (android.os.Process.myTid() == MyApplication.getMainTid()) {
            if (delayMillis != 0) {
                MyApplication.getHandler().postDelayed(runnable, delayMillis);
            } else {
                runnable.run();
            }
        } else {
            MyApplication.getHandler().postDelayed(runnable, delayMillis);
        }
    }

    public static void runOnUiThread(Runnable runnable) {
        runOnUiThread(runnable, 0);
    }

    public static int getColor(int colorId) {
        return getResources().getColor(colorId);
    }

    public static View inflate(int viewId) {
        return View.inflate(getContext(), viewId, null);
    }

    public static Drawable getDrawable(int id) {
        Drawable drawable = getResources().getDrawable(id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
        return drawable;
    }

    public static Typeface getTypeFaceHelveticaNeue() {
        return Typeface.createFromAsset(getContext().getAssets(), "HelveticaNeueLTPro-Th_0.ttf");
//        return Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeueLTPro-Th_0.ttf");
    }


    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    //获取屏幕原始尺寸高度，包括虚拟功能键高度
    public static int getDpi(Context context) {
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     * 获取 虚拟按键的高度
     *
     * @param context
     * @return
     */
    public static int getBottomStatusHeight(Context context) {
        int totalHeight = getDpi(context);

        int contentHeight = getScreenHeight(context);

        return totalHeight - contentHeight;
    }

    /**
     * 标题栏高度
     *
     * @return
     */
    public static int getTitleHeight(Activity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }


    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static void sopMap(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            L.v("key= " + entry.getKey() + "   , value= " + entry.getValue());
        }
    }


    public static String getAppInfo() {
        try {
            String pkName = getContext().getPackageName();
            String versionName = getContext().getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = getContext().getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            return pkName + "   " + versionName + "  " + versionCode;
        } catch (Exception e) {
        }
        return null;
    }

}