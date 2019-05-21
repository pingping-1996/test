package com.example.demo.test;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class sohu {
    public static void main(String[] args) {
        String url = "http://v2.sohu.com/public-api/feed?scene=CATEGORY&sceneId=1460&page=1&size=20";
        String get = gethtml(url);
        System.out.println(get);
    }

    /*
    *
    *
    Accept-Encoding: gzip, deflate
    Accept-Language: zh-CN,zh;q=0.9
    Connection: keep-alive
    Cookie: SUV=1901241557406914; gidinf=x099980109ee0f1bdd0bc443b0005a26a9c5ccd329f8; _muid_=1549159192782075; beans_dmp=%7B%22admaster%22%3A1550056739%2C%22shunfei%22%3A1550056739%2C%22reachmax%22%3A1550056739%2C%22lingji%22%3A1550056739%2C%22yoyi%22%3A1550056739%2C%22ipinyou%22%3A1550056739%2C%22ipinyou_admaster%22%3A1550056739%2C%22miaozhen%22%3A1550056739%2C%22diantong%22%3A1550056739%2C%22huayang%22%3A1550056739%7D; beans_mz_userid=Ih7mg0yAH9j8; UM_distinctid=168e6da22252b5-069af2739b675f-3a3a5d0c-100200-168e6da2226445; reqtype=pc; IPLOC=CN2109; t=1551853150730
    Host: v2.sohu.com
    Referer: http://www.sohu.com/c/8/1460
    User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36
    *
    *
    * */
    public static String gethtml(String url) {
        String htmlContent = "";
        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpClient client = builder.build();
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept", "*/*");
        request.setHeader("Accept-Encoding", "gzip, deflate, br");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
        //request.setHeader("Connection", "keep-alive");
        //request.setHeader("Host", "shankapi.ifeng.com");
        request.setHeader("Referer","http://www.sohu.com/c/8/1460");
//        request.setHeader("User-Agent",
//                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        //request.setHeader("Cookie", "pgv_pvid=8545944658; tvfe_boss_uuid=2739e2fcd409283b; pac_uid=0_32906de2df684; pgv_info=ssid=s446121789; pgv_pvi=775371776; pgv_si=s807455744");
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
