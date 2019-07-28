package com.extlight.core.service;

import com.extlight.common.base.BaseService;
import com.extlight.common.exception.GlobalException;
import com.extlight.core.model.SysLog;
import com.extlight.core.model.vo.SysLogVO;

/**
 * @Author MoonlightL
 * @Title: SysLogService
 * @ProjectName freedom-boot
 * @Description: 系统日志 ServiceImpl
 * @Date 2019-07-09 13:53:07
 */
public interface SysLogService extends BaseService<SysLog, SysLogVO> {

    /**
     * 获取用户相关日志信息
     * @param userId
     * @return
     * @throws GlobalException
     */
    SysLogVO queryUserLog(Long userId) throws GlobalException;
}

