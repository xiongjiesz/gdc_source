package com.gdc.gdcmvp.activity.base;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.gdc.gdcmvp.constant.Constant;
import com.gdc.gdcmvp.iview.base.IBaseCoreView;
import com.gdc.gdcmvp.manager.AppManager;
import com.gdc.gdcmvp.presenter.base.BasePresenter;
import com.gdc.gdcmvp.router.IntentGenerator;
import com.gdc.gdcmvp.router.Router;
import com.gdc.gdcmvp.util.ActivityUtil;
import com.gdc.gdcmvp.util.VersionUtil;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.activity.base
 * @file BaseCoreActivity
 * @date 2018/3/5
 */

public class BaseCoreActivity extends AppCompatActivity implements View.OnClickListener, IBaseCoreView {
    /**
     * 判断页面是否是在后台起来的
     */
    protected            boolean isInBackground       = false;
    /**
     * Activity当前页的数据状态
     */
    private              Bundle  savedInstanceState   = null;
    /**
     * 记录广播接收者是否已经被注销（没有注册）
     */
    private              boolean isReceiverRegistered = false;
    /**
     * 存储Actiity关联的
     */
    private List<BasePresenter>  presenterList        = new ArrayList<BasePresenter>(1);
    /**
     * Fragment的Tag标签
     */
    private List<String>        fragmentTagList = new ArrayList<String>(1);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // s1:设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // s2:设置竖屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        // s3:设置输入法模式 adjust_pan
        getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_ADJUST_PAN);
        // s4:@TODO 加入fragment的处理 隐藏重建的fragment
        hideRestoredFragments(savedInstanceState);
        hideRestoredSupportFragments(savedInstanceState);
        // s5:页面初始化
        init();
    }

    /**
     * 页面初始化
     */
    private void init() {
        //s1:把activity加入堆栈管理器
        initProc();
        //s2:初始化页面入参
        Intent intent = getIntent();
        Serializable data = null;
        if(null != intent){
            data = intent.getSerializableExtra(IntentGenerator.PARAM_DATA);
        }
        initData(intent,data);
        //s3:初始化UI控件
        initUI();
        //s4:初始化界面动画
        initAnim();
        //s5:初始化界面监听
        initListener();
        //s6:初始化广播接收者
        initReceiver();
    }

    /**
     * 让UI层与Presenter层建立联系
     * @param presenter
     */
    public void regPresenter(BasePresenter presenter) {
        presenterList.add(presenter);
    }

    public void unRegPresenter(BasePresenter presenter) {
        presenterList.remove(presenter);
    }

    protected void initProc() {
        //s1:把Activity加入到管理器中
        AppManager
                .getInstance()
                .addActivity(this);

        //s2:设置应用程序的名称
        Constant.VERSION = VersionUtil.getAppVersionName(this);
    }

    protected void initData(Intent intent, Serializable data) {

    }

    protected void initUI() {

    }

    protected void initAnim() {

    }

    protected void initListener() {

    }

    @Override
    public void onClick(View view) {

    }

    protected void initReceiver() {
        IntentFilter it = new IntentFilter();
        it.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        it.addAction(Intent.ACTION_SCREEN_ON);
        it.addAction(Intent.ACTION_SCREEN_OFF);
        it.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        it.addAction(ActivityUtil.ACTION_FINISH_ACTIVITY);
        registerReceiver(mPageCoreReceiver, it);
        setReceiverRegistered(true);
    }

    /**
     * app级的广播事件处理:
     * 1.关闭系统对话框,Activity转入后台的处理
     * 2.屏幕亮起,恢复Activity显示状态
     * 3.屏幕暗下，Activity转入后台的处理
     * 4.网络状态已经改变
     * 5.finish Activity逻辑处理
     */
    private BroadcastReceiver mPageCoreReceiver = new BroadcastReceiver() {
        private static final String SYSTEM_DIALOG_REASON_KEY = "reason";
        private static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(TextUtils.equals(action,Intent.ACTION_CLOSE_SYSTEM_DIALOGS)){
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                //s1:如果是按下了Home键则将页面切换至后台,保存一些数据
                if(!TextUtils.isEmpty(reason) && TextUtils.equals(reason,
                        SYSTEM_DIALOG_REASON_HOME_KEY)){
                    page2Background();
                }
            }else if(TextUtils.equals(action,Intent.ACTION_SCREEN_ON)){
                //s2:屏幕亮起,恢复Activity显示状态
                onRestoreInstanceState(savedInstanceState);
            }else if(TextUtils.equals(action,Intent.ACTION_SCREEN_OFF)){
                //s3:屏幕暗下，Activity转入后台的处理
                page2Background();
            }else if(TextUtils.equals(action,ConnectivityManager.CONNECTIVITY_ACTION)){
                //s4:网络状态已经改变
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService
                        (Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                boolean isConn = false;
                if (info != null && info.isAvailable()) {
                    isConn = true;
                }
                onConnectionChanged(isConn);
            }else if(TextUtils.equals(action,ActivityUtil.ACTION_FINISH_ACTIVITY)){
                //s5:finish Activity逻辑处理
                String activity = intent.getStringExtra(ActivityUtil.PARAM_ACTIVITY);
                if (BaseCoreActivity.this.getClass()
                        .getName()
                        .equals(activity)) {
                    BaseCoreActivity.this.finish();
                }
            }
        }
    };

    private void page2Background() {
        //s1:将Activity切换至后台
        setInBackground(true);
        //s2:将Activity的相关状态数据保存到文件
        onSaveInstanceStateByFile();
    }

    private synchronized void setInBackground(boolean isInBackground) {
        this.isInBackground = isInBackground;
    }

    /**
     * 将数据保存到文件
     */
    protected void onSaveInstanceStateByFile() {

    }

    /**
     * 从文件恢复数据
     */
    protected void onRestoreInstanceStateByFile() {

    }

    /**
     * 网络状态发生改变时的逻辑处理
     * @param isConn
     */
    protected void onConnectionChanged(boolean isConn) {

    }

    @Override
    public void navigate(String host) {
        navigate(host, null);
    }

    @Override
    public void navigate(String host, Intent intent) {
        navigate(host, intent, null);
    }

    @Override
    public void navigate(String host, Intent intent, Serializable data) {
        intent = navigateInternal(host, intent, data);
        startActivity(intent);
    }

    @Override
    public void navigateForResult(String host, int requestCode) {
        navigateForResult(host, null, requestCode);
    }

    @Override
    public void navigateForResult(String host, Intent intent, int requestCode) {
        navigateForResult(host, intent, null, requestCode);
    }

    @Override
    public void navigateForResult(String host, Intent intent, Serializable data,
            int requestCode) {
        intent = navigateInternal(host, intent, data);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 生成页面跳转意图
     *
     * @param host   跳转目标页
     * @param intent 意图
     * @param data   数据
     * @return
     */
    public Intent navigateInternal(String host, Intent intent, Serializable data) {
        intent = Router
                .getInstance()
                .toTarget(this, host, intent, data);
        return intent;
    }

    private synchronized boolean isReceiverRegistered() {
        return isReceiverRegistered;
    }

    private synchronized void setReceiverRegistered(boolean receiverRegistered) {
        isReceiverRegistered = receiverRegistered;
    }

    private void hideRestoredFragments(Bundle savedInstanceState) {
        try {
            hackHideFragmentRestoredFragments(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
            if (null != savedInstanceState) {
                Fragment fragmentByTag;
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                for (String tag : fragmentTagList) {
                    fragmentByTag = getFragmentManager().findFragmentByTag(tag);
                    if (null == fragmentByTag) {
                        fragmentTagList.remove(tag);
                    } else {
                        transaction.hide(fragmentByTag);
                    }
                }
                transaction.commit();
            }
        }
    }

    //@TODO activity launchMode为singleTop singleTask singleInstance 时fragment的处理
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    /**
     * 防止fragment重建导致显示重影，状态异常。
     * 通过反射去获取android.app.FragmentManagerImpl#findFragmentByTag里遍历的frg集合
     * 因为是反射，如果api变动可能导致失败，所以放在hideRestoredFragments里调用，作为补充
     * @param savedInstanceState
     * @throws Exception
     */
    private void hackHideFragmentRestoredFragments(Bundle savedInstanceState)
            throws Exception {

        if (null != savedInstanceState) {
            ArrayList<Fragment> traversal = new ArrayList<>(0);

            FragmentManager manager = getFragmentManager();
            Class<? extends FragmentManager> clazz = manager.getClass();
            Field mAddedFiled = clazz.getDeclaredField("mAdded");
            boolean mAddedAccessible = mAddedFiled.isAccessible();
            mAddedFiled.setAccessible(true);
            traversal.addAll((ArrayList<Fragment>) mAddedFiled.get(manager));
            mAddedFiled.setAccessible(mAddedAccessible);

            Field mActiveFiled = clazz.getDeclaredField("mActive");
            boolean mActiveAccessible = mActiveFiled.isAccessible();
            mActiveFiled.setAccessible(true);
            traversal.addAll((ArrayList<Fragment>) mActiveFiled.get(manager));
            mActiveFiled.setAccessible(mActiveAccessible);

            if (0 < traversal.size()) {
                FragmentTransaction transaction = manager.beginTransaction();
                for (Fragment fragment : traversal) {
                    if (null != fragment) {
                        transaction.hide(fragment);
                    }
                }
                transaction.commit();
            }
            traversal.clear();
        }

    }

    /**
     * 隐藏重建的fragment
     */
    private void hideRestoredSupportFragments(Bundle savedInstanceState) {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        List<android.support.v4.app.Fragment> fragments = manager.getFragments();
        if (null != fragments) {
            android.support.v4.app.FragmentTransaction transaction = manager
                    .beginTransaction();
            for (android.support.v4.app.Fragment frg : fragments) {
                transaction.hide(frg);
            }
            transaction.commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //s1:释放Presenter资源
        for(BasePresenter presenter : presenterList){
            presenter.destory();
        }

        //s2:结束BaseCoreActivity生命周期
        AppManager
                .getInstance()
                .finishActivity(this);

        //s3:取消注册的广播
        if(isReceiverRegistered()){
            unregisterReceiver(mPageCoreReceiver);
            setReceiverRegistered(false);
        }

    }
}
