package com.gdc.gdcmvp.activity.base;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdc.gdcmvp.R;
import com.gdc.gdcmvp.application.ApplicationContext;
import com.gdc.gdcmvp.iview.base.IStatePageView;

import pl.droidsonroids.gif.GifImageView;


/**
 * @author xiongjie
 * @version 1.0.0
 * @package com.gdc.skbj.activity.base
 * @file StatePage
 * @brief
 * 显示页面各种状态的布局:
 * s1:数据加载中
 * s2:数据加载为空
 * s3:数据为空与按钮（重新加载）
 * s4:网络断开连接
 *
 * @date 2017/5/17
 * @since 1.0.0
 */

public class StatePage extends FrameLayout implements IStatePageView {
    /*********** 状态提示信息 by XiongJie begin 2017/5/19 ***********/
    /**
     * 数据加载中提示
     */
    public String loadingDesc = ApplicationContext.getString(R.string
            .abnormal_loading_desc);
    /**
     * 数据加载为空提示
     */
    public String emptyDesc   = ApplicationContext.getString(R.string
            .abnormal_empty_desc);
    /**
     * 数据为空操作提示
     */
    public String emptyTips   = ApplicationContext.getString(R.string
            .abnormal_empty_tips);
    /**
     * 网络出错提示
     */
    public String errorDesc   = ApplicationContext.getString(R.string
            .abnormal_error_desc);
    /**
     * 操作提示
     */
    public String errorOption = ApplicationContext.getString(R.string
            .abnormal_error_option);

    /*************** 状态提示信息 by XiongJie end ***************/
    /*********** 页面控件 by XiongJie begin 2017/5/19 ***********/
    /**
     * 根布局
     */
    private View         rootView;
    /**
     * 根节点
     */
    public  LinearLayout ll_state_page_root;
    /**
     * 图片
     */
    public  GifImageView iv_state_page_pic;
    /**
     * 网络提示
     */
    public  TextView     tv_state_page_desc;
    /**
     * 操作提示
     */
    public  TextView     tv_state_page_tips;
    /**
     * 操作按钮
     */
    public  Button       btn_state_page_option;

    /*************** 页面控件 by XiongJie end ***************/

    /*********** 数据加载资源 by XiongJie begin 2017/5/19 ***********/
    /**
     * 数据加载中 转圈动画资源
     */
    public int    loadingResId = R.mipmap.gif_state_loading;
    /**
     *加载数据为空资源
     */
    public int    emptyResId   = R.mipmap.img_state_empty;
    /**
     *加载数据出错资源
     */
    public int    errorResId   = R.mipmap.img_state_empty;
    /**
     * 空图片对象 设置图片时使用
     */
    private Drawable emptyDrawable;
    /**
     * 错误图片对象 设置图片时使用
     */
    private Drawable errorDrawable;
    /*************** 数据加载资源 by XiongJie end ***************/
    /**
     * 加载图片的宽
     */
    private int      loadingW;
    /**
     * 加载图片的高
     */
    private int      loadingH;
    private int      otherW;
    private int      otherH;

    /**
     * 构造方法1
     * @param context
     */
    public StatePage(@NonNull Context context) {
        this(context, null);
    }

