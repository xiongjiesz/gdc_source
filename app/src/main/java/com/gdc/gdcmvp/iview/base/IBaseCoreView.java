package com.gdc.gdcmvp.iview.base;

import android.content.Intent;

import java.io.Serializable;


/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.iview.base
 * @file IBaseCoreView Activity之间的跳转
 * @date 2018/3/5
 */

public interface IBaseCoreView {
    void navigate(String host);

    void navigate(String host, Intent intent);

    void navigate(String host, Intent intent, Serializable data);

    void navigateForResult(String host, int requestCode);

    void navigateForResult(String host, Intent intent, int requestCode);

    void navigateForResult(String host, Intent intent, Serializable data, int requestCode);
}
