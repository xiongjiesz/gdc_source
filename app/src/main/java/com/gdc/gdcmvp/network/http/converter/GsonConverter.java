package com.gdc.gdcmvp.network.http.converter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.List;


/**
 * @author XiongJie
 * @version appVer
 * @package com.gdc.common.network.http
 * @file GsonConverter
 * @brief Gson数据格式转换类
 * @date 2017/6/12
 * @since appVer
 */
public class GsonConverter {
    /**
     * 请求参数由json数据拼接
     */
    public static final  int    JSON_GET     = 1001;

    /**
     * 将Object对象转换成json串
     * @param obj 参数对象
     * @param <T>
     * @return
     */
    public static <T> String toJsonString(T obj){
        String ret = null;
        //gson序列化不忽略null为空的key
        Gson gson = new GsonBuilder().serializeNulls().create();
        ret = gson.toJson(obj);
        return ret;
    }

    /**
     * 将json数据转换成java对象
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toObject(String jsonStr,Class<T> clazz){
        if(TextUtils.isEmpty(jsonStr)){
            return  null;
        }
        return new Gson().fromJson(jsonStr,clazz);
    }

    /**
     * 将JsonElement类型的数据转换成java对象
     * @param json
     * @param clazz
     * @return
     */
    public static Object toObject(JsonElement json,Class clazz){
        if(null == json){
            return null;
        }
        return  new Gson().fromJson(json,clazz);
    }

    /**
     * 将Json数组解析成相应的映射对象列表
     * @param jsonData
     * @param <T>
     * @return
     */
    public static <T> List<T> parseJsonArrayWithGson(String jsonData){
        Gson gson = new Gson();
        List<T> result = gson.fromJson(jsonData,new TypeToken<List<T>>(){}.getType());
        return result;
    }
}
