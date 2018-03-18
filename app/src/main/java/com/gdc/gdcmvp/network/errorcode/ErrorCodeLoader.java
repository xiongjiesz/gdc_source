package com.gdc.gdcmvp.network.errorcode;

import android.content.Intent;
import android.text.TextUtils;

import com.gdc.gdcmvp.application.ApplicationContext;
import com.gdc.gdcmvp.constant.Constant;
import com.gdc.gdcmvp.network.errorcode.constant.ErrorCodeConstant;
import com.gdc.gdcmvp.network.errorcode.entity.ReqGetErrorCodeDesc;
import com.gdc.gdcmvp.network.errorcode.entity.RespGetErrorCodeDesc;
import com.gdc.gdcmvp.network.http.HttpUtil;
import com.gdc.gdcmvp.network.http.config.Config;
import com.gdc.gdcmvp.network.http.entity.RequestInfo;
import com.gdc.gdcmvp.router.IntentGenerator;


/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.network.errorcode
 * @file ErrorCodeLoader
 * @date 2018/2/24
 */

public class ErrorCodeLoader {
    private static Config              configGetErrorCodeDesc;
    private static ReqGetErrorCodeDesc reqGetErrorCodeDesc;

    private static void initGetErrorCodeDescSync(){
        configGetErrorCodeDesc = new Config(Constant.TYPE_DEFAULT,
                "getErrorCodeDesc","shopMall6013","common");
        configGetErrorCodeDesc.ignoreRespCodeEscape = true;
        reqGetErrorCodeDesc = new ReqGetErrorCodeDesc();
    }

    /**
     * 根据错误码异步获取错误描述信息
     * @param errorCode
     * @return
     */
    public static String getErrorCodeDescSync(String errorCode){
        String ret = null;
        if (null == configGetErrorCodeDesc) {
            initGetErrorCodeDescSync();
        }
        reqGetErrorCodeDesc.errorCode = errorCode;
        try {
            RequestInfo requestInfo = HttpUtil
                    .getInstance()
                    .requestSync(configGetErrorCodeDesc, reqGetErrorCodeDesc,
                            RespGetErrorCodeDesc.class);

            String responseCode = requestInfo.getResponseCode();
            if (TextUtils.equals(ErrorCodeConstant.ERROR_SUCCESS, responseCode)) {
                RespGetErrorCodeDesc resp = (RespGetErrorCodeDesc) requestInfo
                        .getData();
                if (null != resp) {
                    ret = resp.userDescribe;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * 将错误码提示信息更新到本地文件
     * @param code 错误码
     * @param desc 错误码描述信息
     */
    public static void notifySave(String code, String desc) {
        Intent intent = IntentGenerator.toServiceImplicitly(ErrorCodeConstant
                .HOST_ERROR_CODE, new Intent()
                .putExtra(ErrorCodeConstant.PARAM_SAVE_KEY, code)
                .putExtra(ErrorCodeConstant.PARAM_SAVE_VALUE, desc));
        ApplicationContext
                .getContext()
                .startService(intent);
    }

}
