package com.gdc.gdcmvp.util;

import java.security.MessageDigest;


/**
 * @author XiongJie
 * @version appVer
 * @package com.gdc.common.util
 * @file MD5Util
 * @brief
 * @date 2017/6/12
 * @since appVer
 */
public class MD5 {

    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 生成MD5加密签名字符串
     * @param origin
     * @return
     */
    public static String MD5EncodeUTF8(String origin) {
        String resultString = null;

        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes("UTF-8")));
        } catch (Exception ex) {

        }
        return resultString;
    }
}
