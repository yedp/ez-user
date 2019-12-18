package com.ydp.ez.user.common.util;

public class StringUtils extends org.apache.commons.lang.StringUtils {

    /**
     * String长度确保不超长
     *
     * @param str
     * @param len
     * @return
     */
    public static String lengthEnsure(String str, int len) {
        if (isBlank(str)) {
            return str;
        }
        if (str.length() <= len) {
            return str;
        } else {
            return str.substring(0, len);
        }
    }
}
