package com.genesis.org.cn.genesismeituanopenapijavasdk.utils;

import lombok.SneakyThrows;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * http post request util
 *
 * @author steven
 * &#064;date  2023/10/20
 */
public class HttpPostRequestUtil {

    private static final Logger log = Logger.getLogger(HttpPostRequestUtil.class.getName());

    /**
     * send get request
     *
     * @param url   url
     * @param param param
     * @return {@link String}
     */
    @SneakyThrows
    public static String sendGet(String url, String param, Map<String, String> headers) {
        StringBuilder result = new StringBuilder();
        try {
            // 创建信任所有证书的SSLContext
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }}, new java.security.SecureRandom());
            // 将SSLContext设置到HttpsURLConnection
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            String urlName = url + "?" + param;
            HttpURLConnection conn = getHttpURLConnection(urlName, "GET", headers);

            // 获取响应
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
            }
        } catch (IOException e) {
            log.severe("GET request failed: " + e.getMessage());
        }
        return result.toString();
    }

    /**
     * send post
     *
     * @param url     url
     * @param param   param
     * @param headers headers
     * @return {@link String}
     */
    @SneakyThrows
    public static String sendPost(String url, String param, Map<String, String> headers) {
        StringBuilder result = new StringBuilder();
        try {
            // 创建信任所有证书的SSLContext
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }}, new java.security.SecureRandom());
            // 将SSLContext设置到HttpsURLConnection
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            HttpURLConnection conn = getHttpURLConnection(url, "POST", headers);

            // 发送请求体数据
            try (OutputStream os = conn.getOutputStream(); PrintWriter out = new PrintWriter(os)) {
                out.print(param);
                out.flush();
            }

            // 获取响应
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
            }
        } catch (IOException e) {
            log.severe("POST request failed: " + e.getMessage());
        }
        return result.toString();
    }

    /**
     * send post with params
     *
     * @param url     url
     * @param headers headers
     * @param params  params
     * @return {@link String}
     */
    @SneakyThrows
    public static String sendPostWithParams(String url, Map<String, String> params, Map<String, String> headers) {
        StringBuilder result = new StringBuilder();
        try {
            // 创建信任所有证书的SSLContext
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }}, new java.security.SecureRandom());
            // 将SSLContext设置到HttpsURLConnection
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());


            // 构建包含查询参数的URL 有空格的话，会被替换成%20
            url = url + "?" +
                params.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue().replace(" ", "%20"))
                    .collect(Collectors.joining("&"));
            HttpURLConnection conn = getHttpURLConnection(url, "POST", headers);

            // 获取响应
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
            }
        } catch (IOException e) {
            log.severe("POST request failed: " + e.getMessage());
        }
        return result.toString();
    }

    /**
     * get http connection
     *
     * @param url           url
     * @param headers       headers
     * @param requestMethod request method
     * @return {@link HttpURLConnection}
     * @throws IOException ioexception
     */
    private static HttpURLConnection getHttpURLConnection(String url, String requestMethod
        , Map<String, String> headers) throws IOException {
        URL realUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();

        conn.setRequestMethod(requestMethod);
        conn.setDoOutput(true);
        conn.setDoInput(true);

        // 设置请求头
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            conn.setRequestProperty(entry.getKey(), entry.getValue());
        }

        // 设置超时时间，如果需要的话
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        return conn;
    }

    /**
     * send put request
     *
     * @param headers     headers
     * @param apiUrl      api url
     * @param requestBody request body
     * @return {@link String}
     */
    public static String sendPut(String apiUrl, String requestBody, Map<String, String> headers) {
        StringBuilder responseString = new StringBuilder();
        try {
            // 创建信任所有证书的SSLContext
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }}, new java.security.SecureRandom());
            // 将SSLContext设置到HttpsURLConnection
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法为PUT
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            // 设置请求头
            headers.forEach(connection::setRequestProperty);

            // 写入请求体数据
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // 获取响应
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    responseString.append(line);
                }
                reader.close();

                log.info("响应内容: " + responseString);
            } else {
                log.severe("HTTP请求失败，响应码: " + responseCode);
            }
            connection.disconnect();
        } catch (Exception e) {
            log.severe("PUT request failed: " + e.getMessage());
        }
        return responseString.toString();
    }

}
