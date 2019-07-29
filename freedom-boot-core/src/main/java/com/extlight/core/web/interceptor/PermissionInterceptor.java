package com.extlight.core.web.interceptor;

import com.extlight.common.exception.GlobalExceptionEnum;
import com.extlight.common.model.Result;
import com.extlight.common.utils.JsonUtil;
import com.extlight.core.constant.CoreExceptionEnum;
import com.extlight.core.constant.PermissionEnum;
import com.extlight.core.constant.SystemContant;
import com.extlight.core.model.vo.SysPermissionVO;
import com.extlight.core.model.vo.SysUserVO;
import com.extlight.core.service.SysPermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Autowired
    private SysPermissionService sysPermissionService;

   @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Object obj = request.getSession().getAttribute(SystemContant.CURRENT_USER);
        if (obj == null) {
            if (this.isAjax(request)) {
                this.print(response, JsonUtil.toStr(Result.fail(CoreExceptionEnum.ERROR_LOGIN_EXPIRE), false));
            } else {
                response.sendRedirect("/");
            }
            return false;
        }

        SysUserVO sysUserVO = (SysUserVO) obj;
        if (sysUserVO.getState() != 1) {
            if (this.isAjax(request)) {
                this.print(response, JsonUtil.toStr(Result.fail(GlobalExceptionEnum.ERROR_STATE_WRONG), false));
            } else {
                response.sendRedirect("/");
            }
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

       if (handler instanceof HandlerMethod) {
           HandlerMethod handlerMethod = (HandlerMethod) handler;
           // 针对 listUI 页面返回所需的数据
           if (handlerMethod.hasMethodAnnotation(GetMapping.class) && !handlerMethod.hasMethodAnnotation(ResponseBody.class)) {
               Subject subject = SecurityUtils.getSubject();
               SysUserVO sysUserVO = (SysUserVO) subject.getPrincipal();
               List<SysPermissionVO> permissionList = sysUserVO.getPermissionList();
               if (permissionList != null && !permissionList.isEmpty()) {
                   StringBuilder sb = new StringBuilder();
                   permissionList.stream().forEach(i -> {
                       if (!i.getType().equals(PermissionEnum.MODULE)) {
                           sb.append(i.getCode()).append(";");
                       }
                   });
                   modelAndView.addObject("permissions", sb.toString());
               }

               // 获取通用按钮
               List<SysPermissionVO> buttonList = this.sysPermissionService.findCommonButtonList(request.getRequestURI());
               modelAndView.addObject("buttonList", buttonList);
           }
       }
    }

    private void print(HttpServletResponse response, String result) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getOutputStream().write(result.getBytes("UTF-8"));
    }

    private boolean isAjax(HttpServletRequest request) {
        return !StringUtils.isEmpty(request.getHeader("x-requested-with")) && request.getHeader("x-requested-with").equals("XMLHttpRequest");
    }
}
