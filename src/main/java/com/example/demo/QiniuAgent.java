package com.example.demo;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class QiniuAgent {
    // 代理服务器
    final static String proxyHost = "t.16yun.cn";

    private static final String proxyServer = "w10.t.16yun.cn";
    private static final int proxyPort = 6470;
    private static final String proxyUser = "16YDTUWI";
    private static final String proxyPass = "146039";

//        String proxyServer = "p5.t.16yun.cn";// 61.132.93.14:6214 n5.t.16yun.cn


    private static PoolingHttpClientConnectionManager cm = null;
    private static HttpRequestRetryHandler httpRequestRetryHandler = null;
    private static HttpHost proxy = null;

    private static CredentialsProvider credsProvider = null;
    private static RequestConfig reqConfig = null;

    static {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();

        Registry registry = RegistryBuilder.create()
                .register("http", plainsf)
                .register("https", sslsf)
                .build();

        cm = new PoolingHttpClientConnectionManager(registry);
        cm.setMaxTotal(20);
        cm.setDefaultMaxPerRoute(5);

        proxy = new HttpHost(proxyHost, proxyPort, "http");

        credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(proxyUser, proxyPass));

        reqConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(5000)
                .setConnectTimeout(5000)
                .setSocketTimeout(5000)
                .setExpectContinueEnabled(false)
                .setProxy(new HttpHost(proxyHost, proxyPort))
                .build();
    }

    public String doRequest(HttpRequestBase httpReq, String id) {
        CloseableHttpResponse httpResp = null;
        StringBuffer sb = new StringBuffer();
        try {
            setHeaders(httpReq, id);

            httpReq.setConfig(reqConfig);

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setConnectionManager(cm)
                    .setDefaultCredentialsProvider(credsProvider)
                    .build();

            AuthCache authCache = new BasicAuthCache();
            authCache.put(proxy, new BasicScheme());

            HttpClientContext localContext = HttpClientContext.create();
            localContext.setAuthCache(authCache);
            httpResp = httpClient.execute(httpReq, localContext);

            int statusCode = httpResp.getStatusLine().getStatusCode();

            System.out.println(statusCode);

            BufferedReader rd = new BufferedReader(new InputStreamReader(httpResp.getEntity().getContent(), "UTF-8"));


            String line = "";
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpResp != null) {
                    httpResp.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return sb.toString();
        }
    }

    /**
     * 设置请求头
     *
     * @param httpReq
     */
    private void setHeaders(HttpRequestBase httpReq, String id) {

        // 设置Proxy-Tunnel
        // Random random = new Random();
        // int tunnel = random.nextInt(10000);
        // httpReq.setHeader("Proxy-Tunnel", String.valueOf(tunnel));

        httpReq.addHeader("accept", "*/*");
        httpReq.addHeader("accept-encoding", "gzip, deflate");
        httpReq.addHeader("accept-language", "zh-CN,zh;q=0.9");
        httpReq.addHeader("cookie", ConstantClassField.CK[(int)
                (Math.random() * ConstantClassField.CK.length)]);
        httpReq.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3610.2 Safari/537.36");
//                UA[(int)
//                (Math.random()*UA.length)]);
        httpReq.addHeader("referer", "https://search.jd.com/Search?keyword=%E9%A5%AE%E7%89%87&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&wq=%E9%A5%AE%E7%89%87&page=2&s=300&click=0");

    }


    public String doGetRequest(String id, Integer page) {
        // 要访问的目标页面

        String targetUrl = "https://search.jd.com/search?keyword=%E9%A5%AE%E7%89%87&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&ev=exbrand_%E5%BA%B7%E7%BE%8E%5E&uc=0#J_searchWrap";
                          //https://search.jd.com/s_new.php?keyword=%E9%A5%AE%E7%89%87&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&wq=%E9%A5%AE%E7%89%87&page=4&s=90&scrolling=y

        HttpGet httpGet = null;//15142
        try {
            httpGet = new HttpGet(targetUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doRequest(httpGet, id);
    }

}