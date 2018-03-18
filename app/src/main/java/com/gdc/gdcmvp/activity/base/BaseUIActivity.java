package com.gdc.gdcmvp.activity.base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gdc.gdcmvp.R;
import com.gdc.gdcmvp.application.ApplicationContext;
import com.gdc.gdcmvp.application.FlavorSettings;
import com.gdc.gdcmvp.iview.base.IBaseUIView;
import com.gdc.gdcmvp.log.LogHelper;
import com.gdc.gdcmvp.util.ToastUtil;
import com.gdc.gdcmvp.util.callback.LazyOnClickListener;

import java.io.Serializable;


/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.activity.base
 * @file BaseUIActivity
 * @date 2018/3/15
 */

public class BaseUIActivity extends BaseCoreActivity implements IBaseUIView {
    private static final String TAG = "BaseUIActivity";
    /**
     * 设备连接提示
     */
    private ViewStub       vs_base_ui_bottom;
    /**
     * 页面内容区域，用于填充Activity的布局
     */
    private FrameLayout    fl_base_ui_content;
    /**
     * 页面状态区域，用于显示页面加载成功、失败、等状态
     */
    private FrameLayout    fl_base_ui_state;
    /**
     * 页面标题
     */
    private TextView       tv_title_subject;
    /**
     * 标题栏
     */
    private Toolbar        tb_title_view;
    /**
     * 网络连接状态
     */
    private TextView       tv_base_ui_connection;
    /**
     * 页面根布局
     */
    private RelativeLayout rl_base_ui_root;

    /**
     * 如果网络没有连接时，通过此属性去设置无网络控件的显示与隐藏
     */
    private boolean isOpen = true;

    /**
     * 页面各种状态
     */
    private StatePage      statePage;

    /**
     * 菜单
     */
    private MenuItem action_main;
    /**
     * 子菜单
     */
    private MenuItem action_sub;

    /*************** toolbar by XiongJie end ***************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initProc() {
        super.initProc();
    }

    @Override
    protected void initReceiver() {
        super.initReceiver();
    }

    @Override
    protected void initData(Intent intent, Serializable data) {
        super.initData(intent, data);
    }

    @Override
    protected void initAnim() {
        super.initAnim();
    }

    @Override
    protected void initUI() {
        super.initUI();
        setContentView(R.layout.activity_base_ui);
        rl_base_ui_root = (RelativeLayout) findViewById(R.id.rl_base_ui_root);
        vs_base_ui_bottom = (ViewStub) findViewById(R.id.vs_base_ui_bottom);
        fl_base_ui_content = (FrameLayout) findViewById(R.id.fl_base_ui_content);
        fl_base_ui_state = (FrameLayout) findViewById(R.id.fl_base_ui_state);
        tv_base_ui_connection = (TextView) findViewById(R.id.tv_base_ui_connection);
        tb_title_view = (Toolbar) findViewById(R.id.tb_title_view);
        tv_title_subject = (TextView) findViewById(R.id.tv_title_subject);
        if (-1 != FlavorSettings.getInstance().defaultPageBgColor) {
            rl_base_ui_root.setBackgroundColor(FlavorSettings.getInstance()
                    .defaultPageBgColor);
        }
        initToolbar();
    }

    protected void initToolbar() {
        // 集中处理toolbar不分结构，以免太分散
        // 如果只是简单设置标题可以在initUI里设置，如果设置比较复杂，需要继承initToolbar，在其中集中处理

        tb_title_view.setNavigationOnClickListener(new LazyOnClickListener() {
            @Override
            public void onLazyClick(View v) {
                onBackPressed();
            }
        });

        tb_title_view.inflateMenu(R.menu.menu_title);

        action_main = tb_title_view.getMenu()
                .findItem(R.id.action_main)
                .setVisible(false);
        action_sub = tb_title_view.getMenu()
                .findItem(R.id.action_sub)
                .setVisible(false);
    }


    @Override
    public void setPageSuccess() {
        LogHelper.i(TAG, "setPageSuccess");
        checkStatePage();
        statePage.setPageSuccess();
        fl_base_ui_state.setVisibility(View.GONE);
    }

    @Override
    public void setPageLoading() {
        LogHelper.i(TAG, "setPageLoading");
        checkStatePage();
        statePage.setPageLoading();
        fl_base_ui_state.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPageError(CharSequence desc, View.OnClickListener onClickListener) {
        LogHelper.i(TAG, "setPageError");
        checkStatePage();
        statePage.setPageError(desc, onClickListener);
        fl_base_ui_state.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPageError(CharSequence desc, CharSequence option, View
            .OnClickListener onClickListener) {
        LogHelper.i(TAG, "setPageError");
        checkStatePage();
        statePage.setPageError(desc, option, onClickListener);
        fl_base_ui_state.setVisibility(View.VISIBLE);
    }

    /**
     * 设置页面为请求到的数据为空的UI
     */
    @Override
    public void setPageEmpty(CharSequence desc) {
        LogHelper.i(TAG, "setPageEmpty");
        checkStatePage();
        statePage.setPageEmpty(desc);
        fl_base_ui_state.setVisibility(View.VISIBLE);
    }

    /**
     * 设置页面为请求到的数据为空的UI
     */
    @Override
    public void setPageEmpty(CharSequence desc, View.OnClickListener
            onClickListener) {
        LogHelper.i(TAG, "setPageEmpty");
        checkStatePage();
        statePage.setPageEmpty(desc, onClickListener);
        fl_base_ui_state.setVisibility(View.VISIBLE);
    }

    /**
     * 设置页面为请求到的数据为空的UI
     */
    @Override
    public void setPageEmpty(CharSequence desc, CharSequence option, View.OnClickListener onClickListener) {
        LogHelper.i(TAG, "setPageEmpty");
        checkStatePage();
        statePage.setPageEmpty(desc, option, onClickListener);
        fl_base_ui_state.setVisibility(View.VISIBLE);
    }

    @Override
    public void resetErrorBackgroud(Drawable drawable) {
        checkStatePage();
        statePage.resetErrorBackgroud(drawable);
    }

    @Override
    public void resetEmptyBackgroud(Drawable drawable) {
        checkStatePage();
        statePage.resetEmptyBackgroud(drawable);
    }

    @Override
    public void resetLoadingBackgroud(int resId) {
        checkStatePage();
        statePage.resetLoadingBackgroud(resId);
    }

    private synchronized void checkStatePage(){
        if (null == statePage) {
            statePage = new StatePage(this);
            fl_base_ui_state.addView(statePage);
        }
    }

    public void resetStatePageParent(ViewGroup parent, int index) {
        checkStatePage();
        fl_base_ui_state.removeView(statePage);
        parent.addView(statePage, index);
    }

    @Override
    protected void onConnectionChanged(boolean isConn) {
        super.onConnectionChanged(isConn);
        tv_base_ui_connection.setVisibility(View.GONE);
        if (isConn) {

        } else {
            if (isOpen) {
                ToastUtil.show(ApplicationContext.getString(R.string.no_internet_connection));
            } else {

            }
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
