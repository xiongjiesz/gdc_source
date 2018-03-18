package com.gdc.gdcmvp.presenter.base;

import com.gdc.gdcmvp.log.LogHelper;
import com.gdc.gdcmvp.network.http.entity.RequestInfo;
import com.gdc.gdcmvp.network.http.exception.RxHttpResponseException;

import io.reactivex.subscribers.DisposableSubscriber;


/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.presenter.base
 * @file RequestInfoSubscriber
 *       1.订阅关系管理者 观察者()与被观察者(HttpUtil)之间建立订阅关系
 *       RequestInfo类不能简单的理解数请求数据对象，而要理解为Http请求与响应过程的一个数据载体
 *       2.订阅关系解除
 * @date 2018/3/14
 */

public abstract class RequestInfoSubscriber extends DisposableSubscriber<RequestInfo> {
    private static final String TAG = "RequestInfoSubscriber";

    @Override
    public void onNext(RequestInfo requestInfo) {
        String method = requestInfo.getMethod();
        String responseCode = requestInfo.getResponseCode();
        String extraInfo = requestInfo.getExtraInfo();
        Object data = requestInfo.getData();
        this.cancel();
        onSuccess(method, responseCode, extraInfo, data);
    }

    @Override
    public void onError(Throwable t) {
        if (t instanceof RxHttpResponseException) {
            RxHttpResponseException ex = (RxHttpResponseException) t;
            String method = ex.getMethod();
            String errorInfo = ex.getErrorInfo();
            String respCode = ex.getRespCode();
            this.cancel();
            onFailure(method, errorInfo, respCode);
        } else {
            t.printStackTrace();
            LogHelper.w(TAG, "not RxHttpResponseException : " + t.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(String method, String responseCode, String extraInfo, Object data);

    public abstract void onFailure(String method, String errorInfo, String respCode);

}
