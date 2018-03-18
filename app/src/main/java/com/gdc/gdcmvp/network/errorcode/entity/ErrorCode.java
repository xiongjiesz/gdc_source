package com.gdc.gdcmvp.network.errorcode.entity;

import android.text.TextUtils;


/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.network.errorcode.entity
 * @file ErrorCode 错误码对象
 * @date 2018/2/11
 */

public class ErrorCode {

    /**
     * 错误码
     */
    public String errorCode;
    /**
     * 错误描述
     */
    public String desc;

    public ErrorCode() {}

    public ErrorCode(String desc) {
        this.desc = desc;
    }

    public ErrorCode(String errorCode, String desc) {
        this.errorCode = errorCode;
        this.desc = desc;
    }

    @Override
    public boolean equals(Object obj) {
        if(null == obj){
            return false;
        }else if(obj instanceof ErrorCode){
            ErrorCode tmp = (ErrorCode) obj;
            if(!TextUtils.isEmpty(errorCode)){
                return errorCode.equals(tmp.errorCode);
            }else {
                return null == tmp.errorCode;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        if(TextUtils.isEmpty(errorCode)){
            return super.hashCode();
        }else{
            return errorCode.hashCode();
        }
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
