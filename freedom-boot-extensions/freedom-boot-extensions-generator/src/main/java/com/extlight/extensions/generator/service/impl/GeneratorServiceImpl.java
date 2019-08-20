package com.extlight.extensions.generator.service.impl;

import com.extlight.common.base.BaseMapper;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.base.BaseServiceImpl;
import com.extlight.common.exception.GlobalException;
import com.extlight.extensions.generator.mapper.GenTableMapper;
import com.extlight.extensions.generator.model.GenColumn;
import com.extlight.extensions.generator.model.GenTable;
import com.extlight.extensions.generator.model.dto.GenTableDTO;
import com.extlight.extensions.generator.model.dto.GeneratorParam;
import com.extlight.extensions.generator.model.vo.GenTableVO;
import com.extlight.extensions.generator.service.GeneratorService;
import com.extlight.extensions.generator.util.GenerateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * @Author MoonlightL
 * @ClassName: GeneratorService
 * @ProjectName freedom-boot
 * @Description: 代码生成器 ServiceImpl
 * @Date 2019/7/8 12:47
 */
@Service
public class GeneratorServiceImpl extends BaseServiceImpl<GenTable> implements GeneratorService {

    private static final String IGNORE_TABLE = "flyway_schema_history";

    @Value("${spring.application.name}")
    private String projectName;

    @Autowired
    private GenTableMapper genTableMapper;

    @Override
    public BaseMapper<GenTable> getBaseMapper() {
        return genTableMapper;
    }


    @Override
    protected Example getExample(BaseRequest params) {

        Example example = new Example(GenTable.class);
        Example.Criteria criteria = example.createCriteria();

        GenTableDTO genTableDTO = (GenTableDTO) params;

        if (!StringUtils.isEmpty(genTableDTO.getTableName())) {
            criteria.andLike("tableName", genTableDTO.getTableName() + "%");
        }


        return example;
    }
    @Override
    public List<GenTableVO> findTableList(BaseRequest params) throws GlobalException {

        String tableName = null;
        if (!StringUtils.isEmpty(params.getSearch())) {
            tableName = params.getSearch();
        }

        List<GenTable> list = this.genTableMapper.selectTableList(tableName);
        if (list.isEmpty()) {
            return new ArrayList<>();
        }

        List<GenTableVO> result = new ArrayList<>(list.size());
        list.stream().forEach(i -> {
            if (!IGNORE_TABLE.equals(i.getTableName())) {
                result.add(i.toVoModel());
            }
        });

        return result;
    }

    @Override
    public byte[] generateCode(String[] tableNameArr, GeneratorParam generatorParam) throws GlobalException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        if (StringUtils.isEmpty(generatorParam.getProjectName())) {
            generatorParam.setProjectName(this.projectName);
        }

        try (ZipOutputStream zip = new ZipOutputStream(outputStream)){
            for (String tableName : tableNameArr){
                //查询表信息
                GenTable table = this.queryTable(tableName);
                //查询列信息
                List<GenColumn> columnList = this.queryColumns(tableName);
                //生成代码
                GenerateUtil.generateCode(generatorParam, table, columnList, zip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }

    @Override
    public List<String> findTableNameList() throws GlobalException {
        return this.genTableMapper.selectTableNameList();
    }


    private GenTable queryTable(String tableName) {
        return this.genTableMapper.selectTableByTableName(tableName);
    }

    private List<GenColumn> queryColumns(String tableName) {
        return this.genTableMapper.selectColumnByTableName(tableName);
    }
}
