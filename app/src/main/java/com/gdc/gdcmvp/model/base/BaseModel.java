package com.gdc.gdcmvp.model.base;

import com.gdc.gdcmvp.network.http.HttpUtil;


/**
 * @author XiongJie
 * @version appVer
 * @Package com.gdc.gdcmvp.model.base
 * @file
 * @Description:
 * @date 2018/3/12 21:04
 * @since appVer
 */

public abstract class BaseModel {

    private Object[] methods;

    /**
     * 子类在实现Model时需要复写此构造函数，在此注册需要在页面销毁时需要取消的http请求
     * @param methods
     */
    public BaseModel(Object ... methods){
        this.methods=methods;
    }

    /**
     * 取消HTTP连接
     */
    public void destroy(){
        HttpUtil
                .getInstance().cancel(methods);
        onDestory();
    }

    protected void onDestory(){

    }

}
