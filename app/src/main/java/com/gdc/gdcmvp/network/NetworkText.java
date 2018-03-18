package com.gdc.gdcmvp.network;

import com.gdc.gdcmvp.R;
import com.gdc.gdcmvp.application.ApplicationContext;


/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.network
 * @file NetworkText
 * @date 2018/2/12
 */

public interface NetworkText {
    /**
     * 请求请功
     */
    String SUCCESS = ApplicationContext.getString(R.string.framework_request_success);
    /**
     * 无返回数据
     */
    String ERROR_NO_RESPONSE_STR  = ApplicationContext.getString(R.string.framework_response_empty);
    /**
     * 返回数据解析失败
     */
    String ERROR_PARSE_STR_FAILED = ApplicationContext.getString(R.string.framework_response_parse_error);
    /**
     * 客户端请求失败
     */
    String ERROR_CLIENT_REQUEST_FAILED=ApplicationContext.getString(R.string.framework_client_request_failed);
    /**
     * 上传成功!
     */
    String UPLOAD_SUCCESS=ApplicationContext.getString(R.string.framework_upload_success);

}
