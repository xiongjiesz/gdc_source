package com.gdc.gdcmvp.network.http.parser;

import android.text.TextUtils;

import com.gdc.gdcmvp.log.LogHelper;
import com.gdc.gdcmvp.network.http.config.Config;
import com.gdc.gdcmvp.network.http.config.Constant;
import com.gdc.gdcmvp.network.http.converter.GsonConverter;
import com.gdc.gdcmvp.network.http.entity.ResponseEntity;
import com.gdc.gdcmvp.network.http.template.DefaultTemplate;
import com.gdc.gdcmvp.util.ReflectUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

import okhttp3.Headers;


/**
 * @author XiongJie
 * @version appVer
 * @package com.gdc.common.network.http.parser
 * @file ParserFactory
 * @brief 响应数据转换工厂
 * @date 2017/6/12
 * @since appVer
 */
public class ParserFactory {

    private static final String TAG = "ParserFactory";

    public static ResponseEntity parseResponse(Config config, String response,
            Class clazz, Headers headers) {
        ResponseEntity ret = null;
        switch (config.type) {
        case Constant.TYPE_DEFAULT:
            ret = DefaultTemplate.parse(config, response, clazz,headers);
            break;
        }
        return ret;
    }

    /**
     * 将json字符串解析为Java对象实体
     * @param json
     * @param clazz
     * @return
     */
    public static ResponseEntity parseDefaultJson(String json, Class clazz) {
        ResponseEntity ret = null;
        String reqCode = null;
        String reqBak = null;
        try {
            //s1:创建解析器
            JsonParser parser = new JsonParser();
            JsonElement root = parser.parse(json);

            //s2:获取outParam
            JsonObject rootJson = root.getAsJsonObject();
            JsonObject contentJson = rootJson.getAsJsonObject("content");

            //s3:获取reqCode
            JsonPrimitive reqCodeJson = contentJson.getAsJsonPrimitive("reqCode");
            reqCode = reqCodeJson.getAsString();

            //s4:获取reqBak
            JsonPrimitive reqBakJson = contentJson.getAsJsonPrimitive("reqBak");
            reqBak = reqBakJson.getAsString();

            //s5:获取respParam
            JsonElement respParam = contentJson.get("respParam");
            if(null == respParam || "null".equals(respParam.toString())){
                ret = ParserFactory.createEntity(clazz);
                ret.setReqCode(reqCode);
                ret.setBackInfo(reqBak);
            }else{
                LogHelper.i(TAG, "业务响应参数 : " + respParam.toString());
                ret = (ResponseEntity) GsonConverter.toObject(respParam,clazz);
                if(null == ret && Constant.DEBUG_MODEL){
                    throw new RuntimeException("GsonConverter生成对象失败");
                }
                ret.setReqCode(reqCode);
                ret.setBackInfo(reqBak);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            if ((!TextUtils.isEmpty(reqCode)) && (!TextUtils.isEmpty(reqBak))) {
                ret = ParserFactory.createEntity(clazz);
                ret.setReqCode(reqCode);
                ret.setBackInfo(reqBak);
            }
        }
        return ret;
    }

    public static ResponseEntity createEntity(Class clazz){
        ResponseEntity ret = (ResponseEntity) ReflectUtil.createInstance(clazz);
        return ret;
    }
}
