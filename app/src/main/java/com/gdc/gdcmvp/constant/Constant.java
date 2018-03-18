package com.gdc.gdcmvp.constant;

/**
 * @author XiongJie
 * @version %I%, %G%
 * @package com.gdc.gdcmvp.constant
 * @file Constant
 * @date 2018/2/1
 */

public class Constant {
    /**
     * 1001 耕读村框架  1002 智慧村落 1003 耕读文化
     */
    public static final int APP_ID = 1001;
    /**
     * 测试模式标记（true：测试环境； false：正式环境）
     */
    public static boolean DEBUG_MODEL = true;
    /**
     * DEFAULT: 默认请求方式 [对应startRequest]
     */
    public static final int    TYPE_DEFAULT        = 1001;
    /**
     * XML: 接口数据类型是XML
     */
    public static final int    DATA_XML            = 2001;
    /**
     * JSON: 接口数据类型是JSON
     */
    public static final int    DATA_JSON           = 2002;
    /**
     * 游客模式会员账号
     */
    public static final String VISITORACCOUNT_LOGO = "visitorAccount";
    /**
     * OS_NAME : 1002：android各种请求过程中如果需要版本类型可以使用此常量
     */
    public static final String OS_NAME             = "1002";
    /**
     * 版本说明
     */
    public static String VERSION;
}
