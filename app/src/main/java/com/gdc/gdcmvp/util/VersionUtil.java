package com.gdc.gdcmvp.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.util
 * @file VersionUtil
 * @date 2018/3/12
 */

public class VersionUtil {
    /**
     * 获取版本号
     *
     * @param activity
     * @return
     */
    public static int getAppVersionCode(Context activity) {
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo(
                    activity.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 1;
        }
    }

    /**
     * 获取当前程序版本
     *
     * @return
     */
    public static String getAppVersionName(Context activity) {
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo(
                    activity.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
}
