package com.gdc.gdcmvp.application;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.LocalBroadcastManager;

import com.gdc.gdcmvp.util.LanguageUtil;


/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.application
 * @file ApplicationContext
 * @date 2018/2/5
 */

public class ApplicationContext {
    private static Context context;
    private static Handler UIHandler = null;

    public static void initContext(Context context) {
        ApplicationContext.context = LanguageUtil.initContext(context);
        if (null == UIHandler) {
            UIHandler = new Handler();
        }
    }

    public static Context getContext() {
        return context.getApplicationContext();
    }

    public static Context getWrappedContext() {
        return context;
    }

    public static Handler getUIHandler() {
        if (null == UIHandler) {
            throw new RuntimeException("必须先在MyApplication中初始化ApplicationContext.initContext(application)");
        }
        return UIHandler;
    }

    public static String getString(@StringRes int resId){
        return context.getString(resId);
    }

    public static String getString(@StringRes int resId, Object... formatArgs) {
        return context.getString(resId,formatArgs);
    }

    public static int getColor(@ColorRes int resId){
        return context.getResources().getColor(resId);
    }

    public static int getDimensionPixelOffset(@DimenRes int resId){
        return context.getResources().getDimensionPixelOffset(resId);
    }

    @SuppressWarnings("deprecation")
    public static Drawable getDrawable(@DrawableRes int resId){
        return context.getResources().getDrawable(resId);
    }

    public static int getIdentifier(String name, String defType, String defPackage) {
        return context.getResources().getIdentifier(name, defType, defPackage);
    }

    public static int getSystemId(String name) {
        return getIdentifier(name, "id", "android");
    }

    public static LocalBroadcastManager getLocalBroadcastManager() {
        return LocalBroadcastManager.getInstance(context);
    }
}
