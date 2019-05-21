package com.example.demo.test;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class Tifeng {

    public static void main(String[] args) {
        String url = "http://shankapi.ifeng.com/shanklist/_/getColumnInfo/_/dynamicFragment/ / /20/3-35191-/getColumnInfoCallback?callback=getColumnInfoCallback";
        //String url = "http://shankapi.ifeng.com/shanklist/_/getColumnInfo/_/dynamicFragment/6508767803489652938/1551827100000/20/3-35191-/getColumnInfoCallback?callback=getColumnInfoCallback";
        String get = gethtml(url);
        System.out.println(get);
    }




    public static String gethtml(String url) {
        String htmlContent = "";
        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpClient client = builder.build();
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept", "*/*");
        request.setHeader("Accept-Encoding", "gzip, deflate");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
        request.setHeader("Connection", "keep-alive");
        request.setHeader("Host", "shankapi.ifeng.com");
        request.setHeader("referer","http://news.ifeng.com/");
        request.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        request.setHeader("Cookie", "UM_distinctid=169486e9109cd8-03dae7f20b66cc-3a3a5d0c-100200-169486e910a3b7; userid=1551698401663_m1av5f2992; prov=cn024; city=024; weather_city=ln_sy;");
        try {
            HttpResponse response = client.execute(request);
            htmlContent = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("306行" + request);
        //System.out.println("307行" + htmlContent);


        return htmlContent;
    }
}
