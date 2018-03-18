package com.gdc.gdcmvp.network.http.signature;

import com.gdc.gdcmvp.util.MD5;


/**
 * @author XiongJie
 * @version appVer
 * @package com.gdc.common.network.signature
 * @file SignManager
 * @brief 签名信息管理
 * @date 2017/6/12
 * @since appVer
 */
public class SignManager {
    /**
     * @Fields TYPE_NONE: 对应HttpRequest.init(Object[])里不加密的方式 isEncryption = false
     * (当前不存在)
     */
    public static final int TYPE_NONE = 0;

    /**
     * @Fields TYPE_MD5: 对应HttpRequest.init(Object[])里对业务参数字符串的纯md5加密 isSignByKey
     * = false
     */
    public static final int TYPE_MD5 = 1;

    /**
     * @Fields TYPE_KEY_MD5: 对应HttpRequest.init(Object[])里用rsaKey对业务参数字符串进行md5加密
     * isRSA = false
     */
    public static final int TYPE_KEY_MD5 = 2;

    /**
     * @Fields TYPE_KEY_RSA: 对应HttpRequest.init(Object[])里用rsaKey进行rsa加密 isRSA = true
     */
    public static final int TYPE_KEY_RSA = 3;

    /** @Fields TYPE_RSA: 对应支付sdk里用JniRSAEncrypt进行的rsa加密 */

    public static final int TYPE_RSA = 4;

    /** @Fields TYPE_BASE64: 预留的base64加密 */

    public static final int TYPE_BASE64 = 5;

    public static final int TYPE_WALLET = 6;

    /** @Fields TYPE_QUERY_HTTP_KEY:请求key的接口，对应接口queryHTTPKey */

    public static final int TYPE_QUERY_HTTP_KEY = 7;

    /**
     * @param type 签名类型
     * @param msg  需要签名的数据
     * @return 返回的是一个字符串数组，只有两个元素，第一个是msg(需要签名的数据),第二个是sign(签名之后的数据)
     */
    public static String[] encryptMessage(int type, String msg) {
        String[] ret = null;
        String sign = null;
        //@TODO 签名数据中附带私钥与公钥加密
        //KeyLoader keyStore = KeyLoader.getInstance();
        switch (type) {
        case TYPE_NONE:
            break;
        case TYPE_MD5:
            sign = MD5.MD5EncodeUTF8(msg);
            break;
        case TYPE_KEY_MD5:
            break;
        case TYPE_KEY_RSA:
            break;
        case TYPE_RSA:
            break;
        case TYPE_BASE64:
            break;
        case TYPE_WALLET:
            break;
        case TYPE_QUERY_HTTP_KEY:
            break;
        }
        ret = new String[]{ msg, sign };
        return ret;
    }

    /**
     * 验证签名
     * @param type
     * @param msg
     * @param sign
     * @return
     */
    public static String decryptMessage(int type, String msg, String sign) {
        //KeyLoader keyStore = KeyLoader.getInstance();
        boolean verify = false;
        switch (type){
        case TYPE_NONE:
            break;
        case TYPE_MD5:
            verify = MD5.MD5EncodeUTF8(msg).equals(sign);
            if (!verify) {
                msg = null;
            }
            break;
        case TYPE_KEY_MD5:
            break;
        case TYPE_KEY_RSA:
            break;
        case TYPE_RSA:
            break;
        case TYPE_BASE64:
            break;
        case TYPE_WALLET:
            break;
        case TYPE_QUERY_HTTP_KEY:
            break;
        }
        return msg;
    }
}
