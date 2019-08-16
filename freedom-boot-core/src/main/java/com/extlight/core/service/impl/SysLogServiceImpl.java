package com.extlight.core.service.impl;

import com.extlight.common.base.BaseMapper;
import com.extlight.common.base.BaseRequest;
import com.extlight.common.base.BaseServiceImpl;
import com.extlight.common.constant.ActionEnum;
import com.extlight.common.exception.GlobalException;
import com.extlight.core.mapper.SysLogMapper;
import com.extlight.core.model.SysLog;
import com.extlight.core.model.dto.SysLogDTO;
import com.extlight.core.model.vo.SysLogVO;
import com.extlight.core.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author MoonlightL
 * @ClassName: SysLogServiceImpl
 * @ProjectName freedom-boot
 * @Description: 系统日志 ServiceImpl
 * @Date 2019-07-09 13:53:07
 */
@Service
public class SysLogServiceImpl extends BaseServiceImpl<SysLog> implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public BaseMapper<SysLog> getBaseMapper() {
        return this.sysLogMapper;
    }

    @Override
    protected Example getExample(BaseRequest params) {

        Example example = new Example(SysLog.class);
        if (params != null) {
            Example.Criteria criteria = example.createCriteria();

            SysLogDTO sysLogDTO = (SysLogDTO) params;

            if (!StringUtils.isEmpty(sysLogDTO.getStartTime()) && !StringUtils.isEmpty(sysLogDTO.getEndTime())) {
                criteria.andBetween("createTime", sysLogDTO.getStartTime() + " 00:00:00", sysLogDTO.getEndTime() + " 23:59:59");
            }

            if (sysLogDTO.getUserId() != null) {
                criteria.andEqualTo("userId", sysLogDTO.getUserId());
            }
        }

        return example;
    }

    @Override
    public SysLogVO queryUserLog(Long userId) throws GlobalException {
        Example example = new Example(SysLog.class);
        example.createCriteria().andEqualTo("userId", userId).andEqualTo("actionType", ActionEnum.LOGIN.getCode());
        example.orderBy("createTime").desc();

        List<SysLog> logList = super.listByExample(example, 0, 2, false);
        SysLog sysLog = logList.get(logList.size() - 1);

        SysLogVO sysLogVO = sysLog.convertToVoModel();
        int count = this.sysLogMapper.selectCountByExample(example);
        sysLogVO.setLoginCount(count);

        return sysLogVO;
    }
}