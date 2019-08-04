package com.extlight.core.service.impl;

import com.extlight.common.base.BaseMapper;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.base.BaseServiceImpl;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.exception.GlobalExceptionEnum;
import com.extlight.common.utils.ExceptionUtil;
import com.extlight.core.constant.PermissionEnum;
import com.extlight.core.constant.SysPermissionExceptionEnum;
import com.extlight.core.mapper.SysPermissionMapper;
import com.extlight.core.model.SysPermission;
import com.extlight.core.model.dto.SysPermissionDTO;
import com.extlight.core.model.vo.SysPermissionVO;
import com.extlight.core.model.vo.TreeNode;
import com.extlight.core.service.SysPermissionService;
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
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermission, SysPermissionVO> implements SysPermissionService {

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
        }

        return example;
    }


    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public int save(SysPermission sysPermission) throws GlobalException {
        if (!sysPermission.getType().equals(PermissionEnum.MODULE.getCode())) {
            if (sysPermission.getPid() == null || sysPermission.getPid() == 0) {
                ExceptionUtil.throwEx(GlobalExceptionEnum.ERROR_PARAM);
            }

            SysPermissionVO parent = super.getById(sysPermission.getPid());
            if (parent == null) {
                ExceptionUtil.throwEx(SysPermissionExceptionEnum.ERROR_PERMISSION_NOT_EXIST);
            }

        } else {
            sysPermission.setCode("");
        }

        return super.save(sysPermission);
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public int remove(Long id) throws GlobalException {
        int result = super.remove(id);
        return result;
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public int removeBatch(List<Long> idList) throws GlobalException {
        int result = super.removeBatch(idList);
        return result;
    }


    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public int update(SysPermission sysPermission) throws GlobalException {
        int result = super.update(sysPermission);
        return result;
    }

    @Override
    public List<SysPermissionVO> findHierarchyPermissionList() throws GlobalException {
        List<SysPermissionVO> list = this.sysPermissionMapper.selectHierarchyPermissionList();
        return list;
    }

    @Override
    public List<SysPermissionVO> findPermissionListByUserId(Long userId) throws GlobalException {

        List<SysPermission> sysPermissionList = this.sysPermissionMapper.selectByUserId(userId);
        return this.parseData(sysPermissionList);
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
    public List<SysPermissionVO> findCommonButtonList(String url) throws GlobalException {
        List<SysPermission> commonButtonList = this.sysPermissionMapper.selectCommonButtonList(url);
        return this.parseData(commonButtonList);
    }


    private List<SysPermissionVO> parseData(List<SysPermission> list) {
        if (list.isEmpty()) {
            return new ArrayList<>();
        }

        List<SysPermissionVO> result = new ArrayList<>(list.size());
        list.forEach(i -> result.add(i.toVO(SysPermissionVO.class)));

        return result;
    }
}
