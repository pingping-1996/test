package com.example.demo.elong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.text.Document;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class YLcomment {
    public static void main(String[] args) {
        String url1 = "http://hotel.elong.com/02201201/";
        String url2 = "http://hotel.elong.com/ajax/comment/getcommentbypage/?hotelId=02201201&recommendedType=0&pageIndex=3&mainTagId=0&subTagId=0&rankType=0&eToken=5df88e27-8c9c-4799-b314-abbe1c0e5c1e&code=7211906&_=1554968341943";
        JSONArray arr =  GetAsynCommentView(url2,url1);
        System.out.println("arr==="+arr);
    }

    //get请求
    public static JSONArray GetAsynCommentView(String url, String referer) {
        String htmlContent = "";
        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpClient client = builder.build();
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept",
                "application/json, text/javascript, */*; q=0.01");
        request.setHeader("Accept-Encoding", "gzip, deflate");
        request.setHeader("Connection", "keep-alive");
        //request.setHeader("Content-Type",
        //"application/x-www-form-urlencoded; charset=utf-8");
        request.setHeader("Cookie",
                "newjava1=ec4acdc2909559397e6c18445d4cf6d4; JSESSIONID=52160C749C9F6620BE2ACFFD8D288032; CookieGuid=5df88e27-8c9c-4799-b314-abbe1c0e5c1e; SessionGuid=ad2cce77-e127-4005-868f-80a18b0a459e; Esid=2a5b0461-962c-493a-b649-e85023b0eb1e; fv=pcweb; anti_token=687243E2-F874-4E5B-8CD8-54C11D15292C; ext_param=bns%3D4%26ct%3D3; s_cc=true; s_visit=1; _fid=5df88e27-8c9c-4799-b314-abbe1c0e5c1e; iv=1234567890555155; IHotelSearch=InDate%3D2019-04-11%26OutDate%3D2019-04-12; semid=ppzqbaidu; outerFrom=ppzqbaidu; com.eLong.CommonService.OrderFromCookieInfo=Orderfromtype=5&Parentid=3150&Status=1&Cookiesdays=0&Coefficient=0.0&Pkid=50793&Priority=9001&Isusefparam=0&Makecomefrom=0&Savecookies=0; s_eVar44=ppzqbaidu; __tctmc=20377580.248343553; __tctmd=20377580.229070686; __tctma=20377580.1554962211171711.1554963366088.1554963366088.1554963366088.1; __tctmu=20377580.0.0; __tctmz=20377580.1554963366088.1.1.utmccn=(organic)|utmcmd=organic|utmEsl=utf-8|utmcsr=baidu|utmctr=%e8%89%ba%e9%be%99; longKey=1554962211171711; __tctrack=0; H5CookieId=31a16c62-1019-4cb7-bf29-22d00d1b6b84; CitySearchHistory=0101%23%E5%8C%97%E4%BA%AC%23beijing%23; ShHotel=InDate=2019-04-11&CityID=0101&CityNameEN=beijing&CityNameCN=%E5%8C%97%E4%BA%AC&OutDate=2019-04-12&CityName=%E5%8C%97%E4%BA%AC; __tctmd=0.1; SHBrowseHotel=cn=02201201%2C%2C%2C%2C%2C%2C%3B90963703%2C%2C%2C%2C%2C%2C%3B40101133%2C%2C%2C%2C%2C%2C%3B90812111%2C%2C%2C%2C%2C%2C%3B91179367%2C%2C%2C%2C%2C%2C%3B&; __tctmc=0.215177870; s_sq=elongcom%3D%2526pid%253Dhotel.elong.com%25252F02201201%25252F%2526pidt%253D1%2526oid%253Djavascript%25253Avoid(0)%2526ot%253DA; __tccgd=0.1; __tctmb=0.3479634389809538.1554968343075.1554968343075.1");
        request.setHeader("Host", "hotel.elong.com");
        request.setHeader("Referer", referer);
        request.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        request.setHeader("X-Requested-With", "XMLHttpRequest");
        request.setHeader("Upgrade-Insecure-Requests","1");
        try {
            HttpResponse response = client.execute(request);
            htmlContent = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("306行" + request);
        System.out.println("307行" + htmlContent);
        JSONObject jsonObject = JSON.parseObject(htmlContent);
        String value = jsonObject.getString("value");
        JSONObject jsondata = JSON.parseObject(value);//
        JSONArray comments = jsondata.getJSONArray("Comments");//?


        return comments;
    }


}