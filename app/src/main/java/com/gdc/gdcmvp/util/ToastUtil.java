package com.gdc.gdcmvp.util;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.gdc.gdcmvp.application.ApplicationContext;


/**
 * @author XiongJie
 * @version appVer
 * @Package com.gdc.gdcmvp.util
 * @file
 * @Description:
 * @date 2018/3/18 11:50
 * @since appVer
 */

public class ToastUtil {

    /**
     * 在主线程或子线程中显示汽泡提示
     * @param text
     */
    public static void show(@NonNull String text) {
        show(ApplicationContext.getContext(), text);
    }

    /**
     * 只包含文本的使用text内容的toast
     *
     * @param context 上下文
     * @param text    显示的字符串文本
     */
    public static void show(@NonNull final Context context, @NonNull final String
            text) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            //s1:主线程toast提示
            Toast
                    .makeText(context, text, Toast.LENGTH_SHORT)
                    .show();
        }else{
            //s2:子线程消息切换至主线程
            ApplicationContext.getUIHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                }
            },0);
        }
    }
}
