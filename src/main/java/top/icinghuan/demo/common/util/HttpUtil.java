/**
 * @(#)HttpUtil.java, Dec 17, 2014.
 * <p/>
 * Copyright 2014 Tiger, Inc. All rights reserved. Tiger PROPRIETARY/CONFIDENTIAL. Use is subject to
 * license terms.
 */
package top.icinghuan.demo.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.*;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Slf4j
public class HttpUtil {

    private static final int CONNECT_TIMEOUT = 10000;
    private static final int SOCKET_TIMEOUT = 10000;

    public static final String URL_SEPARATOR = "/";

    public static String get(String url, Map<String, String> headers) {
        return get(url, headers, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }

    public static String get(String url, Map<String, String> headers, int connectTimeout, int socketTimeout) {
        try {
            Request request = Request.Get(url).connectTimeout(connectTimeout)
                    .socketTimeout(socketTimeout);
            request.addHeader("content-type", "application/json;charset=UTF-8");
            if (headers != null && headers.size() > 0) {
                for (Entry<String, String> entry : headers.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            }
            Response response = request.execute();
            HttpResponse httpResponse = response.returnResponse();
            String respStr = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            return respStr;
        } catch (Exception e) {
            log.error("http get {}", url, e);
        }
        return null;
    }

    public static String get(String url, String accessToken, String tradeToken, int connectTimeout, int socketTimeout) {
        try {
            Request request = Request.Get(url).connectTimeout(connectTimeout)
                    .socketTimeout(socketTimeout);
            request.addHeader("content-type", "application/json;charset=UTF-8");
            if (accessToken != null) {
                request.addHeader("Authorization", "Bearer " + accessToken);
            }
            if (tradeToken != null) {
                request.addHeader("TradeToken", tradeToken);
            }
            Response response = request.execute();
            HttpResponse httpResponse = response.returnResponse();
            String respStr = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            return respStr;
        } catch (Exception e) {
            log.error("http get {}", url, e);
        }
        return null;
    }

    public static String get(String url, String accessToken, int connectTimeout, int socketTimeout) {
        try {
            Request request = Request.Get(url).connectTimeout(connectTimeout)
                    .socketTimeout(socketTimeout);
            request.addHeader("content-type", "application/json;charset=UTF-8");
            if (accessToken != null) {
                request.addHeader("Authorization", "Bearer " + accessToken);
            }
            Response response = request.execute();
            HttpResponse httpResponse = response.returnResponse();
            String respStr = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            return respStr;
        } catch (Exception e) {
            log.error("http get {}", url, e);
        }
        return null;
    }

    public static String get(String url, String accessToken) {
        return get(url, accessToken, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }


    public static String get(String url, String accessToken, String tradeToken) {
        return get(url, accessToken, tradeToken, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }

    public static String get(String url) {
        return get(url, null, null, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }

    public static String get(String url, String charset, int connectTimeout, int socketTimeout, Map<String, String> params) {
        try {
            StringBuilder sb = new StringBuilder(url);

            if (params != null) {
                int i = 0;
                for (Entry<String, String> param : params.entrySet()) {
                    if (i == 0) {
                        sb.append(url.indexOf("?") >= 0 ? '&' : '?').append(param.getKey()).append('=').append(param.getValue());
                    } else {
                        sb.append('&').append(param.getKey()).append('=').append(param.getValue());
                    }
                    ++i;
                }
            }
            Content content = Request.Get(sb.toString())
                    .connectTimeout(connectTimeout)
                    .socketTimeout(socketTimeout).execute().returnContent();

            return IOUtils.toString(content.asStream(), charset);
        } catch (Exception e) {
            log.error("http get {}", url, e);
        }
        return null;
    }

    public static String get(Executor executor, String url) {
        try {
            Content content = executor.execute(Request.Get(url).connectTimeout(5000)
                    .socketTimeout(5000)).returnContent();
            return IOUtils.toString(content.asStream(), content.getType().getCharset());
        } catch (Exception e) {
            log.error("http get {}", url, e);
        }
        return null;
    }

    public static String post(String url, Map<String,String> headers, Map<String, Object> params) {
        try {
            if (params == null || params.size() == 0) {
                return get(url, headers);
            }
            List<NameValuePair> pairs;
            Form form = Form.form();
            for (Entry<String, Object> entry : params.entrySet()) {
                form.add(entry.getKey(), String.valueOf(entry.getValue()));
            }
            pairs = form.build();
            Request request = Request.Post(url).connectTimeout(CONNECT_TIMEOUT)
                    .socketTimeout(SOCKET_TIMEOUT);
            if (headers != null && headers.size() > 0) {
                for (Entry<String, String> entry : headers.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            }
            request.bodyForm(pairs, Charset.forName("UTF-8"));
            Response response = request.execute();
            HttpResponse httpResponse = response.returnResponse();
            String respStr = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            return respStr;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("http post {}", url, e);
        }
        return null;
    }

    public static String post(String url, String accessToken, String tradeToken, Map<String, Object> params) {
        try {
            if (params == null || params.size() == 0) {
                return get(url, accessToken, tradeToken);
            }
            List<NameValuePair> pairs;
            Form form = Form.form();
            for (Entry<String, Object> entry : params.entrySet()) {
                form.add(entry.getKey(), String.valueOf(entry.getValue()));
            }
            pairs = form.build();
            Request request = Request.Post(url).connectTimeout(CONNECT_TIMEOUT)
                    .socketTimeout(SOCKET_TIMEOUT);
            if (accessToken != null) {
                request.addHeader("Authorization", "Bearer " + accessToken);
            }
            if (tradeToken != null) {
                request.addHeader("TradeToken", tradeToken);
            }
            request.bodyForm(pairs, Charset.forName("UTF-8"));
            Response response = request.execute();
            HttpResponse httpResponse = response.returnResponse();
            String respStr = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            return respStr;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("http post {}", url, e);
        }
        return null;
    }
    //

    public static String post(String url, String accessToken, Map<String, Object> params) {
        try {
            if (params == null || params.size() == 0) {
                return get(url, accessToken);
            }
            List<NameValuePair> pairs;
            Form form = Form.form();
            for (Entry<String, Object> entry : params.entrySet()) {
                form.add(entry.getKey(), String.valueOf(entry.getValue()));
            }
            pairs = form.build();
            Request request = Request.Post(url).connectTimeout(CONNECT_TIMEOUT)
                    .socketTimeout(SOCKET_TIMEOUT);
            if (accessToken != null) {
                request.addHeader("Authorization", "Bearer " + accessToken);
            }
            request.bodyForm(pairs, Charset.forName("UTF-8"));
            Response response = request.execute();
            HttpResponse httpResponse = response.returnResponse();
            String respStr = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            return respStr;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("http post {}", url, e);
        }
        return null;
    }

    public static String getUrl(String baseUrl, String relativeUrl, Map<String, Object> params) {
        String url = baseUrl + relativeUrl;
        if (params != null) {
            url += "?";
            url += getParamUrl(params);
        }
        return url;
    }

    public static String getParamUrl(Map<String, Object> params) {
        if (params == null || params.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("&");
        }
        String result = sb.toString();
        return result.substring(0, result.length() - 1);
    }
}
