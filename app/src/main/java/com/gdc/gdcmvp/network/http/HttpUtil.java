package com.gdc.gdcmvp.network.http;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.gdc.gdcmvp.constant.Constant;
import com.gdc.gdcmvp.log.LogHelper;
import com.gdc.gdcmvp.network.NetworkText;
import com.gdc.gdcmvp.network.errorcode.ErrorCodeManager;
import com.gdc.gdcmvp.network.errorcode.constant.ErrorCodeConstant;
import com.gdc.gdcmvp.network.http.config.Config;
import com.gdc.gdcmvp.network.http.config.ConfigFactory;
import com.gdc.gdcmvp.network.http.entity.RequestInfo;
import com.gdc.gdcmvp.network.http.entity.ResponseEntity;
import com.gdc.gdcmvp.network.http.exception.RxHttpResponseException;
import com.gdc.gdcmvp.network.http.parser.ParserFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.network.http
 * @file HttpUtil
 * @date 2018/1/25
 */

public class HttpUtil {
    public static final String TAG = "HttpLog";

    private OkHttpClient mClient;

    private Handler mUIHandler;


    private static class HttpUtilHolder {
        static HttpUtil instance = new HttpUtil();
    }

    public static HttpUtil getInstance() {
        return HttpUtilHolder.instance;
    }

    /**
     * 自定义HTTP连接属性（如图片文件上传延长连接超时时间）
     * @param builder
     * @return
     */
    public static HttpUtil customInstance(OkHttpClient.Builder builder) {
        HttpUtil ret = new HttpUtil();
        //替换一下生成请求时的连接对象
        ret.mClient = builder.build();
        return ret;
    }

