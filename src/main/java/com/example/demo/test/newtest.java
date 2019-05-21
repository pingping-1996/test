package com.example.demo.test;

import com.alibaba.fastjson.JSONObject;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.http.Header;

import java.util.Date;

public class newtest {
    public static void main(String[] args) throws HttpProcessException {
        //插件式配置Header（各种header信息、自定义header）
        Header[] headers = HttpHeader.custom()
                //.cookie("UM_distinctid=16abb082b80679-0075c4ae8b8b2a-3a3a5d0c-100200-16abb082b81272; tt_webid=6691198395070563843; csrftoken=0ba67588da835d05492e0c0416a80f22; s_v_web_id=0d738da72c0ade680e1914af187d6d55; tt_webid=6691198395070563843; WEATHER_CITY=%E5%8C%97%E4%BA%AC; __tasessionId=9dczg5pr51557976459560; CNZZDATA1259612802=674838410-1557912530-https%253A%252F%252Flanding.toutiao.com%252F%7C1557974089")
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36")
                .build();
        String html = HttpClientUtil.get(HttpConfig.custom()
                .headers(headers)
                //设置headers，不需要时则无需设置

                .url("http://paper.people.com.cn/rmrb/html/2019-05/20/nbs.D110000renmrb_12.htm").encoding("UTF-8"));
        //JSONObject jsonObject = JSONObject.parseObject(html);
        System.out.println(html);




    }

}
