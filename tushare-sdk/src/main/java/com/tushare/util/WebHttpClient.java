package com.tushare.util;

import com.tushare.exception.TushareException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;


public class WebHttpClient {

    private static int soTimeout = 20000;
    private static int connTimeout = 20000;
    private static final String charset = "UTF-8";
    private static SSLContext sslContext;

    private static final Logger logger = LogManager.getLogger(WebHttpClient.class);

    public static int getSoTimeout() {
        return soTimeout;
    }

    public static int getConnTimeout() {
        return connTimeout;
    }

    public static void setSoTimeout(int soTimeout) {
        WebHttpClient.soTimeout = soTimeout;
    }

    public static void setConnTimeout(int connTimeout) {
        WebHttpClient.connTimeout = connTimeout;
    }


    public static String postRequest(String message, String url, String contentType) throws TushareException {
        CloseableHttpClient httpClient = HttpClients.custom().disableAutomaticRetries().setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();


        logger.info("url: {}", url);
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(soTimeout)
                .setConnectTimeout(connTimeout).setConnectionRequestTimeout(connTimeout).build();
        httpPost.setConfig(requestConfig);
        String responseStr = "";
        try {
            logger.info("request message: {}", message);
            StringEntity strEntity = new StringEntity(message, charset);
            strEntity.setContentEncoding(charset);
            strEntity.setContentType(contentType);
            httpPost.setEntity(strEntity);
            logger.info("begin post");
            CloseableHttpResponse response = httpClient.execute(httpPost);
            logger.info("end post");
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    responseStr = EntityUtils.toString(entity, charset);
                    logger.info("response message: {}", responseStr);
                }
            } finally {
                response.close();
            }
        } catch (Exception ex) {
            throw new TushareException("HTTP请求发送失败", ex);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
            }
        }
        if (StringUtils.isBlank(responseStr)) {
            throw new TushareException("HTTP响应异常");
        }
        return responseStr;
    }


    /**
     * 设置为忽略SSL证书
     */
    private static void setSslContext(){
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            sslContext = sslContext.getInstance("TLSv1.2");
            sslContext.init(null, trustAllCerts, new SecureRandom());
        } catch (Exception ex) {

        }

    }
}