    /**
     * 构造方法初始化http连接对象
     */
    private HttpUtil() {

        if (Constant.DEBUG_MODEL) {
            //s1:该拦截器用于记录应用中的网络请求的信息。在debug模式下才增加日志打印
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            //s2:创建OkHttpClient对象
            mClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit
                    .SECONDS)//连接超时时间 15*1000
                    .writeTimeout(15, TimeUnit.SECONDS)//上传数据给服务器端的超时,使用上传图片时注意处理
                    .readTimeout(30, TimeUnit.SECONDS)//socket timeout 服务器响应的超时
                    .addInterceptor(logging)//debug模式下增加日志打印
                    .build();
        } else {
            //s1:创建OkHttpClient对象
            mClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit
                    .SECONDS)//连接超时时间 15*1000
                    .writeTimeout(15, TimeUnit.SECONDS)//上传数据给服务器端的超时,使用上传图片时注意处理
                    .readTimeout(30, TimeUnit.SECONDS)//socket timeout 服务器响应的超时
                    .build();
        }

        //s3:创建主线程handler
        Looper mainLooper = Looper.getMainLooper();
        this.mUIHandler = new Handler(mainLooper);
    }

    public Handler getUIHandler() {
        return mUIHandler;
    }

    /**
     * s1:普通HTTP请求
     *
     * @param tmpConfig
     * @param param
     * @param respClazz
     * @param <T>
     * @param <U>
     * @return
     */
    public <T, U extends ResponseEntity> Flowable<RequestInfo> requestFlowable
    (@NonNull final Config tmpConfig, final T param, @NonNull final Class<U>
            respClazz) {
        //RxJava备压策略与操作符
        return Flowable.create(new FlowableOnSubscribe<RequestInfo>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull
                    FlowableEmitter<RequestInfo> emitter) throws Exception {
                // s1:被解除订阅的话没必要再执行代码,如果被观察者被订阅，则执行如下代码
                if (!emitter.isCancelled()) {
                    //s1.1配置请求类型、接口名请求码、请求参数、签名方式等信息
                    Config config = ConfigFactory.handleConfig(tmpConfig);
                    //s1.2创建请求URL
                    String url = ConfigFactory.createURL(config);
                    LogHelper.i(TAG, "URL:" + url);
                    //s1.3创建请求体
                    RequestBody body = ConfigFactory.createBody(config, param);
                    if (null == body) {
                        //@TODO 请求体创建失败的处理
                    } else {
                        //s1.4创建请求对象
                        Request request = ConfigFactory.createHeader(config) // 配置请求头
                                .url(url) // 配置请求地址
                                .post(body) // 配置请求体
                                .tag(config.method) // 设置tag
                                .build();
                        //配置网络请求的操作比较多，需要再检查一次订阅关系是否被解除
                        if (!emitter.isCancelled()) {
                            //s1.5创建请求回调
                            Call newCall = mClient.newCall(request);
                            //s1.5执行请求
                            Response response = newCall.execute();
                            // 网络请求是耗时操作，得到响应后必须再检查一次订阅关系是否被解除
                            if (!emitter.isCancelled()) {
                                //s1.6处理响应
                                if (!response.isSuccessful()) {
                                    //@TODO s1.6.1 响应不成功的处理逻辑
                                } else {
                                    // s1.6.2 响应成功的处理逻辑
                                    boolean hasContent = true;
                                    final String resp = response
                                            .body()
                                            .string();
                                    LogHelper.i(TAG, resp);

                                    if (!hasContent) {
                                        //@TODO 是否有内容的处理
                                    }

                                    //s1.6.3 响应内容解析
                                    final ResponseEntity responseEntity =
                                            ParserFactory.parseResponse(config,
                                                    resp, respClazz, response
                                                            .headers());
                                    final String respCode = responseEntity
                                            .getResponseCode();

                                    //s1.6.4 转义错误码
                                    String info = ErrorCodeManager.escapeCode
                                            (respCode);
                                    responseEntity.setErrorInfo(info);

                                    //解析有一些性能消耗，需要再检查一次订阅关系是否被解除
                                    if (!emitter.isCancelled()) {
                                        //插入处理错误码的钩子
                                        //
                                        // if(null != FlavorSettings.getInstance()
                                        //
                                        //        .hook){
                                        //
                                        // @TODO 错误处理
                                        //                                        }

                                        // 550开头的是本地自定义错误码
                                        if (respCode.startsWith("550")) {
                                            //@TODO 本地自定义错误码处理
                                        } else {
                                            //RxJava发起事件将http请求的数据反馈给观察者
                                            onNext(emitter, new RequestInfo(config
                                                    .method, respCode, info,
                                                    responseEntity));
                                            //结束本次事件
                                            onComplete(emitter);
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }, BackpressureStrategy.LATEST);
    }

    public <T, U extends ResponseEntity> RequestInfo requestSync(@NonNull final
    Config tmpConfig, final T param, @NonNull final Class<U> respClazz) throws
            Exception {
        //s1:适配配置
        Config config = ConfigFactory.handleConfig(tmpConfig);
        //s2:处理url
        String url = ConfigFactory.createURL(config);
        //s3:创建请求体
        RequestBody body = ConfigFactory.createBody(config, param);
        if (null == body) {
            throw new RxHttpResponseException(config.method, "", NetworkText
                    .ERROR_CLIENT_REQUEST_FAILED);
        }
        //s4:生成请求对象
        Request request = ConfigFactory
                .createHeader(config)
                .url(url)
                .post(body)
                .tag(config.method)
                .build();
        //s5:创建请求回调对象
        Call newCall = mClient.newCall(request);
        //s6:执行请求
        Response response = newCall.execute();

        //s7:处理http响应
        if (!response.isSuccessful()) {
            //s7.1 响应不成功的处理
            final String error = Constant.DEBUG_MODEL ?
                    response.code() + ":" + response.message() :
                    ErrorCodeManager.escapeCode(ErrorCodeConstant.ERROR_HTTP);
            throw new RxHttpResponseException(config.method, ErrorCodeConstant.ERROR_HTTP, error);
        } else {
            //s7.2 响应成功的处理
            boolean hasContent = true;
            final String resp = response
                    .body()
                    .string();
            //s8:检查是否有内容
            if (TextUtils.isEmpty(resp)) {
                hasContent = false;
            }
            if (!hasContent) {
                throw new RxHttpResponseException(config.method, ErrorCodeConstant
                        .ERROR_RESPONSE_PROTOCAL, ErrorCodeManager.escapeCode
                        (ErrorCodeConstant.ERROR_RESPONSE_PROTOCAL));
            }

            //s9:解析响应内容
            final ResponseEntity responseEntity = ParserFactory.parseResponse
                    (config, resp, respClazz, response.headers());
            final String respCode = responseEntity.getResponseCode();
            //s10:转义错误码
            String info = ErrorCodeManager.escapeCode(respCode, config
                    .ignoreRespCodeEscape);
            responseEntity.setErrorInfo(info);

            // TODO 插入处理错误码的钩子

            // 550开头的是本地自定义错误码
            if (respCode.startsWith("550")) {
                throw new RxHttpResponseException(config.method, respCode, info);
            } else {
                return new RequestInfo(config.method, respCode, info,
                        responseEntity);
            }
        }
    }

    private static <T> void onNext(FlowableEmitter<T> emitter, @NonNull T value) {
        if (!emitter.isCancelled()) {
            //用事件发射器将HTTP请求响应的数据交给订阅者
            emitter.onNext(value);
        }
    }

    private static <T> void onComplete(FlowableEmitter<T> emitter) {
        if (!emitter.isCancelled()) {
            emitter.onComplete();
        }
    }

    /**
     * 用tag关闭请求
     * @param tags
     */
    public void cancel(Object... tags){
        if(0 == tags.length){
            LogHelper.i(TAG, "tag为空");
            return;
        }

        for (Object object : tags) {
            if (null == object) {
                LogHelper.i(TAG, "tag为空");
                return;
            }
        }

        Dispatcher dispatcher = mClient.dispatcher();
        List<Call> queuedCalls = dispatcher.queuedCalls();
        for (Call call : queuedCalls) {
            cancelTags(call, tags);
        }

        List<Call> runningCalls = dispatcher.runningCalls();
        for (Call call : runningCalls) {
            cancelTags(call, tags);
        }
    }

    /**
     * 取消HTTP请求
     * @param call
     * @param tags
     */
    private void cancelTags(Call call, Object... tags) {
        for (Object object : tags) {
            if (object.equals(call.request()
                    .tag())) {
                call.cancel();
            }
        }
    }

}
