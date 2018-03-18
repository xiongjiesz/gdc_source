package com.gdc.gdcmvp.network.errorcode;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.gdc.gdcmvp.log.LogHelper;
import com.gdc.gdcmvp.network.NetworkText;
import com.gdc.gdcmvp.network.errorcode.constant.ErrorCodeConstant;
import com.gdc.gdcmvp.network.errorcode.entity.ErrorCode;


/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.network.errorcode
 * @file ErrorCodeManager
 * @date 2018/2/11
 */

public class ErrorCodeManager {
    private static final String DEFAULT_TIP = "系统错误，请重试";

    private ArrayMap<String, ErrorCode> codeMap;

    private ErrorCodeManager() {
        codeMap = new ArrayMap<>();
    }

    public static class ErrorCodeManagerHolder {
        public static ErrorCodeManager instance = new ErrorCodeManager();
    }

    public static ErrorCodeManager getInstance() {
        return ErrorCodeManagerHolder.instance;
    }

    public static String escapeCode(String code) {
        return getInstance().escape(code, false);
    }

    public static String escapeCode(String code, boolean ignoreNotFound) {
        return getInstance().escape(code, ignoreNotFound);
    }

    private String escape(String code, boolean ignoreNotFound) {
        String ret = "";
        if(TextUtils.isEmpty(code)){
            LogHelper.w(ErrorCodeConstant.LOG_TAG,"所要查询的错误码为空");
            return ret;
        }

        if(TextUtils.equals(ErrorCodeConstant.ERROR_SUCCESS,code)){
            // FIXME 多语言
            ret = NetworkText.SUCCESS;
        }else{
            //s1:根据错误码获取错误码对象
            ErrorCode errorCode = codeMap.get(code);
            if(null != errorCode && !TextUtils.isEmpty(errorCode.desc)){
                ret = errorCode.desc;
            }else{
                //@TODO 错误码默认提示
                ret = DEFAULT_TIP;
                //s2:如果本地没有找到错误码描述，则从服务器端查找错误码描述
                if(!ignoreNotFound){
                    // 策略1：请求整个错误码文件
                    // ErrorCodeLoader.notifyUpdate();
                    // 策略2：同步请求单条错误码
                    String tmp = ErrorCodeLoader.getErrorCodeDescSync(code);
                    if (!TextUtils.isEmpty(tmp)) {
                        ret = tmp;
                        // 将错误码提示信息更新到内存
                        codeMap.put(code, new ErrorCode(tmp));
                        // 将错误码提示信息更新到本地文件
                        ErrorCodeLoader.notifySave(code, tmp);
                    }
                }
            }
        }

        return ret;
    }

}
