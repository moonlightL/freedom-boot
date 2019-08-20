package com.extlight.common.component.datasource;

/**
 * @Author MoonlightL
 * @ClassName: DynamicDataSourceContextHolder
 * @ProjectName freedom-boot
 * @Description: 数据源类型上下文
 * @Date 2019/5/30 20:31
 */
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置数据源类型
     */
    public static void setDateSoureType(String dsType) {
        CONTEXT_HOLDER.set(dsType);
    }

    /**
     * 获得数据源类型
     */
    public static String getDateSoureType() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清除数据源类型
     */
    public static void clearDateSoureType() {
        CONTEXT_HOLDER.remove();
    }
}
