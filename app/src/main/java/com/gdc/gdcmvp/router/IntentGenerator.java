package com.gdc.gdcmvp.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.gdc.gdcmvp.application.ApplicationContext;


/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.router
 * @file IntentGenerator 生成Intent
 * @date 2018/2/24
 */

public class IntentGenerator {
    /**
     * 页面入参的Key值
     */
    public static final String PARAM_DATA = "param_data";

    /**
     * 隐式意图调用服务
     *
     * @param tag
     * @param bundle
     * @return
     */
    public static Intent toServiceImplicitly(String tag, Intent bundle) {
        return toServiceImplicitly(null, tag, bundle);
    }

    /**
     * 隐式意图调用服务, 如果包名为空则使用当前app的包名。
     *
     * @param packageName
     * @param tag
     * @param bundle
     * @return
     */
    public static Intent toServiceImplicitly(String packageName, String tag,
            Intent bundle) {
        Intent ret = new Intent();
        ret.setAction(tag);
        ret.setPackage(null == packageName ?
                ApplicationContext
                        .getContext()
                        .getPackageName() :
                packageName);
        if (null != bundle) {
            ret.putExtras(bundle);
        }
        return ret;
    }

    /**
     * 显式意图跳转页面，如果context为空则用ApplicationContext
     *
     * @param context 上下文
     * @param clazz 跳到哪个Activity
     * @param bundle 数据载体Intent
     * @return
     */
    public static Intent toPageExplicitly(Context context, Class<? extends Activity> clazz, Intent bundle) {
        if (null == context) {
            context = ApplicationContext.getContext();
        }
        //在新的任务栈中开启activity
        Intent ret = new Intent(context, clazz);
        if (!(context instanceof Activity)) {
            ret.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        if (null != bundle) {
            ret.putExtras(bundle);
        }

        return ret;
    }
}
