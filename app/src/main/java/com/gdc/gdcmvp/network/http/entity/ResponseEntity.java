package com.gdc.gdcmvp.network.http.entity;

/**
 * @author XiongJie
 * @version appVer
 * @package com.gdc.common.network.http.entity
 * @file ResponseEntity
 * @brief 统一的响应基类
 * @date 2017/6/10
 * @since appVer
 */
public class ResponseEntity {
    /**
     * 响应码
     */
    public String responseCode;
    /**
     * 备注信息
     */
    public String backInfo;
    /**
     * 错误信息
     */
    public String errorInfo;
    /**
     * 社交json内的响应码，和外层的responseCode相同
     */
    public String reqCode;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getBackInfo() {
        return backInfo;
    }

    public void setBackInfo(String backInfo) {
        this.backInfo = backInfo;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getReqCode() {
        return reqCode;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode;
    }
}
