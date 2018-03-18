package com.gdc.gdcmvp.util.callback;

import android.view.View;
import android.widget.Toast;

import com.gdc.gdcmvp.application.ApplicationContext;
import com.gdc.gdcmvp.constant.Constant;


/**
 * @author XiongJie
 * @version appVer
 * @Package com.gdc.gdcmvp.util.callback
 * @file
 * @Description:
 * @date 2018/3/16 0:16
 * @since appVer
 */

public abstract class LazyOnClickListener implements View.OnClickListener {
    private long lastClickTime = -1;
    private long mInterval     = 1000;

    @Override
    public void onClick(View v) {
        if (checkPermission() && moreCheck()) {
            onLazyClick(v);
        } else {
            if (Constant.DEBUG_MODEL) {
                Toast
                        .makeText(ApplicationContext.getContext(), "test : 重复点击！",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private boolean checkPermission() {
        if (-1 != lastClickTime) {
            long tmp = System.currentTimeMillis();
            if (mInterval > tmp - lastClickTime) {
                lastClickTime = tmp;
                return false;
            } else {
                lastClickTime = tmp;
                return true;
            }
        } else {
            lastClickTime = System.currentTimeMillis();
            return true;
        }
    }

    public void setInterval(long interval) {
        mInterval = interval;
    }

    protected boolean moreCheck() {
        return true;
    }

    public abstract void onLazyClick(View v);
}
