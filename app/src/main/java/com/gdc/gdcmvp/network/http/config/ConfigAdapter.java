package com.gdc.gdcmvp.network.http.config;

import com.gdc.gdcmvp.network.http.converter.GsonConverter;
import com.gdc.gdcmvp.network.http.signature.SignManager;


/**
 * @author XiongJie
 * @version appVer
 * @package com.gdc.mvp.network.http.config
 * @file ConfigAdapter
 * @brief 请求适配器 配置请求类型，接口名，请求码，数据类型，签名方式等信息，可扩展
 * @date 2017/6/12
 * @since appVer
 */
public class ConfigAdapter {
    /**
     * 请求接口的数据类型
     */
    private static final int FIELD_DATA = 1;
    /**
     * 请求接口的验签方式
     */
    private static final int FIELD_SIGN = 2;
    /**
     * 请求接口的入参 使用xml拼接
     */
    private static final int FIELD_XML = 3;
    /**
     * 请求接口的入参 使用json拼接
     */
    private static final int FILED_JSON = 4;


    public static Config handle(Config config){
        switch (config.type){
        case Constant.TYPE_DEFAULT:
            config = setData(config, FIELD_DATA, Constant.DATA_JSON);
            config = setData(config, FIELD_SIGN, SignManager.TYPE_MD5);
            config = setData(config, FILED_JSON, GsonConverter.JSON_GET);
            break;
        }
        return config;
    }

    public static Config setData(Config config, int type,int newValue){
        int oldValue = Config.VALUE_DEFAULT;
        switch (type){
        case FIELD_DATA :
            //s1:数据类型
            oldValue = config.dataType;
            if(Config.VALUE_DEFAULT == oldValue){
                config.dataType = newValue;
            }
            break;
        case FIELD_SIGN:
            //s2:签名方式
            oldValue = config.signType;
            if(Config.VALUE_DEFAULT == oldValue){
                config.signType = newValue;
            }
            break;
        case FIELD_XML:
            //s3:xml
            oldValue = config.xmlType;
            if(Config.VALUE_DEFAULT == oldValue){
                config.xmlType = newValue;
            }
            break;
        case FILED_JSON:
            //s4:json
            oldValue = config.jsonType;
            if(Config.VALUE_DEFAULT == oldValue){
                config.jsonType = newValue;
            }
            break;
        }
        return config;
    }
}
