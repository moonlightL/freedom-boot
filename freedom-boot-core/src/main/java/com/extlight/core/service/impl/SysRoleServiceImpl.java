package com.extlight.core.service.impl;

import com.extlight.common.base.BaseMapper;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.base.BaseServiceImpl;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.utils.ExceptionUtil;
import com.extlight.core.constant.SysRoleExceptionEnum;
import com.extlight.core.mapper.SysRoleMapper;
import com.extlight.core.model.SysRole;
import com.extlight.core.model.dto.SysRoleDTO;
import com.extlight.core.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @Author MoonlightL
 * @ClassName: SysRoleServiceImpl
 * @ProjectName freedom-boot
 * @Description: 系统角色 ServiceImpl
 * @Date 2019/7/1 15:48
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public BaseMapper<SysRole> getBaseMapper() {
        return sysRoleMapper;
    }

    @Override
    protected Example getExample(BaseRequest params) {

        Example example = new Example(SysRole.class);
        if (params != null) {
            Example.Criteria criteria = example.createCriteria();
            SysRoleDTO sysRoleDTO = (SysRoleDTO) params;

            if (!StringUtils.isEmpty(sysRoleDTO.getName())) {
                criteria.andLike("name", sysRoleDTO.getName() + "%");
            }
        }

        return example;
    }

    @Override
    public List<SysRole> findRoleListByUserId(Long userId) throws GlobalException {

        List<SysRole> roleList = this.sysRoleMapper.selectByUserId(userId);
        if (roleList.isEmpty()) {
            return new ArrayList<>();
        }

        return roleList;
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public int asignPermission(Long roleId, String permissionIdStr) throws GlobalException {

        SysRole target = super.getById(roleId);
        if (target == null) {
            ExceptionUtil.throwEx(SysRoleExceptionEnum.ERROR_ROLE_NOT_EXIST);
        }

        // 解绑
        this.sysRoleMapper.deleteRolePermissionByRoleId(roleId);

        if (!StringUtils.isEmpty(permissionIdStr)) {
            String[] roleIdArr = permissionIdStr.split(",");
            List<Map<String, Long>> paramList = new ArrayList<>();
            Arrays.stream(roleIdArr).forEach(i -> {
                Map<String, Long> tmp = new HashMap<>(2);
                tmp.put("roleId", roleId);
                tmp.put("permissionId", Long.valueOf(i));
                paramList.add(tmp);
            });

            // 分配
            return this.sysRoleMapper.insertRolePermissionBatch(paramList);
        }

        return 1;
    }
}
