package com.labcontroller.common.utils;

/**
 * @author xietaojie1992
 */
public class StringUtil {
    public static final String EMPTY_STRING = "";

    public static boolean isEmpty(String str) {
        return ((str == null) || (str.length() == 0));
    }

    public static boolean isNotEmpty(String str) {
        return ((str != null) && (str.length() > 0));
    }
}

