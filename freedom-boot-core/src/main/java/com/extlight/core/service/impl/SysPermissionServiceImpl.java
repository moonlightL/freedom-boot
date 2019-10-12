package com.extlight.core.service.impl;

import com.extlight.common.base.BaseMapper;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.base.BaseServiceImpl;
import com.extlight.common.component.module.Module;
import com.extlight.common.component.module.ModuleFactory;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.exception.GlobalExceptionEnum;
import com.extlight.common.utils.ExceptionUtil;
import com.extlight.core.constant.PerTypeEnum;
import com.extlight.core.constant.ResourceTypeEnum;
import com.extlight.core.constant.SysPermissionExceptionEnum;
import com.extlight.core.mapper.SysPermissionMapper;
import com.extlight.core.model.SysPermission;
import com.extlight.core.model.dto.SysPermissionDTO;
import com.extlight.core.model.vo.SysPermissionVO;
import com.extlight.core.model.vo.TreeNode;
import com.extlight.core.service.SysPermissionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author MoonlightL
 * @ClassName: SysPermissionServiceImpl
 * @ProjectName freedom-boot
 * @Description:
 * @Date 2019/7/1 17:23
 */
@Service
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermission> implements SysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public BaseMapper<SysPermission> getBaseMapper() {
        return sysPermissionMapper;
    }

    @Override
    protected Example getExample(BaseRequest params) {

        Example example = new Example(SysPermission.class);
        if (params != null) {
            Example.Criteria criteria = example.createCriteria();

            SysPermissionDTO sysPermissionDTO = (SysPermissionDTO) params;

            if (sysPermissionDTO.getPid() != null) {
                criteria.andEqualTo("pid", sysPermissionDTO.getPid());
            }
        }

        return example;
    }


    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public int save(SysPermission sysPermission) throws GlobalException {
        if (!sysPermission.getPerType().equals(PerTypeEnum.MODULE.getCode())) {
            if (sysPermission.getPid() == null || sysPermission.getPid() == 0) {
                ExceptionUtil.throwEx(GlobalExceptionEnum.ERROR_PARAM);
            }

            SysPermission parent = super.getById(sysPermission.getPid());
            if (parent == null) {
                ExceptionUtil.throwEx(SysPermissionExceptionEnum.ERROR_PERMISSION_NOT_EXIST);
            }

        } else {
            sysPermission.setPerCode("");
        }

        return super.save(sysPermission);
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public int remove(Long id) throws GlobalException {
        SysPermission sysPermission = super.getById(id);
        if (sysPermission == null) {
            ExceptionUtil.throwEx(SysPermissionExceptionEnum.ERROR_PERMISSION_NOT_EXIST);
        }

        if (!sysPermission.getResourceType().equals(ResourceTypeEnum.BUSINESS.getCode())) {
            ExceptionUtil.throwEx(SysPermissionExceptionEnum.ERROR_PERMISSION_CANNOT_REMOVE);
        }

        int result = super.remove(id);
        return result;
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public int removeBatch(List<Long> idList) throws GlobalException {

        List<Long> data = new ArrayList<>(idList.size());
        for (Long permissionId : idList) {
            SysPermission sysPermission = super.getById(permissionId);
            if (sysPermission != null && sysPermission.getResourceType().equals(ResourceTypeEnum.BUSINESS.getCode())) {
                data.add(permissionId);
            }
        }

        if (data.isEmpty()) {
            return 0;
        }
        
        int result = super.removeBatch(data);
        return result;
    }


    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public int update(SysPermission sysPermission) throws GlobalException {
        int result = super.update(sysPermission);
        return result;
    }


    @Override
    public PageInfo<SysPermission> pageAll(BaseRequest params) throws GlobalException {
        SysPermissionDTO sysPermissionDTO = (SysPermissionDTO) params;
        if (sysPermissionDTO.getPid() == null) {
            sysPermissionDTO.setPageNum(0);
            sysPermissionDTO.setSortOrder("ASC");
            return super.pageAll(sysPermissionDTO);
        }

        return this.getPermissionListByModuleId(sysPermissionDTO);
    }

    @Override
    public List<SysPermissionVO> findHierarchyPermissionList() throws GlobalException {
        List<SysPermissionVO> list = this.sysPermissionMapper.selectHierarchyPermissionList();
        return list;
    }

    @Override
    public List<SysPermission> findPermissionListByUserId(Long userId) throws GlobalException {
        return this.sysPermissionMapper.selectByUserId(userId);
    }

    @Override
    public List<TreeNode> findPermissionNodesByRoleId(Long roleId) throws GlobalException {
        // 所有权限
        List<SysPermission> allPermissionList = this.sysPermissionMapper.selectAll();
        if (allPermissionList.isEmpty()) {
            return new ArrayList<>();
        }

        // 当前 roleId 的权限
        List<SysPermission> checkedPermissionList = this.sysPermissionMapper.selectByRoleId(roleId);
        List<Long> checkRoleIdList = checkedPermissionList.stream().map(i -> i.getId()).collect(Collectors.toList());

        List<TreeNode> treeNodeList = new ArrayList<>();
        allPermissionList.stream().forEach(i -> {
            TreeNode treeNode = new TreeNode();
            BeanUtils.copyProperties(i, treeNode);
            treeNode.setChecked(checkRoleIdList.contains(i.getId()));
            treeNode.setOpen(true);
            treeNodeList.add(treeNode);
        });

        return treeNodeList;
    }

    @Override
    public List<SysPermission> findCommonButtonList(String url) throws GlobalException {
        return this.sysPermissionMapper.selectCommonButtonList(url);
    }

    @Override
    public List<SysPermissionVO> findModuleList() throws GlobalException {
        // 所有模块列表
        List<SysPermission> sysPermissionList = this.sysPermissionMapper.selectListByPerType(PerTypeEnum.MODULE.getCode());
        // 已加载的模块列表
        List<Module> moduleList = ModuleFactory.getModuleList();
        List<String> moduleCodeList = moduleList.stream().map(i -> i.getCode()).collect(Collectors.toList());

        List<SysPermissionVO> result = new ArrayList<>(sysPermissionList.size());
        // 判断加载状态
        sysPermissionList.forEach(i -> {
            SysPermissionVO sysPermissionVO = i.toVoModel();
            sysPermissionVO.setIsLoad(moduleCodeList.contains(i.getModuleCode()));
            result.add(sysPermissionVO);

        });

        return result;
    }

    @Override
    public SysPermission findPermissionByUrl(String url) throws GlobalException {
        return this.sysPermissionMapper.selectByUrl(url);
    }

    private PageInfo<SysPermission> getPermissionListByModuleId(SysPermissionDTO sysPermissionDTO) throws GlobalException {

        SysPermission module = super.getById(sysPermissionDTO.getPid());
        if (module == null) {
            ExceptionUtil.throwEx(SysPermissionExceptionEnum.ERROR_PERMISSION_NOT_EXIST);
        }

        if (!module.getPerType().equals(PerTypeEnum.MODULE.getCode())) {
            ExceptionUtil.throwEx(SysPermissionExceptionEnum.ERROR_PERMISSION_NOT_MODULE_TYPE);
        }

        // 获取菜单列表
        List<SysPermission> list = super.list(sysPermissionDTO);
        List<SysPermission> permissionList = new ArrayList<>();
        permissionList.add(module);

        list.forEach(i -> {
            permissionList.add(i);
            // 获取按钮列表
            List<SysPermissionVO> btnList = sysPermissionMapper.selectByPid(i.getId());
            if (!btnList.isEmpty()) {
                btnList.forEach(j -> {
                    SysPermission btn = new SysPermission();
                    BeanUtils.copyProperties(j, btn);
                    permissionList.add(btn);
                });
            }
        });

        Page<SysPermission> page = new Page<>(1, permissionList.size(), false);
        page.addAll(permissionList);
        page.setTotal(permissionList.size());
        return new PageInfo<>(page);
    }
}
