package com.extlight.extensions.generator.mapper;

import com.extlight.common.base.BaseMapper;
import com.extlight.extensions.generator.model.GenColumn;
import com.extlight.extensions.generator.model.GenTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author MoonlightL
 * @Title: GenTableMapper
 * @ProjectName freedom-boot
 * @Description: 生成器表数据 Mapper
 * @Date 2019/7/8 12:33
 */
public interface GenTableMapper extends BaseMapper<GenTable> {

    /**
     * 查询当前库所有表
     * @param tableName
     * @return
     */
    List<GenTable> selectTableList(@Param("tableName") String tableName);

    /**
     * 查询表信息
     * @param tableName
     * @return
     */
    GenTable selectTableByTableName(@Param("tableName") String tableName);

    /**
     * 查询表字段信息
     * @param tableName
     * @return
     */
    List<GenColumn> selectColumnByTableName(@Param("tableName") String tableName);

    /**
     * 获取所有表名
     * @return
     */
    List<String> selectTableNameList();
}
