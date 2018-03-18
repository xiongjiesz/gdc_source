package com.gdc.gdcmvp.iview.base;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.View;


/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.iview.base
 * @file IStatePageView
 * @date 2018/3/15
 */

public interface IStatePageView {
    /**
     * 设置页面状态为Loading
     */
    void setPageLoading();

    /**
     * 设置页面状态为加载成功
     */
    void setPageSuccess();

    /**
     * 设置页面状态为加载出错
     *
     * @param desc
     * @param onClickListener 可为null，为null是按钮不显示
     */
    void setPageError(CharSequence desc, View.OnClickListener onClickListener);

    /**
     * 设置页面状态为加载出错
     *
     * @param desc
     * @param option 可为空，为空时使用默认的提示文本
     * @param onClickListener
     */
    void setPageError(CharSequence desc, CharSequence option,View.OnClickListener
            onClickListener);

    /**
     * 设置页面为空数据状态
     *
     * @param desc
     * @param option 可为空，为空时使用默认的提示文本
     * @param onClickListener
     */
    void setPageEmpty(CharSequence desc, CharSequence option,View.OnClickListener onClickListener);

    /**
     * 设置页面为空数据状态,默认按钮提示语
     *
     * @param desc
     * @param onClickListener 可为null，为null是按钮不显示
     */
    void setPageEmpty(CharSequence desc ,View.OnClickListener onClickListener);

    /**
     * 设置页面为空数据状态,无按钮
     *
     * @param desc
     */
    void setPageEmpty(CharSequence desc );

    /**
     * 初始化时调用此方法可以修改错误状态的背景图
     *
     * @param drawable
     */
    void resetErrorBackgroud(Drawable drawable);

    /**
     * 初始化时调用此方法可以修改空数据状态的背景图
     *
     * @param drawable
     */
    void resetEmptyBackgroud(Drawable drawable);

    /**
     * 初始化时调用此方法可以修改加载状态的动图
     *
     * @param resId
     */
    void resetLoadingBackgroud(@DrawableRes int resId);
}
