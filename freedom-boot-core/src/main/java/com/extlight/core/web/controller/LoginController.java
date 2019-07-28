package com.extlight.core.web.controller;

import com.extlight.common.base.BaseController;
import com.extlight.common.component.annotation.ActionLog;
import com.extlight.common.constant.ActionEnum;
import com.extlight.common.constant.ModuleEnum;
import com.extlight.common.exception.GlobalException;
import com.extlight.common.exception.GlobalExceptionEnum;
import com.extlight.common.model.Result;
import com.extlight.common.utils.IpUtil;
import com.extlight.common.utils.RsaUtil;
import com.extlight.common.utils.TokenUtil;
import com.extlight.core.constant.PermissionEnum;
import com.extlight.core.constant.SystemContant;
import com.extlight.core.model.dto.LoginDTO;
import com.extlight.core.model.vo.SysPermissionVO;
import com.extlight.core.model.vo.SysUserVO;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MoonlightL
 * @Title: LoginController
 * @ProjectName freedom-boot
 * @Description: 登录控制器
 * @date 2019/5/31 13:41
 */
@Controller
@RequestMapping("/system/login")
public class LoginController extends BaseController {

    private static final String INDEX_PATH = "/home/index";

    @Autowired
    private Producer captchaProducer;

    /**
     * 获取验证码
     * @param response
     * @param session
     * @throws Exception
     */
    @GetMapping(value = "/captcha.jpg")
    public void getKaptchaImage(HttpServletResponse response, HttpSession session) throws Exception {
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setContentType("image/jpeg");
        //生成验证码
        String capText = this.captchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        //向客户端写出
        BufferedImage bi = this.captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
    }

    /**
     * 获取公钥
     * @param session
     * @return
     * @throws Exception
     */
    @PostMapping("/publicKey.json")
    @ResponseBody
    public Result publicKey(HttpSession session) throws GlobalException {
        Map<String, Object> map = RsaUtil.generateKey();
        String publicKey = RsaUtil.getPublicKey(map);
        String privateKey = RsaUtil.getPrivateKey(map);
        session.setAttribute("privateKey", privateKey);
        return Result.success(publicKey);
    }

    /**
     * 用户登录
     * @param loginDTO
     * @return
     * @throws GlobalException
     */
    @PostMapping("/login.json")
    @ResponseBody
    @ActionLog(value="用户登录", moduleName = ModuleEnum.SYSTEM, actionType = ActionEnum.LOGIN)
    public Result login(@Validated LoginDTO loginDTO, HttpServletRequest request) throws GlobalException {

        HttpSession session = request.getSession();

        String capText = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (!loginDTO.getVerifyCode().equals(capText)) {
            throw new GlobalException(GlobalExceptionEnum.ERROR_VERIFY_CODE_WRONG);
        }

        session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);

        String decryptPassword = this.decrypt(loginDTO.getPassword(), session);
        UsernamePasswordToken token = new UsernamePasswordToken(loginDTO.getUsername(), decryptPassword.toCharArray());
        Subject subject = SecurityUtils.getSubject();

        try {
            token.setRememberMe(loginDTO.getRememberMe());
            subject.login(token);
        } catch (UnknownAccountException e) {
            throw new GlobalException(GlobalExceptionEnum.ERROR_USER_NOT_EXIST);

        } catch (IncorrectCredentialsException e) {
            throw new GlobalException(GlobalExceptionEnum.ERROR_USER_PASSWORD_WRONG);

        }

        SysUserVO sysUserVO = (SysUserVO) subject.getPrincipal();
        sysUserVO.setLocation(IpUtil.getCity(super.getClientIp(request)));

        subject.getSession().setAttribute(SystemContant.CURRENT_USER, sysUserVO);
        request.setAttribute("userId", sysUserVO.getId());

        Map<String,Object> resultMap = new HashMap<>(2);
        resultMap.put("token", this.getToken(sysUserVO));
        resultMap.put("path", INDEX_PATH);
        return Result.success(resultMap);
    }

    /**
     * 用户注销
     * @return
     * @throws GlobalException
     */
    @PostMapping("/logout.json")
    @ResponseBody
    public Result logout() throws GlobalException {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Result.success();
    }

    private String getToken(SysUserVO sysUserVO) {
        Map<String, Object> claims = null;
        List<SysPermissionVO> permissionList = sysUserVO.getPermissionList();
        if (permissionList != null && !permissionList.isEmpty()) {
            claims = new HashMap<>(10);
            StringBuilder sb = new StringBuilder();
            permissionList.stream().forEach(i -> {
                if (!i.getType().equals(PermissionEnum.MODULE)) {
                    sb.append(i.getCode()).append(";");
                }
            });
            claims.put("permissions", sb.toString());
        }
        return TokenUtil.createToken(sysUserVO.getId(), sysUserVO.getUsername(), claims);
    }


    private String decrypt(String password, HttpSession session) {
        String privateKey = (String) session.getAttribute("privateKey");
        session.removeAttribute("privateKey");
        String result = new String(RsaUtil.decryptByPrivateKey(privateKey, RsaUtil.decryptBase64(password)));
        return result;
    }
}