package com.extlight.core.web.controller;

import com.extlight.common.base.BaseController;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.component.annotation.ActionLog;
import com.extlight.common.constant.ActionEnum;
import com.extlight.common.constant.ModuleEnum;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.model.Result;
import com.extlight.common.utils.ExceptionUtil;
import com.extlight.core.constant.SysUserExceptionEnum;
import com.extlight.core.model.SysUser;
import com.extlight.core.model.dto.SysUserDTO;
import com.extlight.core.model.vo.SysLogVO;
import com.extlight.core.model.vo.SysRoleVO;
import com.extlight.core.model.vo.SysUserVO;
import com.extlight.core.service.SysLogService;
import com.extlight.core.service.SysRoleService;
import com.extlight.core.service.SysUserService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author MoonlightL
 * @ClassName: SysUserController
 * @ProjectName freedom-boot
 * @Description: 系统用户控制器
 * @Date 2019/6/4 17:20
 */
@Controller
@RequestMapping("/core/user")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysLogService sysLogService;

    //#########################################【页面请求】##################################################

    /**
     * 新增页面
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/saveUI.html")
    @RequiresPermissions("core:user:save")
    public String saveUI(Map<String,Object> resultMap) throws GlobalException {
        return render(SAVE_PAGE, resultMap);
    }

    /**
     * 修改页面
     * @param id
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/updateUI/{id}.html")
    @RequiresPermissions("core:user:update")
    public String updateUI(@PathVariable("id") Long id, Map<String,Object> resultMap) throws GlobalException {
        SysUserVO vo = this.sysUserService.getById(id);
        if (vo == null) {
            ExceptionUtil.throwEx(SysUserExceptionEnum.ERROR_USER_NOT_EXIST);
        }

        resultMap.put("vo", vo);
        resultMap.put("readOnly", false);
        return render(UPDATE_PAGE, resultMap);
    }

    /**
     * 列表页面
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/listUI.html")
    @RequiresPermissions("core:user:listUI")
    public String listUI(Map<String,Object> resultMap) throws GlobalException {
        resultMap.put("assignRoleUrl", "/core/user/assignRoleUI/{userId}.html");
        return render(LIST_PAGE, resultMap);
    }

    /**
     * 详情页面
     * @param id
     * @param resultMap
     * @return
     * @throws GlobalException
     */
    @GetMapping("/detailUI/{id}.html")
    @RequiresPermissions("core:user:query")
    public String detailUI(@PathVariable("id") Long id, Map<String,Object> resultMap) throws GlobalException {
        SysUserVO vo = this.sysUserService.getById(id);
        if (vo == null) {
            ExceptionUtil.throwEx(SysUserExceptionEnum.ERROR_USER_NOT_EXIST);
        }

        resultMap.put("vo", vo);
        resultMap.put("readOnly", true);
        return render(DETAIL_PAGE, resultMap);
    }

    //#########################################【AJAX 请求】##################################################

    @PostMapping("/save.json")
    @RequiresPermissions("core:user:save")
    @ResponseBody
    @ActionLog(value="新增用户", moduleName = ModuleEnum.SYSTEM, actionType = ActionEnum.SAVE)
    public Result save(@Validated(BaseRequest.Save.class) SysUserDTO sysUserDTO) throws GlobalException {
        SysUser sysUser = sysUserDTO.toDo(SysUser.class);
        return this.sysUserService.save(sysUser) > 0 ? Result.success() : Result.fail();
    }

    @PostMapping("/remove.json")
    @RequiresPermissions("core:user:remove")
    @ResponseBody
    @ActionLog(value="删除用户", moduleName = ModuleEnum.SYSTEM, actionType = ActionEnum.REMOVE)
    public Result remove(@RequestParam String idStr) throws GlobalException {
        String[] idArr = idStr.split(",");
        int num;
        if (idArr.length == 1) {
            num = this.sysUserService.remove(Long.valueOf(idArr[0]));
        } else {
            String[] idStrArr = idStr.split(",");
            List<Long> idList = new ArrayList<>(idStr.length());
            Arrays.stream(idStrArr).forEach(i -> idList.add(Long.valueOf(i)));
            num = this.sysUserService.removeBatch(idList);
        }
        return num > 0 ? Result.success() : Result.fail();
    }



    @PostMapping("/update.json")
    @RequiresPermissions("core:user:update")
    @ResponseBody
    @ActionLog(value="编辑用户", moduleName = ModuleEnum.SYSTEM, actionType = ActionEnum.UPDATE)
    public Result update(@Validated(BaseRequest.Update.class) SysUserDTO sysUserDTO) throws GlobalException {
        SysUserVO dbData = this.sysUserService.getById(sysUserDTO.getId());
        if (dbData == null) {
            ExceptionUtil.throwEx(SysUserExceptionEnum.ERROR_USER_NOT_EXIST);
        }

        SysUser sysUser = sysUserDTO.toDo(SysUser.class);
        return this.sysUserService.update(sysUser) > 0 ? Result.success() : Result.fail();
    }


    @GetMapping("/list.json")
    @RequiresPermissions("core:user:listUI")
    @ResponseBody
    public Result list(@Validated(BaseRequest.Query.class) SysUserDTO params) throws GlobalException {
        PageInfo<SysUserVO> pageInfo = this.sysUserService.page(params);
        return Result.success(pageInfo);
    }


    //#########################################【特殊方法】##################################################

    @GetMapping("/profile.html")
    public String profileUI(Map<String,Object> resultMap) throws GlobalException {
        Subject subject = SecurityUtils.getSubject();
        SysUserVO sysUserVO = (SysUserVO) subject.getPrincipal();

        SysLogVO sysLogVO = this.sysLogService.queryUserLog(sysUserVO.getId());
        sysUserVO.setLoginCount(sysLogVO.getLoginCount())
                .setLastLoginIp(sysLogVO.getIp())
                .setLastLoginTime(sysLogVO.getCreateTime());

        resultMap.put("currentUser", sysUserVO);
        resultMap.put("updateBasicAction", super.getPrefix() + "/updateBasicInfo.json");
        resultMap.put("updatePwdAction", super.getPrefix() + "/updatePassword.json");

        return render("profile", resultMap);
    }

    /**
     * 分配角色页面
     * @param userId
     * @return
     * @throws GlobalException
     */
    @GetMapping("/assignRoleUI/{userId}.html")
    @RequiresPermissions("core:user:assign:role")
    public String assignRoleUI(@PathVariable Long userId, Map<String,Object> resultMap) throws GlobalException {

        SysUserVO target = this.sysUserService.getById(userId);
        if (target == null) {
            return "404";
        }

        resultMap.put("target", target);

        List<SysRoleVO> allRoleList = this.sysRoleService.list();
        if (allRoleList.isEmpty()) {
            resultMap.put("roleList", new ArrayList<>());
        } else {
            List<SysRoleVO> roleList = this.sysRoleService.findRoleListByUserId(userId);
            List<Long> checkIdList = roleList.stream().map(i -> i.getId()).collect(Collectors.toList());
            allRoleList.stream().forEach(i -> {
                if (checkIdList.contains(i.getId())) {
                    i.setChecked(true);
                }
            });
            resultMap.put("roleList", allRoleList);
        }

        resultMap.put("action", "/core/user/assignRole.json");
        return render("assignRoleUI", resultMap);
    }

    /**
     * 分配角色
     * @param userId
     * @param roleIdStr
     * @return
     * @throws GlobalException
     */
    @PostMapping("/assignRole.json")
    @RequiresPermissions("core:user:assign:role")
    @ResponseBody
    @ActionLog(value="分配角色", moduleName = ModuleEnum.SYSTEM, actionType = ActionEnum.OTHER)
    public Result assignRole(Long userId, String roleIdStr) throws GlobalException {
        return this.sysUserService.asignRole(userId, roleIdStr) > 0 ? Result.success() : Result.fail();
    }

    /**
     * 修改个人头像
     * @param avatar
     * @return
     * @throws GlobalException
     */
    @PostMapping("/updateAvatar.json")
    @ResponseBody
    @ActionLog(value="修改个人头像", moduleName = ModuleEnum.SYSTEM, actionType = ActionEnum.UPDATE)
    public Result updateAvatar(MultipartFile avatar) throws GlobalException {
        if (avatar == null || avatar.isEmpty()) {
            ExceptionUtil.throwEx(SysUserExceptionEnum.ERROR_UPLOAD_AVATAR_IS_EMPTY);
        }

        byte[] bytes = new byte[0];
        try {
            bytes = avatar.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.sysUserService.updateAvatar(avatar.getOriginalFilename(), avatar.getContentType(), bytes) ? Result.success() : Result.fail();
    }

    /**
     * 修改个人资料
     * @param sysUserDTO
     * @return
     * @throws GlobalException
     */
    @PostMapping("/updateBasicInfo.json")
    @ResponseBody
    @ActionLog(value="修改个人资料", moduleName = ModuleEnum.SYSTEM, actionType = ActionEnum.UPDATE)
    public Result updateBasicInfo(@Validated(BaseRequest.Update.class) SysUserDTO sysUserDTO) throws GlobalException {
        SysUserVO sysUserVO = (SysUserVO) SecurityUtils.getSubject().getPrincipal();
        SysUser sysUser = sysUserDTO.toDo(SysUser.class);
        sysUser.setId(sysUserVO.getId());
        return this.sysUserService.update(sysUser) > 0 ? Result.success() : Result.fail();
    }

    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     * @return
     * @throws GlobalException
     */
    @PostMapping("/updatePassword.json")
    @ResponseBody
    @ActionLog(value="修改密码", moduleName = ModuleEnum.SYSTEM, actionType = ActionEnum.OTHER)
    public Result updatePassword(String oldPassword, String newPassword) throws GlobalException {

        SysUserVO sysUserVO = (SysUserVO) SecurityUtils.getSubject().getPrincipal();
        SysUser sysUser = this.sysUserService.findUserByUsername(sysUserVO.getUsername());

        if (!sysUser.getPassword().equals(DigestUtils.md5DigestAsHex(oldPassword.getBytes()))) {
            ExceptionUtil.throwEx(SysUserExceptionEnum.ERROR_OLD_PASSWORD_WRONG);
        }

        sysUser.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
        return this.sysUserService.update(sysUser) > 0 ? Result.success() : Result.fail();
    }
}
