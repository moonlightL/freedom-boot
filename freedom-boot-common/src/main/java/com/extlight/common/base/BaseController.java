package com.extlight.common.base;

import com.extlight.common.constant.BrowserConstant;
import com.extlight.common.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author MoonlightL
 * @ClassName: BaseController
 * @ProjectName freedom-boot
 * @Description: 控制器基类
 * @Date 2019/5/31 13:51
 */
@Slf4j
public abstract class BaseController {

    private static final String PREFIX = "prefix";

    private static final String ACTION = "action";

    /**
     * 新增页面
     */
    protected static final String SAVE_PAGE = "saveUI";

    /**
     * 修改页面
     */
    protected static final String UPDATE_PAGE = "updateUI";

    /**
     * 列表页面
     */
    protected static final String LIST_PAGE = "listUI";

    /**
     * 详情页面（与修改页面共用）
     */
    protected static final String DETAIL_PAGE = "updateUI";

    private static final Map<String, String> ACTION_MAP;

    static {
        ACTION_MAP = new HashMap<>();
        ACTION_MAP.put(LIST_PAGE, "list.json");
        ACTION_MAP.put(SAVE_PAGE, "save.json");
        ACTION_MAP.put(UPDATE_PAGE, "update.json");
    }

    protected String render(String toPage, Map<String,Object> resultMap) {

        if (!resultMap.containsKey(PREFIX)) {
            resultMap.put(PREFIX, this.getPrefix());
        }

        if (!resultMap.containsKey(ACTION)) {
            resultMap.put(ACTION, String.format("%s/%s", resultMap.get(PREFIX).toString(), ACTION_MAP.get(toPage.replace("/", ""))));
        }

        if (StringUtils.isEmpty(toPage)) {
            return resultMap.get(PREFIX).toString();
        }

        return String.format("%s/%s", resultMap.get(PREFIX).toString(), toPage);
    }

    /**
     * 获取 Prefix 地址
     * @return
     */
    protected String getPrefix() {

        RequestMapping annotation = this.getClass().getAnnotation(RequestMapping.class);
        String[] values = annotation.value();
        if (values.length == 0) {
            return "";
        }

        return annotation.value()[0];
    }

    /**
     * 获取客户端 ip
     * @param request
     * @return
     */
    protected String getClientIp(HttpServletRequest request) {
        return IpUtil.getIpAddr(request);
    }

    /**
     * 下载文件
     * @param fileUrl  文件资质
     * @param fileName 文件名称
     * @param request
     * @param response
     * @throws Exception
     */
    protected void download(String fileUrl, String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception{

        byte[] data = IOUtils.toByteArray(new URL(fileUrl));
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + this.getFinalName(request, fileName));

        // 写出
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.write(data, outputStream);
    }

    /**
     * 下载文件
     * @param data      文件数据流
     * @param fileName  文件名称
     * @param request
     * @param response
     * @throws Exception
     */
    protected void download(byte[] data, String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception{

        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + this.getFinalName(request, fileName));
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        // 写出
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.write(data, outputStream);
    }

    private String getFinalName(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {

        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;

        if (agent.contains(BrowserConstant.MSIE)) {
            filename = URLEncoder.encode(filename, "UTF-8");
            filename = filename.replace("+", " ");

        } else if (agent.contains(BrowserConstant.FIREFOX)) {
            filename = new String(fileName.getBytes(), "ISO8859-1");

        } else if (agent.contains(BrowserConstant.CHROME)) {
            filename = URLEncoder.encode(filename, "UTF-8");

        } else {
            filename = URLEncoder.encode(filename, "UTF-8");
        }

        return filename;
    }

}
