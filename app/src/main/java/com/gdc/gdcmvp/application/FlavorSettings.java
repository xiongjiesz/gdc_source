package com.gdc.gdcmvp.application;

/**
 * @author XiongJie
 * @version appVer
 * @Package com.gdc.gdcmvp.application
 * @file
 * @Description:
 * @date 2018/3/16 0:00
 * @since appVer
 */

public class FlavorSettings {
    /**
     * 应用程序默认的背景颜色
     */
    public int defaultPageBgColor = -1;

    private FlavorSettings() {

    }

    private static class FlavorSettingsHolder {
        static FlavorSettings instance = new FlavorSettings();
    }

    public static FlavorSettings getInstance() {
        return FlavorSettingsHolder.instance;
    }
}
