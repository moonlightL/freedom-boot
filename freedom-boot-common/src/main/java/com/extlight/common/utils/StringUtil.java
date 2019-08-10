package com.extlight.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author MoonlightL
 * @Title: StringUtil
 * @ProjectName: freedom-boot
 * @Description: 字符串工具
 * @DateTime: 2019/8/1 16:15
 */
public class StringUtil {

    private StringUtil() {}

    /**
     * 连接
     * @param array
     * @param separator
     * @return
     */
    public static String join(Object[] array, String separator) {
        return StringUtils.join(array, separator);
    }

    /**
     * 是否空
     * @param value
     * @return
     */
    public static boolean isBlank(String value) {
        return StringUtils.isBlank(value);
    }
}
