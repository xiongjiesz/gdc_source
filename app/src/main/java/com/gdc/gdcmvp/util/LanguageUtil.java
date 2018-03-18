package com.gdc.gdcmvp.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.support.v4.util.ArrayMap;
import android.util.DisplayMetrics;

import java.util.Locale;


/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.util
 * @file LanguageUtil
 * @brief 语言环境工具类
 * @date 2018/2/1
 */

public class LanguageUtil {
    private static final String SP_LANG_NAME = "com.gdc.language";
    private static Context context;
    private static Locale                   current   = Locale.SIMPLIFIED_CHINESE;
    private static ArrayMap<Locale, String> localeMap = new ArrayMap<>(2);

    /**
     * 获取手机系统现在的语言环境
     * @return
     */
    public static Locale getSystemLanguage() {
        Locale ret;
        Configuration config = Resources
                .getSystem()
                .getConfiguration();
        ret = config.locale;
        return ret;
    }

    /**
     * 判断是否是简体中文，原因：
     * nexus 6p 简体中文(香港)和繁体中文(香港) http://www.jianshu.com/p/a6d090234d25
     * @return true是简体中文
     */
    public static boolean isHansSystem() {
        boolean ret = false;
        Locale systemLanguage = getSystemLanguage();
        String language = systemLanguage.getLanguage();
        boolean isZh = Locale.SIMPLIFIED_CHINESE
                .getLanguage()
                .equals(language);
        //API 21以上，在Nexus出现繁体中文(香港)和简体中文(香港)
        //通过Locale.getScript()区分
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String script = systemLanguage.getScript();
            //Hans表示简体中文，Hant表示繁体中文
            ret = isZh && "Hans".equals(script);
        }
        if (!ret) {
            String country = systemLanguage.getCountry();
            ret = isZh && Locale.SIMPLIFIED_CHINESE
                    .getCountry()
                    .equals(country);
        }
        return ret;
    }

    /**
     * Locale的对象池
     * @param language
     * @param country
     * @return
     */
    public static Locale getLocaleSingleton(String language, String country) {
        return getLocaleSingleton(language, country, "");
    }

    /**
     * Locale的对象池
     * @param language
     * @param country
     * @param display
     * @return
     */
    public static Locale getLocaleSingleton(String language, String country,
            String display) {
        Locale ret = null;
        int size = localeMap.size();
        Locale cur;
        boolean isExist = false;
        for (int i = 0; i < size; i++) {
            cur = localeMap.keyAt(i);
            if (cur
                    .getLanguage()
                    .equals(language) && cur
                    .getCountry()
                    .equals(country)) {
                isExist = true;
                ret = cur;
            }
        }
        if (!isExist) {
            ret = new Locale(language, country);
        }
        localeMap.put(ret, display);
        return ret;
    }

    /************** 获取和存储app可用的语言 begin **************/
    /**
     * 添加app支持的语言配置
     * @param locale
     * @param display
     */
    public static void putAvailable(Locale locale, String display) {
        localeMap.put(locale, display);
    }

    /**
     * 添加app支持的语言配置
     * @param language
     * @param country
     * @param display
     */
    public static void putAvailable(String language, String country, String
            display) {
        getLocaleSingleton(language, country, display);
    }

    /**
     * 获取app支持的语言配置
     * @return
     */
    public static ArrayMap<Locale, String> getAvailables() {
        return localeMap;
    }
    /*************** 获取和存储app可用的语言 end ***************/

    /************** 获取当前app语言信息 begin **************/
    /**
     * 获取当前app内的语言环境
     * @return String类型
     */
    public static String getCurrentLanguage() {
        return current.toString();
    }

    /**
     * 获取当前app内的语言环境
     * @return Locale类型
     */
    public static Locale getCurrentLocale() {
        return current;
    }

    /**
     * 获取当前app内的语言环境用于展示的文本
     * @return
     */
    public static String getCurrentDisplay() {
        return localeMap.get(current);
    }
    /*************** 获取当前app语言信息 end ***************/

    /************** 读写用户语言的选择配置 begin **************/
    /**
     * 将用户当前的语言选择保存到sp里
     * @param languageStr
     */
    public static void setLangPref(String languageStr) {
        SharedPreferences.Editor edit = getPreferences().edit();
        edit.putString("language", languageStr);
        edit.commit();
    }

    /**
     * 从sp里读取用户的语言选择
     * @return
     */
    public static String getLangPref() {
        return getPreferences().getString("language", null);
    }
    /*************** 读写用户语言的选择配置 end ***************/


    /************** 更新app的语言环境 begin **************/
    /**
     * 更改app的语言环境
     * @param locale
     */
    public static void updateConfiguration(Locale locale) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        // 应用用户选择语言
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);
            context.createConfigurationContext(config).getResources()
                    .getConfiguration().setLocale(locale);
        } else {
            config.locale = locale;
            resources.updateConfiguration(config, dm);
        }
        Locale.setDefault(locale);
    }

    /**
     *用用户选择的语言环境去更改app的语言环境
     */
    public static void updatePreference() {
        updateConfiguration(getLanguage());
    }

    private static SharedPreferences getPreferences() {
        if (null == context) {
            throw new RuntimeException("需要先调用com.sanweidu.TddPay.utils.string.LanguageUtil.init");
        }
        return context.getSharedPreferences(SP_LANG_NAME, Context.MODE_PRIVATE);
    }
    /*************** 更新app的语言环境 end ***************/

    private static Locale getLanguage() {
        String language = getLangPref();

        current = Locale.ENGLISH;
        if (null != language) {
            // 加载用户选择
            if (language.startsWith("zh")) {
                current = Locale.SIMPLIFIED_CHINESE;
            }
        }
        return current;
    }

    public static Context initContext(Context newBase){
        Context ret;
        context = newBase;
        ret = replaceContext(newBase);
        context = ret;
        updatePreference();
        return ret;
    }

    public static Context replaceContext(Context newBase){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            newBase = wrap(newBase, getLanguage());
        }
        return newBase;
    }

    public static ContextWrapper wrap(Context context, Locale newLocale) {
        Resources res = context.getResources();
        Configuration configuration = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(newLocale);

            LocaleList localeList = new LocaleList(newLocale);
            LocaleList.setDefault(localeList);
            configuration.setLocales(localeList);

            context = context.createConfigurationContext(configuration);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(newLocale);
            context = context.createConfigurationContext(configuration);
        } else {
            configuration.locale = newLocale;
            res.updateConfiguration(configuration, res.getDisplayMetrics());
        }
        return new ContextWrapper(context);

    }
}