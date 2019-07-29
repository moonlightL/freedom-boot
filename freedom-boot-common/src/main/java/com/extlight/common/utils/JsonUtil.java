package com.extlight.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Author MoonlightL
 * @ClassName: JsonUtil
 * @ProjectName freedom-boot
 * @Description: JSON 工具类
 * @Date 2019/6/28 15:12
 */
public class JsonUtil {

    private JsonUtil() {}

    /**
     * 字符串转对象
     * @param content
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseObj(String content, Class<T> clazz) {
        return JSONObject.parseObject(content, clazz);
    }

    /**
     * 字符串转集合元素
     * @param content
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseList(String content, Class<T> clazz) {
        List<T> list = JSONArray.parseArray(content, clazz);
        return list;
    }

    /**
     * 对象对字符串
     * @param obj
     * @return
     */
    public static String toStr(Object obj, Boolean prettyFormat) {
        return JSONObject.toJSONString(obj, prettyFormat);
    }

    /**
     * 集合元素转字符串
     * @param list
     * @return
     */
    public static String toStr(List<?> list, Boolean prettyFormat) {
        return JSONArray.toJSONString(list, prettyFormat);
    }
}
