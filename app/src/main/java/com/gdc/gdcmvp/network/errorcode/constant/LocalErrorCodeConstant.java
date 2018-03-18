package com.gdc.gdcmvp.network.errorcode.constant;

/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.network.errorcode.constant
 * @file LocalErrorCodeConstant
 * @date 2018/2/12
 */

public interface LocalErrorCodeConstant {
    /**
     * http协议的错误响应结果    例如404，500等
     */
    String ERROR_HTTP               = "101002";
    /**
     * 接口响应数据协议错误    返回数据未按照约定的公共参数协议返回。比如响应结果为空串，格式不正确，respCode，signStr等为空
     */
    String ERROR_RESPONSE_PROTOCAL  = "101004";
}
