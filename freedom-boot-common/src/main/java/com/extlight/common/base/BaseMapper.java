package com.extlight.common.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author MoonlightL
 * @Title: BaseMapper
 * @ProjectName freedom-boot
 * @Description: mapper 基类
 * @date 2019/5/30 19:36
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
