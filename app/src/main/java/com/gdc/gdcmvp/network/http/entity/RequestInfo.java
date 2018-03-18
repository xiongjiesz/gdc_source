package com.gdc.gdcmvp.network.http.entity;

/**
 * @author XiongJie
 * @version appVer
 * @package com.gdc.common.network.http.entity
 * @file RequestInfo
 * @brief 封装请求实体信息
 * @date 2017/6/12
 * @since appVer
 */
public class RequestInfo {
    /**
     * 请求接口的方法
     */
    private String method;
    /**
     * 请求接口的响应码
     */
    private String responseCode;
    /**
     * 附加信息
     */
    private String extraInfo;
    /**
     * 请求接口传递的参数
     */
    private Object data;

    public RequestInfo(String method, String responseCode, String extraInfo,
            Object data) {
        this.method = method;
        this.responseCode = responseCode;
        this.extraInfo = extraInfo;
        this.data = data;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
