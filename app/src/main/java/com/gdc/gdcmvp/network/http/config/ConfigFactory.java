package com.gdc.gdcmvp.network.http.config;

import com.gdc.gdcmvp.network.http.BaseURL;
import com.gdc.gdcmvp.network.http.template.DefaultTemplate;

import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * @author XiongJie
 * @version appVer
 * @package com.gdc.common.network.http.config
 * @file ConfigFactory
 * @brief 配置类，配置请求类型，接口名，请求码，数据类型，签名方式等信息，可扩展
 * @date 2017/6/12
 * @since appVer
 */
public class ConfigFactory {
    /**
     * @TODO 改造成Servlet请求,http://www.gdc.com/gdc/gdcInterface/LoginServlet
     */
    private static final String FORMAT_URL_DEFAULT     =
            "http://%s/%s/%s";
    /**
     * 主机地址
     */
    private static final String WEB_HOST               = BaseURL.getWebHost();

    /**
     * 配置请求接口的请求类型，签名方式，请求数据类型
     * @param config
     * @return
     */
    public static Config handleConfig(Config config){
        return ConfigAdapter.handle(config);
    }

    /**
     * 根据配置信息创建请求的URL地址
     * @param config
     * @return
     */
    public static String createURL(Config config) {
        return createURL(config.type,config.method,config.path);
    }

    /**
     * 根据请求类型与方法名移创建请求的URL地址
     * @param type
     * @param method
     * @return
     */
    public static String createURL(int type, String method) {
        return createURL(type, method, null);
    }

    public static String createURL(int type, String method,String path) {
        String ret=null;
        switch (type){
        case Constant.TYPE_DEFAULT:
            //http://%s/%s/%s
            ret = FORMAT_URL_DEFAULT;
            break;
        }
        //s1:将请求路径与调用的接口方法进行拼接
        //ret = MessageFormat.format(ret,method);
        ret = String.format(ret,WEB_HOST,path,method);
        return ret;
    }

    /**
     * 创建请求体
     * @param config 请求类型，签名方式，请求数据类型
     * @param param 请求参数数据对象
     * @param <T>
     * @return
     */
    public static <T> RequestBody createBody(Config config, T param){
        RequestBody ret = null;
        switch (config.type){
        case Constant.TYPE_DEFAULT:
            ret = DefaultTemplate.create(config,param);
            break;
        }
        return ret;
    }

    public static Request.Builder createHeader(Config config){
        Request.Builder ret = DefaultTemplate.getHeader(config);
        return ret;
    }
}
