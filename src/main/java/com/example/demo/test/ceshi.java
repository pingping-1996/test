package com.example.demo.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.apache.avro.data.Json;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class ceshi {
    public static void main(String[] args) {
        String url = "https://bbs.pcauto.com.cn/intf/topic/counter.ajax?tid=11854908";
        String get = getNum(url);
        System.out.println("=============================================="+get);
        /*JSONObject jsonObject = JSON.parseObject(get);
        //System.out.println(jsonObject);
        String value = jsonObject.getString("views");
        System.out.println(value);*/
    }
    public static String getNum(String url){
        String htmlConnect = "";
        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpClient client = builder.build();
        HttpGet request = new HttpGet(url);

          request.setHeader("accept","*/*");
          request.setHeader("accept-encoding","gzip, deflate, br");
          request.setHeader("accept-language","zh-CN,zh;q=0.9");
          request.setHeader("cookie","u4ad=423esd7d6; u=423o09oj; pcsuv=1548658423804.a.1097133126; /smile=1D1; c=423lv0hl; UM_distinctid=168ad59c0ca9c7-0b33c5da05205f-3a3a5d0c-100200-168ad59c0cb374; JSESSIONID=abcv-dnmP7ONTcE1K--Jw; visitedfid=1799D1988D2130; pcuvdata=lastAccessTime=1550539698302|visits=41; channel=182\n");
          request.setHeader("user-agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");

        //  request.setHeader("referer","https://bbs.pcauto.com.cn/topic-18271106.html");
        try{
            HttpResponse response = client.execute(request);
            htmlConnect = EntityUtils.toString(response.getEntity(),"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        //解析返回jsonaray类型
        return htmlConnect;
    }

}