    /**
     * 构造方法2
     * @param context
     * @param attrs
     */
    public StatePage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
        initUI(context);
    }

    /**
     * 初始化加载框的宽高，以及其他宽高
     */
    private void initData() {
        loadingW = dpToPx(106);
        loadingH = dpToPx(106);
        otherW = dpToPx(211);
        otherH = dpToPx(144);
    }

    private void initUI(Context context) {
        rootView = LayoutInflater
                .from(context).inflate(R.layout.layout_state_page, this);
        this.ll_state_page_root = (LinearLayout) rootView.findViewById(R.id.ll_state_page_root);
        this.iv_state_page_pic = (GifImageView) rootView.findViewById(R.id.iv_state_page_pic);
        this.tv_state_page_desc = (TextView) rootView.findViewById(R.id.tv_state_page_desc);
        this.tv_state_page_tips = (TextView) rootView.findViewById(R.id.tv_state_page_tips);
        this.btn_state_page_option = (Button) rootView.findViewById(R.id.btn_state_page_option);
    }

    @Override
    public void setPageLoading() {
        ViewGroup.LayoutParams lp = iv_state_page_pic.getLayoutParams();
        lp.width = loadingW;
        lp.height = loadingH;
        iv_state_page_pic.setLayoutParams(lp);
        iv_state_page_pic.setImageResource(loadingResId);

        this.setVisibility(View.VISIBLE);
        tv_state_page_desc.setVisibility(View.GONE);
        btn_state_page_option.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setPageSuccess() {
        this.setVisibility(View.GONE);
    }

    @Override
    public void setPageError(CharSequence desc, OnClickListener onClickListener) {
        if (TextUtils.isEmpty(desc)) {
            tv_state_page_desc.setText(errorDesc);
        } else {
            tv_state_page_desc.setText(desc);
        }
        if(onClickListener!=null){
            btn_state_page_option.setText(errorOption);
            btn_state_page_option.setVisibility(View.VISIBLE);
            btn_state_page_option.setOnClickListener(onClickListener);
        }else{
            btn_state_page_option.setVisibility(View.INVISIBLE);
        }
        setImage(errorResId);

        this.setVisibility(View.VISIBLE);
        tv_state_page_desc.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPageError(CharSequence desc, CharSequence option,
            OnClickListener onClickListener) {
        if (TextUtils.isEmpty(desc)) {
            tv_state_page_desc.setText(errorDesc);
        } else {
            tv_state_page_desc.setText(desc);
        }
        if(TextUtils.isEmpty(option)){
            btn_state_page_option.setText(errorOption);
        }else{
            btn_state_page_option.setText(option);
        }
        btn_state_page_option.setOnClickListener(onClickListener);
        setImage(errorResId);

        this.setVisibility(View.VISIBLE);
        tv_state_page_desc.setVisibility(View.VISIBLE);
        btn_state_page_option.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPageEmpty(CharSequence desc) {
        if (TextUtils.isEmpty(desc)) {
            tv_state_page_desc.setText(emptyDesc);
        } else {
            tv_state_page_desc.setText(desc);
        }
        setImage(emptyResId);
        this.setVisibility(View.VISIBLE);
        tv_state_page_desc.setVisibility(View.VISIBLE);
        btn_state_page_option.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setPageEmpty(CharSequence desc, OnClickListener onClickListener) {
        if (TextUtils.isEmpty(desc)) {
            tv_state_page_desc.setText(emptyDesc);
        } else {
            tv_state_page_desc.setText(desc);
        }
        btn_state_page_option.setOnClickListener(onClickListener);
        if (onClickListener != null) {
            btn_state_page_option.setText(emptyTips);
            btn_state_page_option.setVisibility(View.VISIBLE);
        } else {
            btn_state_page_option.setVisibility(View.INVISIBLE);
        }
        setImage(emptyResId);
        this.setVisibility(View.VISIBLE);
        tv_state_page_desc.setVisibility(View.VISIBLE);
    }


    @Override
    public void setPageEmpty(CharSequence desc, CharSequence option, OnClickListener onClickListener) {
        if (TextUtils.isEmpty(desc)) {
            tv_state_page_desc.setText(emptyDesc);
        } else {
            tv_state_page_desc.setText(desc);
        }

        if (TextUtils.isEmpty(option)) {
            btn_state_page_option.setText(emptyTips);
        } else {
            btn_state_page_option.setText(option);
        }
        btn_state_page_option.setOnClickListener(onClickListener);
        setImage(emptyResId);

        this.setVisibility(View.VISIBLE);
        tv_state_page_desc.setVisibility(View.VISIBLE);
        btn_state_page_option.setVisibility(View.VISIBLE);
    }

    @Override
    public void resetErrorBackgroud(Drawable drawable) {
        errorDrawable = drawable;
    }

    @Override
    public void resetEmptyBackgroud(Drawable drawable) {
        emptyDrawable = drawable;
    }

    @Override
    public void resetLoadingBackgroud(int resId) {
        loadingResId = resId;
    }

    private void setImage(int resId){
        ViewGroup.LayoutParams lp = iv_state_page_pic.getLayoutParams();
        lp.width = otherW;
        lp.height = otherH;
        iv_state_page_pic.setLayoutParams(lp);
        if (resId == emptyResId) {
            if (null == emptyDrawable) {
                emptyDrawable = new BitmapDrawable(BitmapFactory.decodeResource
                        (getContext().getResources(), emptyResId));
            }
            iv_state_page_pic.setImageDrawable(emptyDrawable);
        } else if (resId == errorResId) {
            if (null == errorDrawable) {
                errorDrawable = new BitmapDrawable(BitmapFactory.decodeResource
                        (getContext().getResources(), errorResId));
            }
            iv_state_page_pic.setImageDrawable(errorDrawable);
        }
    }

    /**
     * 将dp转换为px
     * @param dps
     * @return
     */
    public static int dpToPx(int dps) {
        return Math.round(ApplicationContext
                .getContext()
                .getResources()
                .getDisplayMetrics().density * dps);
    }
}
