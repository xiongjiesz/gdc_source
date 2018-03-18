package com.gdc.gdcmvp.network.http.entity;

/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.network.http.entity
 * @file GDCRequestEntity
 * @date 2018/1/31
 */

public class GDCRequestEntity<T> {
    private String reqTime;
    private T reqParam;

    public GDCRequestEntity(String reqTime, T reqParam) {
        super();
        this.reqTime = reqTime;
        this.reqParam = reqParam;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public T getReqParam() {
        return reqParam;
    }

    public void setReqParam(T reqParam) {
        this.reqParam = reqParam;
    }
}
