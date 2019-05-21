package com.example.demo.tengxun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.google.gson.JsonArray;
import netscape.javascript.JSObject;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JRTTXWlist {

    public static void main(String[] args) throws HttpProcessException, ParseException {
        String url = "http://ic.snssdk.com/article/v4/tab_comments/?" +
                "group_id=6679139110197658116&item_id=6679139110197658116&aggr_type=0&count=20&offset=0";
        int page = 0;

        for (int j=0;j<200;j++) {
             String pattern = "offset=(\\d+)";
             Pattern p = Pattern.compile(pattern);
             Matcher m = p.matcher(url);
             if(m.find()){
                 page = Integer.valueOf(m.group(1));
                 System.out.println("匹配==="+m.group(1));
             }
            //System.out.println("nextpage==========="+url);
            Header[] headers = HttpHeader.custom()
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36")
                    //.cookie("tt_webid=6680431290917160456; WEATHER_CITY=%E5%8C%97%E4%BA%AC; UM_distinctid=16a25a787fd6e1-09fc4557aabdeb-3a3a5d0c-100200-16a25a787fe3d3; tt_webid=6680431290917160456; csrftoken=d77d9e47019783847d1b2ec634bd3c42; _ga=GA1.2.1529458650.1555410604; s_v_web_id=0d738da72c0ade680e1914af187d6d55; qh[360]=1; uuid=864566034698766; CNZZDATA1259612802=1989036815-1555408990-https%253A%252F%252Fwww.baidu.com%252F%7C1557392329")
                    .build();
            String html = HttpClientUtil.get(HttpConfig.custom()
                    .headers(headers)  //设置headers，不需要时则无需设置
                    .url(url).encoding("UTF-8"));
             System.out.println("url==="+url);
            //Document doc = Jsoup.parse(html);
            //System.out.println(html);
            JSONObject json = new JSONObject();
            List list = new ArrayList();

            //列表以及评论的测试部分
            JSONObject jsonObject = JSONObject.parseObject(html);
            System.out.println(jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("data");



            String content_url = "https://www.toutiao.com/a6679139110197658116/";

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsObject1 = jsonArray.getJSONObject(i);
                //System.out.println((i + 1) + "===" + jsObject1);
                //String title = jsObject1.getString("title");

                String content = jsObject1.getJSONObject("comment").getString("text");
                //String url2 = jsObject1.getString("id");
                String digg_count = jsObject1.getJSONObject("comment").getString("digg_count");
                String create_time = jsObject1.getJSONObject("comment").getString("create_time");
                //Long create_time2 = Long.valueOf(create_time);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String date = df.format(new Date(Long.valueOf(create_time + "000")));

                String user_name = jsObject1.getJSONObject("comment").getString("user_name");
                System.out.println((i + 1) + user_name);


                Map map = new HashMap();
                map.put("comment_content", content);
                map.put("comment_time", date);
                map.put("up_cnt", digg_count);
                map.put("username", user_name);
                list.add(map);
            }
            json.put("url", content_url);
            json.put("keyword", "深圳暴雨引发洪水已导致7人死亡4人失联");
            json.put("title", "深圳强降雨引发洪水，已致10人死亡1人失联");
            json.put("comments", list);

            System.out.println("第"+page+"条==="+json);
            String json2 = String.valueOf(json);
            contentToTxt("C:\\Users\\BFD-LT\\Desktop\\今日头条.txt","第"+page+"条==="+json);
            String page2 = String.valueOf(page+20);
            url = url.replace("&offset="+m.group(1),"&offset="+page2);

        }
    }
    public static void contentToTxt(String filePath, String content) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
                    filePath), true));
            writer.write("\n" + content);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}