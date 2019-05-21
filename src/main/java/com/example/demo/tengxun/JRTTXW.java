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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JRTTXW {

    public static void main(String[] args) throws HttpProcessException {
        Header[] headers = HttpHeader.custom()
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36")
                //.cookie("tt_webid=6680431290917160456; WEATHER_CITY=%E5%8C%97%E4%BA%AC; UM_distinctid=16a25a787fd6e1-09fc4557aabdeb-3a3a5d0c-100200-16a25a787fe3d3; tt_webid=6680431290917160456; csrftoken=d77d9e47019783847d1b2ec634bd3c42; _ga=GA1.2.1529458650.1555410604; s_v_web_id=0d738da72c0ade680e1914af187d6d55; qh[360]=1; uuid=864566034698766; CNZZDATA1259612802=1989036815-1555408990-https%253A%252F%252Fwww.baidu.com%252F%7C1557392329")
                .build();
        String html = HttpClientUtil.get(HttpConfig.custom()
                .headers(headers)  //设置headers，不需要时则无需设置
                .url("https://www.toutiao.com/a6648792223620006414/").encoding("UTF-8"));
        //Document doc = Jsoup.parse(html);
        System.out.println(html);

                /*try {
                    String pattern = "item_id=(.*?)&aggr_type=0&count=20&offset=(\\d+)";
                    Pattern p = Pattern.compile(pattern);
                    Matcher m = p.matcher(url);
                    int page = 0;
                    String new_id = "";
                    if(m.find()){
                        page = Integer.valueOf(m.group(2));
                        new_id = m.group(1);
                        System.out.println(new_id);
                    }
                    String page2 = String.valueOf(page+20);
                    String nextpage = url.replace(m.group(2),page2);

                    System.out.println(nextpage);
                } catch (Exception e) {
                    e.printStackTrace();


        }*/

        //内容详情截取部分
        //System.out.println("doc==="+doc);
        String title = html.substring(html.indexOf("title: '"),html.indexOf("content: '"));
        String content = html.substring(html.indexOf("content: '"),html.indexOf(" groupId: '"));
        //System.out.println(title+content);
        String content2 = content.replace("content: '","")
            .replace("',","");

        content = content.replace("&lt;","<");
        content = content.replace("&gt;",">");

        //System.out.println("```````````````````"+content);
        Document doc = Jsoup.parse(content);

        content = doc.select("div").text();
        //System.out.println(content);

        title = title.replace("title: '","")
                .replace("',","");
        //System.out.println(title);
        String time = html.substring(html.indexOf("time: '"),html.indexOf("tagInfo:"));
        String tim2 = time.replace("time: '","").replace("},","")
                .replace("'","").replace("\n","")
                .trim();
        System.out.println(tim2);
        /*
        //列表以及评论的测试部分
        JSONObject jsonObject = JSONObject.parseObject(html);
        System.out.println(jsonObject);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        System.out.println("========="+jsonArray);
        System.out.println(jsonArray);
        for (int i=0;i<jsonArray.size();i++){
            JSONObject jsObject1 = jsonArray.getJSONObject(i);
            System.out.println((i+1)+"==="+jsObject1);
            //String title = jsObject1.getString("title");

            String title = jsObject1.getJSONObject("comment").getString("text");
            System.out.println((i+1)+title);
            //String url2 = jsObject1.getString("id");

            String url2 = jsObject1.getJSONObject("comment").getString("user_name");
            System.out.println((i+1)+url2);
        }
*/

        String str = "http://www.toutiao.com/group/6518950052948345358/";
        String pattern = "com/(.*?)(\\d+)/";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        String news_id = "";
        if(m.find()){
            news_id = m.group(2);
            System.out.println("news_id"+news_id);
        }


    }


    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");


    private static String getcode(String json) throws Exception{
        //engine.eval(script);
        engine.eval(new FileReader("D:\\BFD\\workdoc\\TTjson.js"));//D:\workdoc\aes.js
        Invocable inv = (Invocable) engine;
        return (String) inv.invokeFunction("TTjson",json);
    }

 }