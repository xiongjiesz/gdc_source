package com.gdc.gdcmvp.network.errorcode.entity;

import com.gdc.gdcmvp.network.http.entity.ResponseEntity;


/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.network.errorcode.entity
 * @file RespGetErrorCodeDesc
 * @date 2018/2/24
 */

public class RespGetErrorCodeDesc extends ResponseEntity {
    /**
     * 错误码
     */
    public String errorCode;

    /**
     * 描述
     */
    public String userDescribe;
}
