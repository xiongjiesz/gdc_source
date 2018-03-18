package com.gdc.gdcmvp.network.http.exception;

/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.network.http.exception
 * @file RxHttpResponseException HTTP 响应信息的异常封装对象
 * @date 2018/2/24
 */

public class RxHttpResponseException extends Exception {
    private String method;
    private String respCode;
    private String errorInfo;

    public RxHttpResponseException() {
        super();
    }

    public RxHttpResponseException(String method, String respCode, String errorInfo) {
        super();
        this.method = method;
        this.respCode = respCode;
        this.errorInfo = errorInfo;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}
