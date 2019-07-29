package com.extlight.extensions.generator.service;

import com.extlight.common.base.BaseRequest;
import com.extlight.common.base.BaseService;
import com.extlight.common.exception.GlobalException;
import com.extlight.extensions.generator.model.GenTable;
import com.extlight.extensions.generator.model.dto.GeneratorParam;
import com.extlight.extensions.generator.model.vo.GenTableVO;

import java.util.List;

/**
 * @Author MoonlightL
 * @ClassName: GenTableService
 * @ProjectName freedom-boot
 * @Description:
 * @Date 2019/7/8 12:39
 */
public interface GeneratorService extends BaseService<GenTable, GenTableVO> {

    /**
     * 查询当前数据库的所有表
     * @param params
     * @return
     * @throws GlobalException
     */
    List<GenTableVO> findTableList(BaseRequest params) throws GlobalException;

    /**
     * 生成代码
     * @param tableNameArr   表名数组
     * @param generatorParam 
     * @return
     * @throws GlobalException
     */
    byte[] generateCode(String[] tableNameArr, GeneratorParam generatorParam) throws GlobalException;

    /**
     * 获取所有表名
     * @return
     * @throws GlobalException
     */
    List<String> findTableNameList() throws GlobalException;
}
