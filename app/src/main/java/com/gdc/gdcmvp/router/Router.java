package com.gdc.gdcmvp.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.gdc.gdcmvp.log.LogHelper;

import java.io.Serializable;
import java.util.Set;


/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.router
 * @file Router
 * @date 2018/3/5
 */

public class Router implements RouterConstant {

    private static final java.lang.String TAG = "Router";
    /**
     * 序列化参数数据用来传递的Key
     */
    public static final  String PARAM_DATA = "param_data";

    /**
     * 所有的activity
     */
    private ArrayMap<String, Class<? extends Activity>> activityMap;


    private static class RouterHolder {
        static Router instance = new Router();
    }

    public static Router getInstance() {
        return RouterHolder.instance;
    }

    private Router() {
        activityMap = new ArrayMap<>();
    }

    /**
     * 生成跳转意图
     *
     * @param context   上下文
     * @param uriString 跳转目标页字符串配置
     * @param intent    意图
     * @param data      需要在界面之间传递的数据
     * @return
     */
    public Intent toTarget(@Nullable Context context, @NonNull String uriString,
            @Nullable Intent intent, @Nullable Serializable data) {
        Intent ret = null;
        Uri uri = Uri.parse(uriString);
        //boolean  isOpaque()  Returns true if this URI is opaque不透明的、模糊的 like
        // "mailto:nobody@google.com".
        if (uri.isOpaque()) {
            return ret;
        }
        //s1:获取跳转Activity的URI 常量里面配置的Activity的字符串
        String routeUri = getUri(uri);
        //s2:不会返回null 从uri中获取参数名 首页模板的跳转参数键值对形式的
        Set<String> params = uri.getQueryParameterNames();
        //s3:根据跳转uri获取Activity对象
        Class clazz = activityMap.get(routeUri);
        if (null == clazz) {
            LogHelper.w(TAG, "没有注册此跳转路径：" + routeUri);
        } else {
            //s4：创建数据传递意图
            if(null == intent){
                //s4.1:携带得有参数,则创建数据传递意图
                if(0 < params.size() || null != data){
                    ret = new Intent();
                }
            }else{
                ret = intent;
            }

            //s4.2:携带参数之网络请求携带的键值对,把携带的参数放到Intent中
            if(0 < params.size()){
                for(String key : params){
                    ret.putExtra(key,uri.getQueryParameter(key));
                }
            }

            //s4.3:携带参数之序列化对象 把携带的序列化数据放到Intent中
            if(null != data){
                ret.putExtra(PARAM_DATA, data);
            }

            //s5: 生成显示跳转意图
            ret = IntentGenerator.toPageExplicitly(context, clazz, ret);
        }
        return ret;
    }

    private static String getUri(Uri uri) {
        String ret;

        String scheme = uri.getScheme();
        String authority = uri.getAuthority();
        String path = uri.getPath();

        if (TextUtils.isEmpty(authority)) {
            // 基址是空，路径要去掉/
            if (path.startsWith("/")) {
                ret = path.substring(1, path.length());
            } else {
                ret = path;
            }
        } else {
            ret = authority.concat(path);
        }

        if (!TextUtils.isEmpty(scheme)) {
            ret = String.format("%s://%s", scheme, ret);
        }

        return ret;
    }

}
