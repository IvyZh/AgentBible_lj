package cn.com.gxdgroup.angentbible.utils;

import android.content.Context;
import android.content.SharedPreferences;

import retrofit2.http.Multipart;

/**
 * 专门访问和设置SharePreference的工具类, 保存和配置一些设置信息
 *
 * @author Kevin
 */
public class SharedPreUtils {
    public static String SHARE_PREFS_NAME = "SHARE_PREFS_NAME";// 使用前一定要赋值
    private static SharedPreferences mSharedPreferences;

    public static void clear() {
        if (mSharedPreferences == null) {
            mSharedPreferences = UIUtils.getContext().getSharedPreferences(
                    SHARE_PREFS_NAME, Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().clear().commit();
    }

    /**
     * Boolean 类型
     **/
    public static boolean getBoolean(String key, boolean defaultValue) {
        if (mSharedPreferences == null) {
            mSharedPreferences = UIUtils.getContext().getSharedPreferences(
                    SHARE_PREFS_NAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public static void putBoolean(String key, boolean value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = UIUtils.getContext().getSharedPreferences(
                    SHARE_PREFS_NAME, Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().putBoolean(key, value).commit();
    }

    /**
     * String 类型
     **/
    public static void putString(String key, String value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = UIUtils.getContext().getSharedPreferences(
                    SHARE_PREFS_NAME, Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().putString(key, value).commit();
    }

    public static String getString(String key, String defaultValue) {
        if (mSharedPreferences == null) {
            mSharedPreferences = UIUtils.getContext().getSharedPreferences(
                    SHARE_PREFS_NAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getString(key, defaultValue);
    }

    /**
     * Int 类型
     **/
    public static void putInt(String key, int value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = UIUtils.getContext().getSharedPreferences(
                    SHARE_PREFS_NAME, Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().putInt(key, value).commit();
    }

    public static int getInt(String key, int defaultValue) {
        if (mSharedPreferences == null) {
            mSharedPreferences = UIUtils.getContext().getSharedPreferences(
                    SHARE_PREFS_NAME, Context.MODE_PRIVATE);
        }

        return mSharedPreferences.getInt(key, defaultValue);
    }

    /**
     * Long 类型
     **/
    public static void putLong(String key, long value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = UIUtils.getContext().getSharedPreferences(
                    SHARE_PREFS_NAME, Context.MODE_PRIVATE);
        }

        mSharedPreferences.edit().putLong(key, value).commit();
    }

    public static long getLong(String key, long defValue) {
        if (mSharedPreferences == null) {
            mSharedPreferences = UIUtils.getContext().getSharedPreferences(
                    SHARE_PREFS_NAME, Context.MODE_PRIVATE);
        }

        return mSharedPreferences.getLong(key, defValue);
    }
}
