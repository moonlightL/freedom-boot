package com.extlight.common.utils;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author MoonlightL
 * @Title: HttpClientUtil
 * @ProjectName freedom-boot
 * @Description: Http 请求工具类
 * @date 2019/5/31 11:28
 */
public class HttpClientUtil {

    private HttpClientUtil() {

    }

    private static PoolingHttpClientConnectionManager cm;

    static {
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(20);
    }

    /**
     * 发送 get 请求
     * @param url
     * @return
     */
    public static String sendGet(String url) {
        // 创建 http 请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");

        return sendRequest(httpGet);
    }

    /**
     * 发送 post 请求
     * @param url
     * @param params
     * @return
     */
    public static String sendPost(String url, Map<String, Object> params, Map<String, String> headers) {
        // 创建 http 请求
        HttpPost httpPost = new HttpPost(url);

        // 设置请求头
        if (headers != null && !headers.isEmpty()) {
            headers.forEach((k, v)-> httpPost.addHeader(k, v));
        }

        // 设置参数
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> formparams = new ArrayList<>();
            params.forEach((k, v) -> formparams.add(new BasicNameValuePair(k, v != null ? v.toString() : "")));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
            httpPost.setEntity(entity);
        }

        return sendRequest(httpPost);
    }

    /**
     * 发送 json 格式的 post 请求
     * @param url
     * @param json
     * @return
     */
    public String sendWithJson(String url, String json) {
        // 创建 http 请求
        HttpPost httpPost = new HttpPost(url);

        StringEntity stringEntity = new StringEntity(json,Charset.defaultCharset());
        stringEntity.setContentType("application/json");
        httpPost.setEntity(stringEntity);

        return sendRequest(httpPost);
    }

    /**
     * 发送 xml 格式的 post 请求
     * @param url
     * @param xmlStr
     * @return
     */
    public static String sendWithXml(String url, String xmlStr) {
        // 创建 http 请求
        HttpPost httpPost = new HttpPost(url);
        //解决中文乱码问题
        StringEntity stringEntity = new StringEntity(xmlStr, Charset.defaultCharset());
        stringEntity.setContentEncoding(Charset.defaultCharset().toString());
        stringEntity.setContentType(ContentType.APPLICATION_XML.toString());
        httpPost.setEntity(stringEntity);

        return sendRequest(httpPost);
    }



    private static String sendRequest(HttpRequestBase request) {
        // 创建 httpClient
        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();

        // 设置请求配置
        request.setConfig(getRequestConfig());

        CloseableHttpResponse response = null;

        try {
            response = httpclient.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "UTF-8");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private static RequestConfig getRequestConfig() {
               // 创建连接的最长时间
        return RequestConfig.custom().setConnectTimeout(1000)
                // 从连接池获取连接的最长时间
                .setConnectionRequestTimeout(500)
                // 数据传输最长时间
                .setSocketTimeout(10000)
                .build();
    }
}
