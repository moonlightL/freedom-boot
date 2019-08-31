package com.extlight.core.web.interceptor;

import com.extlight.common.utils.ExceptionUtil;
import com.extlight.common.utils.HttpUtil;
import com.extlight.core.constant.PermissionEnum;
import com.extlight.core.constant.StateEnum;
import com.extlight.core.model.SysPermission;
import com.extlight.core.model.vo.SysUserVO;
import com.extlight.core.service.SysPermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: MoonlightL
 * @ClassName: PermissionInterceptor
 * @ProjectName: freedom-boot
 * @Description: 权限拦截器
 * @DateTime: 2019-07-06 00:32
 */
@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    private static final String LIST_HTML = "listUI.html";

    @Autowired
    private SysPermissionService sysPermissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpUtil.isAjax(request))  {
            return true;
        }

        String requestURI = request.getRequestURI();
        if (!requestURI.contains(LIST_HTML)) {
            return true;
        }

        SysPermission sysPermission = this.sysPermissionService.findPermissionByUrl(requestURI);
        if (sysPermission == null) {
            return true;
        }

        if (sysPermission.getState().equals(StateEnum.NORMAL.getCode())) {
            return true;
        }

        ExceptionUtil.throwEx("该资源被禁止访问");

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) {

       if (HttpUtil.isAjax(request))  {
           return;
       }

       if (handler instanceof HandlerMethod) {
           HandlerMethod handlerMethod = (HandlerMethod) handler;
           // 针对 listUI 页面返回所需的数据
           if (handlerMethod.hasMethodAnnotation(GetMapping.class)) {
               GetMapping getMapping = handlerMethod.getMethodAnnotation(GetMapping.class);
               if (getMapping.value()[0].contains(LIST_HTML)) {
                   Subject subject = SecurityUtils.getSubject();
                   SysUserVO sysUserVO = (SysUserVO) subject.getPrincipal();
                   List<SysPermission> permissionList = sysUserVO.getPermissionList();
                   if (permissionList != null && !permissionList.isEmpty()) {
                       StringBuilder sb = new StringBuilder();
                       permissionList.stream().forEach(i -> {
                           if (!i.getResourceType().equals(PermissionEnum.MODULE)) {
                               sb.append(i.getPerCode()).append(";");
                           }
                       });

                       modelAndView.addObject("permissions", sb.toString());
                   }

                   // 获取通用按钮
                   List<SysPermission> buttonList = this.sysPermissionService.findCommonButtonList(request.getRequestURI());
                   modelAndView.addObject("buttonList", buttonList);
               }
           }
       }
    }

}
