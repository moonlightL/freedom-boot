package com.extlight.core.web.config.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

/**
 * @Author MoonlightL
 * @ClassName: ShiroSessionDao
 * @ProjectName freedom-boot
 * @Description: 自定义 SessionDao
 * @Date 2019/7/17 14:41
 */
@Slf4j
public class ShiroSessionDao extends EnterpriseCacheSessionDAO {

    public ShiroSessionDao() {
        super();
    }

}
