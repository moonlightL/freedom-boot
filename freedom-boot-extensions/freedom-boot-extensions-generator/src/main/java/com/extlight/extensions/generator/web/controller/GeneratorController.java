package com.extlight.extensions.generator.web.controller;

import com.extlight.common.base.BaseController;
import com.extlight.common.component.annotation.ActionLog;
import com.extlight.common.constant.ActionEnum;
import com.extlight.common.constant.ModuleEnum;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.model.Result;
import com.extlight.extensions.generator.model.dto.GenTableDTO;
import com.extlight.extensions.generator.model.dto.GeneratorParam;
import com.extlight.extensions.generator.model.vo.GenTableVO;
import com.extlight.extensions.generator.service.GeneratorService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author MoonlightL
 * @ClassName: GeneratorController
 * @ProjectName freedom-boot
 * @Description: 生成器控制器
 * @Date 2019/7/8 12:40
 */
@Controller
@RequestMapping("/generator/table")
public class GeneratorController extends BaseController {

    @Autowired
    private GeneratorService genTableService;

    /**
     * 请求页面
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/listUI.html")
    @RequiresPermissions("generator:table:listUI")
    public String listUI(Map<String,Object> resultMap) throws GlobalException {
        List<String> tableNameList = this.genTableService.findTableNameList();
        resultMap.put("tableNameList", tableNameList);
        resultMap.put("action", "/generator/table/generate.html");

        return render(LIST_PAGE, resultMap);
    }

    @GetMapping("/list.json")
    @RequiresPermissions("generator:table:listUI")
    @ResponseBody
    public Result list(GenTableDTO params) throws GlobalException {
        List<GenTableVO> list = this.genTableService.findTableList(params);
        Page<GenTableVO> page = new Page<>(1, list.size(), false);
        int count = 0;
        if (!list.isEmpty()) {
            page.addAll(list);
            count = this.genTableService.count(params);
        }
        page.setTotal(count);
        return Result.success(new PageInfo<>(page));
    }

    @GetMapping("/generate.html")
    @ActionLog(value = "生成代码", moduleName = ModuleEnum.GENERATOR, actionType = ActionEnum.OTHER)
    public void generate(GeneratorParam generatorParam, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] tableNameArr = generatorParam.getTableName().split(",");
        byte[] data = this.genTableService.generateCode(tableNameArr, generatorParam);
        String fileName = (tableNameArr.length == 1) ? tableNameArr[0] + ".zip" : "generator-code.zip";
        super.download(data, fileName, request, response);
    }
}
