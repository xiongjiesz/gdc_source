package com.gdc.gdcmvp.network.http.config;

import com.gdc.gdcmvp.constant.Constant;


/**
 * @author XiongJie
 * @version appVer
 * @package com.gdc.common.api
 * @file Config
 * @brief
 * @date 2017/6/9
 * @since appVer
 */
public class Config {
    public static final int VALUE_DEFAULT = -1;

    /**
     * ********************http请求的配置属性********************
     */
    public int type = Constant.TYPE_DEFAULT;
    /**
     * 请求方法名称
     */
    public String method;

    /**
     * 请求路径
     */
    public String path;

    /**
     * 请求码
     */
    public String reqCode;
    /**
     * 请求签名
     */
    public String reqSign;
    /**
     * json/xml
     */
    public int dataType = VALUE_DEFAULT;
    /**
     * SignManager里的type
     */
    public int signType = VALUE_DEFAULT;
    /**
     * XmlConverter里的type
     */
    public int xmlType = VALUE_DEFAULT;
    /**
     * GsonConverter里的type
     */
    public int jsonType = VALUE_DEFAULT;
    /**
     * 缓存类别
     */
    public int cacheType = VALUE_DEFAULT;

    /**
     * ************************文件下载的配置属性 ***********************
     */
    /**
     * 下载类型
     */
    public int downloadType;
    /**
     * isDefault:true 表示地址是又拍云的地址 false则不是
     */
    public boolean isDefault = false;
    /**
     * 是否忽略响应码
     */
    public boolean ignoreRespCodeEscape = false;
    /**
     * 下载文件的保存路径
     */
    public String filepath;

    /**
     * 通过反射替换回调时需要给回调配置文件路径
     * @param filepath
     */
    public Config(String filepath) {
        this.filepath = filepath;
    }

    /**
     * @param type 请求类别
     * @param method 请求方法
     * @param reqCode 请求码
     */
    public Config(int type, String method, String reqCode) {
        this.type = type;
        this.method = method;
        this.reqCode = reqCode;
    }

    /**
     * 错误码的配置属性
     * @param type 请求类别
     * @param method 请求方法
     * @param reqCode 请求码
     * @param path 请求路径
     */
    public Config(int type, String method, String reqCode, String path) {
        this.type = type;
        this.method = method;
        this.reqCode = reqCode;
        this.path = path;
    }

    /**
     * 如果public boolean isRSAEncry() 返回 true 用此构造函数创建Config以使用HttpUtil进行请求
     * @param type
     * @param method
     * @param reqCode
     * @param signType
     */
    public Config(int type, String method, String reqCode, int signType) {
        this.type = type;
        this.method = method;
        this.reqCode = reqCode;
        this.signType = signType;
    }

    /**
     * 下载请求的配置的构造函数
     * @param downloadType
     * @param method
     * @param isDefault
     * @param filepath
     */

    public Config(int downloadType, String method, boolean isDefault, String filepath) {
        this.method = method;
        this.downloadType = downloadType;
        this.isDefault = isDefault;
        this.filepath = filepath;
    }





}
