package com.gdc.gdcmvp.util;

import com.gdc.gdcmvp.network.http.config.Constant;


/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.util
 * @file ReflectUtil 反射工具类
 * @date 2018/2/9
 */

public class ReflectUtil {
    public static Object createInstance(Class clazz) {
        Object ret=null;
        try {
            ret = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            if(Constant.DEBUG_MODEL){
                throw new RuntimeException("类 ："+clazz.toString()+" 必须写出无参数的构造函数");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
