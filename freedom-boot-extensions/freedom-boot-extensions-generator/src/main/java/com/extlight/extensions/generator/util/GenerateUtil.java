package com.extlight.extensions.generator.util;

import com.extlight.common.exception.GlobalException;
import com.extlight.common.utils.DateUtil;
import com.extlight.extensions.generator.model.GenColumn;
import com.extlight.extensions.generator.model.GenTable;
import com.extlight.extensions.generator.model.dto.GeneratorParam;
import com.extlight.extensions.generator.model.vo.GenColumnVO;
import com.extlight.extensions.generator.model.vo.GenTableVO;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author MoonlightL
 * @Title: GenerateUtil
 * @ProjectName freedom-boot
 * @Description: 代码生成器工具
 * @Date 2019/7/8 15:26
 */
public class GenerateUtil {

    private static final Map<String, String> DATA_TYPE_MAP;

    static {
        DATA_TYPE_MAP = new HashMap<>();
        DATA_TYPE_MAP.put("bit", "Boolean");
        DATA_TYPE_MAP.put("tinyint", "Integer");
        DATA_TYPE_MAP.put("smallint", "Integer");
        DATA_TYPE_MAP.put("mediumint", "Integer");
        DATA_TYPE_MAP.put("int", "Integer");
        DATA_TYPE_MAP.put("integer", "Integer");
        DATA_TYPE_MAP.put("bigint", "Long");
        DATA_TYPE_MAP.put("float", "Float");
        DATA_TYPE_MAP.put("double", "Double");
        DATA_TYPE_MAP.put("decimal", "BigDecimal");
        DATA_TYPE_MAP.put("char", "String");
        DATA_TYPE_MAP.put("varchar", "String");
        DATA_TYPE_MAP.put("tinytext", "String");
        DATA_TYPE_MAP.put("text", "String");
        DATA_TYPE_MAP.put("mediumtext", "String");
        DATA_TYPE_MAP.put("longtext", "String");
        DATA_TYPE_MAP.put("date", "LocalDateTime");
        DATA_TYPE_MAP.put("datetime", "LocalDateTime");
        DATA_TYPE_MAP.put("timestamp", "LocalDateTime");
    }

    /**
     * 列名转换成 Java 属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "" );
    }

    /**
     * 表名转换成 Java 类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replaceFirst(tablePrefix, "" );
        }
        return columnToJava(tableName);
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String className, String packageName, String moduleName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }

        if (template.contains("DO.java.vm")) {
            return packagePath + "model" + File.separator + className + ".java";
        }

        if (template.contains("DTO.java.vm")) {
            return packagePath + "model" + File.separator + "dto" + File.separator + className + "DTO.java";
        }

        if (template.contains("vo.java.vm")) {
            return packagePath + "model" + File.separator + "vo" + File.separator + className + "vo.java";
        }

        if (template.contains("Mapper.java.vm")) {
            return packagePath + "mapper" + File.separator + className + "Mapper.java";
        }

        if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm")) {
            return packagePath + "web" + File.separator + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains("Mapper.xml.vm")) {
            return "main" + File.separator + "resources" + File.separator + "mybatis" + File.separator + "mapper" + File.separator + moduleName + File.separator + className + "Mapper.xml";
        }

        if (template.contains("listUI.html.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator + moduleName + File.separator + className + File.separator + "listUI.html";
        }

        if (template.contains("saveUI.html.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator + moduleName + File.separator + className + File.separator + "saveUI.html";
        }

        if (template.contains("updateUI.html.vm")) {
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator + moduleName + File.separator + className + File.separator + "updateUI.html";
        }

        return null;
    }


    public static void generateCode(GeneratorParam generatorParam, GenTable table, List<GenColumn> columns, ZipOutputStream zip) {

        boolean hasBigDecimal = false;
        //表信息
        GenTableVO tableVO = table.toVO(GenTableVO.class);
        //表名转换成Java类名
        String className = tableToJava(tableVO.getTableName(), generatorParam.getTablePrefix());
        tableVO.setClassName(className);
        tableVO.setVariableName(StringUtils.uncapitalize(className));

        //列信息
        List<GenColumnVO> columnList = new ArrayList<>();
        for (GenColumn genColumn : columns) {

            GenColumnVO genColumnVO = genColumn.toVO(GenColumnVO.class);

            //列名转换成Java属性名
            String attrName = columnToJava(genColumn.getColumnName());
            genColumnVO.setAttrName(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = DATA_TYPE_MAP.get(genColumnVO.getDataType().toLowerCase());
            genColumnVO.setAttrType(attrType);
            if (!hasBigDecimal && "BigDecimal".equals(attrType)) {
                hasBigDecimal = true;
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(genColumn.getColumnKey()) && tableVO.getPk() == null) {
                tableVO.setPk(genColumnVO);
            }

            columnList.add(genColumnVO);
        }

        tableVO.setColumnList(columnList);

        //没主键，则第一个字段为主键
        if (tableVO.getPk() == null) {
            tableVO.setPk(tableVO.getColumnList().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //封装模板数据
        Map<String, Object> map = new HashMap<>(14);
        map.put("tableName", tableVO.getTableName());
        map.put("tableComment", tableVO.getTableComment());
        map.put("pk", tableVO.getPk());
        map.put("className", tableVO.getClassName());
        map.put("variableName", tableVO.getVariableName());
        map.put("pathName", tableVO.getClassName().toLowerCase());
        map.put("columnList", tableVO.getColumnList());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("packageName", generatorParam.getPackageName());
        map.put("moduleName", generatorParam.getModuleName());
        map.put("author", generatorParam.getAuthor());
        map.put("email", generatorParam.getEmail());
        map.put("projectName", generatorParam.getProjectName());
        map.put("dateTime", DateUtil.toStr(LocalDateTime.now()));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplateList();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(getFileName(template, tableVO.getClassName(), generatorParam.getPackageName(), generatorParam.getModuleName())));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new GlobalException(500, "渲染模板失败，表名：" + tableVO.getTableName());
            }
        }

    }

    public static List<String> getTemplateList() {

        List<String> result = new ArrayList<>();
        result.add("templates/velocity/Controller.java.vm");
        result.add("templates/velocity/DO.java.vm");
        result.add("templates/velocity/DTO.java.vm");
        result.add("templates/velocity/Mapper.java.vm");
        result.add("templates/velocity/Mapper.xml.vm");
        result.add("templates/velocity/Service.java.vm");
        result.add("templates/velocity/ServiceImpl.java.vm");
        result.add("templates/velocity/VO.java.vm");
        result.add("templates/velocity/listUI.html.vm");
        result.add("templates/velocity/saveUI.html.vm");
        result.add("templates/velocity/updateUI.html.vm");

        return result;
    }
}
