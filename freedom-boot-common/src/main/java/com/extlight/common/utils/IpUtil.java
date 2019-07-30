package com.extlight.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author MoonlightL
 * @ClassName: IpUtil
 * @ProjectName freedom-boot
 * @Description:
 * @Date 2019/5/31 11:23
 */
@Slf4j
public class IpUtil {

    private static final String IP_V4 = "127.0.0.1";

    private static final String IP_V6 = "0:0:0:0:0:0:0:1";

    private static final String UNKNOW = "unknown";

    private static final Integer LENGTH = 15;

    private static final String DEFAULT_SEPARATOR = ",";

    private IpUtil() {}


    /**
     * 获取 IP 信息
     * @param ip
     * @return 国家|区域|省份|城市|ISP
     */
    public static String getInfo(String ip) {

        try {
            ClassPathResource resource = new ClassPathResource("ip2region.db");
            File file = resource.getFile();

            if (!file.exists()) {
                log.error("=======ip2region.db 文件不存在=========");
                return "异常";
            }

            //查询算法
            int algorithm = DbSearcher.BTREE_ALGORITHM;
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, file.getAbsolutePath());

            Method method = null;
            switch (algorithm) {
                case DbSearcher.BTREE_ALGORITHM:
                    method = searcher.getClass().getMethod("btreeSearch", String.class);
                    break;
                case DbSearcher.BINARY_ALGORITHM:
                    method = searcher.getClass().getMethod("binarySearch", String.class);
                    break;
                case DbSearcher.MEMORY_ALGORITYM:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
                default:
                    break;
            }

            DataBlock dataBlock;
            if (!Util.isIpAddress(ip)) {
                log.warn("========非法 IP 格式========");
                return "ip不合法";
            }

            dataBlock  = (DataBlock) method.invoke(searcher, ip);

            return dataBlock.getRegion();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "未知";
    }

    /**
     * 获取省份
     * @param ip
     * @return
     */
    public static String getProvince(String ip) {
        String info = getInfo(ip);
        return info.split("\\|")[2];
    }

    /**
     * 获取省份
     * @param request
     * @return
     */
    public static String getProvince(HttpServletRequest request) {
        return getProvince(getIpAddr(request));
    }

    /**
     * 获取城市
     * @param ip
     * @return
     */
    public static String getCity(String ip) {
        String info = getInfo(ip);
        return info.split("\\|")[3];
    }

    /**
     * 获取城市
     * @param request
     * @return
     */
    public static String getCity(HttpServletRequest request) {
        return getCity(getIpAddr(request));
    }

    /**
     * 获取所在地
     * @param ip
     * @return
     */
    public static String getLocation(String ip) {
        String info = getInfo(ip);
        String[] infoArr = info.split("\\|");
        return infoArr[0] + "|" + infoArr[2] + "|" + infoArr[3];
    }

    /**
     * 获取所在地
     * @param request
     * @return
     */
    public static String getLocation(HttpServletRequest request) {
        return getLocation(getIpAddr(request));
    }

    /**
     * 获取客户端真实IP
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {

        String ipAddress = request.getHeader("x-forwarded-for");

        if (ipAddress == null || ipAddress.length() == 0 || UNKNOW.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || UNKNOW.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || UNKNOW.equalsIgnoreCase(ipAddress)) {

            ipAddress = request.getRemoteAddr();

            if (ipAddress.equals(IP_V4) || ipAddress.equals(IP_V6)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

                ipAddress = inet.getHostAddress();
            }
        }

        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > LENGTH) {
            if (ipAddress.indexOf(DEFAULT_SEPARATOR) > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(DEFAULT_SEPARATOR));
            }
        }
        return ipAddress;
    }

    /**
     * 获取主机 ip
     * @return
     */
    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return IP_V4;
    }

    /**
     * 获取主机名称
     * @return
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return UNKNOW;
    }
}