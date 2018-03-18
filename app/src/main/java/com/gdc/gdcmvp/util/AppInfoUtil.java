package com.gdc.gdcmvp.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.gdc.gdcmvp.application.ApplicationContext;


/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.util
 * @file AppInfoUtil
 * @date 2018/2/1
 */

public class AppInfoUtil {

    private static String versionName;
    private static int versionCode = -1;
    private static String uuid;

    public static String getVersionName() {
        if (null == versionName) {
            try {
                PackageInfo info = ApplicationContext
                        .getContext().getPackageManager().getPackageInfo(getPackageName(), 0);
                // 当前应用的版本名称
                versionName = info.versionName;
                versionCode = info.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return versionName;
    }

    public static int getVersionCode() {
        if (-1 == versionCode) {
            try {
                PackageInfo info = ApplicationContext
                        .getContext().getPackageManager().getPackageInfo(getPackageName(), 0);
                // 当前应用的版本名称
                versionName = info.versionName;
                versionCode = info.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return versionCode;
    }

    public static String getPackageName() {
        return ApplicationContext
                .getContext().getPackageName();
    }

}
