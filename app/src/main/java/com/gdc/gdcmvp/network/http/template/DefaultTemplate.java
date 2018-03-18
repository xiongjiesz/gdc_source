package com.gdc.gdcmvp.network.http.template;

import android.text.TextUtils;

import com.gdc.gdcmvp.constant.Constant;
import com.gdc.gdcmvp.log.LogHelper;
import com.gdc.gdcmvp.network.http.config.Config;
import com.gdc.gdcmvp.network.http.converter.GsonConverter;
import com.gdc.gdcmvp.network.http.entity.GDCRequestEntity;
import com.gdc.gdcmvp.network.http.entity.ResponseEntity;
import com.gdc.gdcmvp.network.http.parser.ParserFactory;
import com.gdc.gdcmvp.network.http.signature.SignManager;
import com.gdc.gdcmvp.user.UserManager;
import com.gdc.gdcmvp.util.AppInfoUtil;
import com.gdc.gdcmvp.util.LanguageUtil;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * @author XiongJie
 * @version appVer
 * @package com.gdc.common.network.http.template
 * @file DefaultTemplate
 * @brief 请求参数拼接模版
 * @date 2017/6/12
 * @since appVer
 */
public class DefaultTemplate {
    private static final String TAG = "DefaultTemplate";
    /**
     * 创建请求体
     * @param config
     * @param param
     * @param <T>
     * @return
     */
    public static <T> RequestBody create(Config config, T param){
        RequestBody ret = null;
        // s1:包装请求业务参数的实体
        GDCRequestEntity<T> requestEntity = genBizReqEntity(config.reqCode, param);
        // s2:通过转换器生成jsonString
        String encryptionParam = GsonConverter.toJsonString(requestEntity);
        // s3:签名和加密
        String[] encryptMessage = SignManager.encryptMessage(config.type,
                encryptionParam);
        // s4:取出加密后的签名字符串
        config.reqSign = encryptMessage[1];
        // s5:生成请求体
        ret = new FormBody.Builder()
                .add("encryptionParam",encryptMessage[0])//请求参数
                .build();
        return ret;
    }

    /**
     * 将响应数据转换成响应实体对象
     * @param config
     * @param response
     * @param clazz
     * @return
     */
    public static ResponseEntity parse(Config config, String response, Class
            clazz, Headers headers) {
        ResponseEntity ret = null;

        String respCode = headers.get("respCode");
        String sign = headers.get("sign");

        if(TextUtils.isEmpty(respCode)){
            //@TODO S1 协议格式错误处理
        }else if(TextUtils.isEmpty(sign)){
            //@TODO s2 不成功没有实体/只返回551001的逻辑处理
        }else{
            //s3 成功有实体
            //s3.1 验签解密字符串
            String bizStr = SignManager.decryptMessage(config.type,response,sign);
            LogHelper.i(TAG, config.method + " 解密结果 : " + bizStr);
            if(null == bizStr){
                //@TODO 验签失败的处理
            }else {
                //验签成功
                ret = ParserFactory.parseDefaultJson(bizStr,clazz);
                if(null == ret){
                    //@TODO 生成实体失败的处理
                }else{
                    //生成实体成功
                    ret.setResponseCode(respCode);
                }
            }
        }
        return ret;
    }

    /**
     * 获取包装请求业务参数的实体
     * @param reqCode
     * @param param
     * @param <T>
     * @return
     */
    private static <T> GDCRequestEntity<T> genBizReqEntity(String reqCode, T
            param) {
        String time = Long.toString(System.currentTimeMillis());
        return new GDCRequestEntity<T>(time, param);
    }

    public static Request.Builder getHeader(Config config){
         /*
         * 请求头：
         * "sign": "be0688b93a63294407197e2599fb67a0",
         * "clientUDID": "1b8a8bc-8ee7-42a4-85a0-78fbe07e9eae",
         * "countryId": "86",
         * "reqCode": "shopMall3001",
         * "appId": "1001",
         * "lang": "zh",
         * "osName": "1001",
         * "version": "1.2.1",
         * "memberNo": "18503075497i"
         */
         return new Request.Builder()
                 .header("sign",config.reqSign)
                 .header("reqCode",config.reqCode)
                 .header("appId",String.valueOf(Constant.APP_ID))
                 .header("lang", LanguageUtil.getCurrentLanguage())
                 .header("osName",Constant.OS_NAME)
                 .header("version", AppInfoUtil.getVersionName())
                 .header("memberNo", UserManager.getUser().getCurrentAccount());
    }
}
