package com.extlight.core.service.impl;

import com.extlight.common.base.BaseMapper;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.base.BaseServiceImpl;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.exception.GlobalExceptionEnum;
import com.extlight.common.utils.ExceptionUtil;
import com.extlight.common.utils.StringUtil;
import com.extlight.common.utils.ThreadUtil;
import com.extlight.core.constant.SysUserExceptionEnum;
import com.extlight.core.mapper.SysUserMapper;
import com.extlight.core.model.SysUser;
import com.extlight.core.model.dto.SysUserDTO;
import com.extlight.core.model.vo.SysUserVO;
import com.extlight.core.service.SysUserService;
import com.extlight.core.web.config.CoreConfig;
import com.extlight.extensions.file.service.FileDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author MoonlightL
 * @ClassName: SysUserServiceImpl
 * @ProjectName freedom-boot
 * @Description: 系统用户 ServiceImpl
 * @Date 2019/5/31 11:15
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, SysUserVO> implements SysUserService {

    @Autowired
    private CoreConfig coreConfig;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private FileDataService fileDataService;

    @Override
    public BaseMapper<SysUser> getBaseMapper() {
        return this.sysUserMapper;
    }

    @Override
    protected Example getExample(BaseRequest params) {

        Example example = new Example(SysUser.class);
        if (params != null) {
            Example.Criteria criteria = example.createCriteria();

            criteria.andNotEqualTo("superAdmin", 1);

            SysUserDTO sysUserDTO = (SysUserDTO) params;

            if (!StringUtils.isEmpty(sysUserDTO.getUsername())) {
                criteria.andLike("username", sysUserDTO.getUsername() + "%");
            }

            if (!StringUtils.isEmpty(sysUserDTO.getNickname())) {
                criteria.andLike("nickname", sysUserDTO.getNickname() + "%");
            }

            if (!StringUtils.isEmpty(sysUserDTO.getEmail())) {
                criteria.andLike("email", sysUserDTO.getEmail() + "%");
            }
        }

        return example;
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public int remove(Long id) throws GlobalException {
        if (id == 1L) {
            ExceptionUtil.throwEx(GlobalExceptionEnum.ERROR_CAN_NOT_DELETE_RESOURCE);
        }
        return super.remove(id);
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public int removeBatch(List<Long> idList) throws GlobalException {
        List<Long> paramIdList = idList.stream().filter(i -> i.longValue() != 1L).collect(Collectors.toList());
        return super.removeBatch(paramIdList);
    }

    @Override
    public SysUser findUserByUsername(String username) throws GlobalException {
        SysUser sysUser = this.sysUserMapper.selectByUsername(username);
        if (sysUser == null) {
            return null;
        }

        return sysUser;
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public int asignRole(Long userId, String roleIdStr) throws GlobalException {

        SysUserVO target = super.getById(userId);
        if (target == null) {
            ExceptionUtil.throwEx(SysUserExceptionEnum.ERROR_USER_NOT_EXIST);
        }

        // 解绑
        this.sysUserMapper.deleteUserRoleByUserId(userId);

        if (!StringUtils.isEmpty(roleIdStr)) {
            String[] roleIdArr = roleIdStr.split(",");
            List<Map<String, Long>> paramList = new ArrayList<>();
            Arrays.stream(roleIdArr).forEach(i -> {
                Map<String, Long> tmp = new HashMap<>(2);
                tmp.put("userId", userId);
                tmp.put("roleId", Long.valueOf(i));
                paramList.add(tmp);
            });

            // 分配
            return this.sysUserMapper.insertUserRoleBatch(paramList);
        }

        return 1;
    }

    @Override
    public boolean updateAvatar(String originalFilename, String contentType, byte[] data) throws GlobalException {

        String url = this.fileDataService.uploadFile(originalFilename, contentType, data);
        if (StringUtil.isBlank(url)) {
            ExceptionUtil.throwEx(SysUserExceptionEnum.ERROR_UPDATE_AVATAR_FAIL);
        }

        Long userId = ThreadUtil.get();
        SysUser sysUser = new SysUser();
        sysUser.setId(userId).setAvatar(url);

        return super.update(sysUser) > 0;
    }

    @Override
    public int save(SysUser model) throws GlobalException {
        model.setPassword(DigestUtils.md5DigestAsHex(this.coreConfig.getPassword().getBytes()));
        model.setAvatar(this.coreConfig.getAvatar());
        return super.save(model);
    }

    @Override
    public int update(SysUser model) throws GlobalException {
        return super.update(model);
    }
}
