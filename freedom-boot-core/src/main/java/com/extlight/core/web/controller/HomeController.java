package com.extlight.core.web.controller;

import com.extlight.common.base.BaseController;
import com.extlight.core.component.ShiroService;
import com.extlight.core.constant.PermissionEnum;
import com.extlight.core.model.SysPermission;
import com.extlight.core.model.vo.SysPermissionVO;
import com.extlight.core.model.vo.SysUserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: MoonlightL
 * @ClassName: HomeController
 * @ProjectName: freedom-boot
 * @Description: 首页控制器
 * @DateTime: 2019-07-05 23:49
 */
@Controller
@RequestMapping("/home")
public class HomeController extends BaseController {

    @Autowired
    private ShiroService shiroService;

    /**
     * 首页
     * @param resultMap
     * @return
     */
    @GetMapping("/index")
    public String index(Map<String, Object> resultMap) {

        Subject subject = SecurityUtils.getSubject();
        SysUserVO sysUserVO = (SysUserVO) subject.getPrincipal();
        if (sysUserVO == null) {
            return "/";
        }

        resultMap.put("menuList", this.parseMenu(sysUserVO));

        return render("index", resultMap);
    }

    /**
     * 首页默认内容
     * @param resultMap
     * @return
     */
    @GetMapping("/dashboard.html")
    public String dashboard(Map<String,Object> resultMap) {
        //TODO

        return render("dashboard", resultMap);
    }

    private List<SysPermissionVO> parseMenu(SysUserVO sysUserVO) {

        // 用户的权限可能会被修改，此处需要重新查询数据库设置权限
        this.shiroService.bindAuthorization(sysUserVO);

        List<SysPermission> permissionList = sysUserVO.getPermissionList();

        List<SysPermissionVO> result = new ArrayList<>();

        // 过滤出父权限
        Map<Long, SysPermissionVO> parentMap = new HashMap<>(50);
        permissionList.stream()
                .filter(i -> i.getResourceType().equals(PermissionEnum.MODULE.getCode()))
                .collect(Collectors.toList())
                .forEach(i -> {
                    SysPermissionVO parent = i.toVoModel();
                    parentMap.put(i.getId(), parent);
                });

        // 将子权限封装到父权限的 children 中
        permissionList.stream()
                .filter(i -> i.getResourceType().equals(PermissionEnum.MENU.getCode()))
                .collect(Collectors.toList())
                .forEach(i -> {
                    SysPermissionVO child = i.toVoModel();
                    SysPermissionVO parent = parentMap.get(child.getPid());
                    if (parent != null) {
                        if (!parent.getChildren().contains(child)) {
                            parent.getChildren().add(child);
                        }

                        if (!result.contains(parent)) {
                            result.add(parent);
                        }
                    }
                });

        return result;
    }

}
