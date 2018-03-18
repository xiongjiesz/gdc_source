package com.gdc.gdcmvp.presenter.base;

import android.support.annotation.CallSuper;

import com.gdc.gdcmvp.model.base.BaseModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * @author XiongJie
 * @version appVer
 * @Package com.gdc.gdcmvp.presenter.base
 * @file
 * @Description:
 * @date 2018/3/12 21:00
 * @since appVer
 */

public abstract class BasePresenter {

    /**
     * 调度控制任务队列 是用于对调度任务进行维护的一个管理类
     * 可以快速解除所有添加的Disposable类 每当我们得到一个Disposable时就调用
     */
    private CompositeDisposable compositeDisposable;

    /**
     * 管理所有的Model
     */
    private List<BaseModel> modelList = new ArrayList<BaseModel>(1);

    public BasePresenter() {
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * 创建订阅者或称观察者
     * 在具体的Presenter中通过调用Model的业务处理方法建立订阅关系.
     * @return
     */
    public RequestInfoSubscriber getSubscriber() {
        RequestInfoSubscriber ret = new RequestInfoSubscriber() {
            @Override
            public void onSuccess(String method, String responseCode, String
                    extraInfo, Object data) {
                BasePresenter.this.onSuccess(method, responseCode, extraInfo, data);
            }

            @Override
            public void onFailure(String method, String errorInfo, String respCode) {
                BasePresenter.this.onFailure(method, errorInfo, respCode);
            }
        };

        //将订阅者（观察者）对象添加到任务调度管理类中进行统一管理，便于解除订阅关系
        compositeDisposable.add(ret);
        return ret;
    }

    /**
     * 将观察者对象添加到任务调度管理器中进行统一管理 便于后期解除订阅关系
     */
    protected void regDisposable(@NonNull Disposable d){
        compositeDisposable.add(d);
    }

    @CallSuper
    protected void onSuccess(String method, String responseCode, String extraInfo,
            Object data) {

    }

    @CallSuper
    protected void onFailure(String method, String errorInfo, String respCode) {

    }

    protected abstract void onDestroy();

    /**
     * 将Presenter与Model关联
     *
     * @param model
     */
    public void regModel(BaseModel model) {
        modelList.add(model);
    }

    public void unRegModel(BaseModel model) {
        modelList.remove(model);
    }

    public void destory() {
        if (null != modelList) {
            for (BaseModel model : modelList) {
                model.destroy();
            }
        }
        //解除所有调度任务的订阅关系
        compositeDisposable.clear();
        onDestroy();
    }

    /**
     * 批量结束观察者与被观察者的订阅事件
     * @param disposables
     */
    public static void batchCancel(Disposable... disposables){
        if(null != disposables){
            for(Disposable disposable : disposables){
                if(null != disposable){
                    disposable.dispose();
                }
            }
        }
    }
}
