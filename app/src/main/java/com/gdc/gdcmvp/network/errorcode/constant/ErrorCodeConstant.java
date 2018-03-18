package com.gdc.gdcmvp.network.errorcode.constant;

/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.network.errorcode.constant
 * @file ErrorCodeConstant
 * @date 2018/2/11
 */

public interface ErrorCodeConstant extends LocalErrorCodeConstant,
                                           HttpSystemErrorCodeConstant {

    String LOG_TAG = "ErrorCode";
    /**
     * 错误码加载服务
     */
    String HOST_ERROR_CODE   = "com.gdc.gdcmvp.network.errorcode.service.ErrorCodeService";
    /**
     * 错误码存储key
     */
    String PARAM_SAVE_KEY      = "param_save_key";
    /**
     * 错误码存储value
     */
    String PARAM_SAVE_VALUE      = "param_save_value";
}
