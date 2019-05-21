package com.example.demo.ebxtest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TestPcautoContent {

    public static void main(String[] args) {
        String url = "https://bbs.pcauto.com.cn/topic-18403468.html";
        String html = gethtml(url);
        //System.out.println(html);
        Document doc = Jsoup.parse(html);

        String single = doc.select(".post_r_tit a").attr("class");
        System.out.println("single========"+single);
            //判断是不是第一页
            //String url = "https://bbs.pcauto.com.cn/topic-18271106.html";
            //https://bbs.pcauto.com.cn/topic-11854908-2.html
            String urll = url.replace(".html","");
            String[] urlll = urll.split("-");
            String urllll = urlll[1];
            System.out.println("urllll===="+urllll);

            String regEx = "topic-\\d*-";//匹配不是第一页
            Pattern p =Pattern.compile(regEx);
            Matcher flag = p.matcher(url);
            String lll="";
            if(flag.find()){
                lll = flag.group();
            }

            if(!lll.equals("")){
                System.out.println("这不是第一页");
                String title = doc.select("#subjectTitle").text();//帖子主题
                Elements elements  = doc.select(".post_wrap");
                for(Element element:elements){
                    //回复内容
                    String reply_con2 = element.select(".cite").text();
                    String reply_con = element.select(".replyBody").text()
                            .replace(reply_con2,"");//回复内容
                        System.out.println("回复内容======="+reply_con);
                    String name = element.select(".needonline").text();
                        System.out.println("用户名======="+name);
                    String posttime = element.select(".post_time").text();
                        System.out.println("发表时间======"+posttime);
                    if(posttime.isEmpty()){
                        continue;
                    }

                }

            }else{

                List list = new ArrayList();
                System.out.println("这是第一页");
                String content = doc.select(".post_main .post_msg").get(0).text();
                Elements elements  = doc.select(".post_wrap");
                    b:for(Element element:elements) {
                        /*String rep = "";
                        if(!element.select(".cite").isEmpty()){
                            System.out.println("201903131030太平洋汽车");
                            rep = element.select(".cite font a").get(1).attr("href");
                            System.out.println("=============="+rep);
                            String[] str =  rep.split("_");
                            System.out.println("dfdc=-==-"+str[1].replace(".html",""));
                        }*/
                        element.select(".cite").empty();
                        String reply_con = element.select(".replyBody").text()
                                 ;//回复内容
                        System.out.println("回复内容======="+reply_con);
                        String name = element.select(".needonline").text();
                        //System.out.println("用户名======="+name);
                        String posttime = element.select(".post_time").text();
                        //System.out.println("发表时间======"+posttime);




                    /*if(posttime.isEmpty()){
                        break b;
                    }
*/
                    Map map = new HashMap();
                    map.put("usename",name);
                    list.add(map);
                        //System.out.println(map);
                    //String use_name = element.select(".ofw a").get(0).text();
                    //System.out.println(use_name);
                }
                //System.out.println(list);
            }


    }










    public static String gethtml(String url) {
        String htmlContent = "";
        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpClient client = builder.build();
        HttpGet request = new HttpGet(url);
        request.setHeader("accept-encoding", "gzip, deflate, br");
        request.setHeader("accept-language", "zh-CN,zh;q=0.9");
        //request.setHeader("Connection", "keep-alive");
        //request.setHeader("Content-Type",
        //"application/x-www-form-urlencoded; charset=utf-8");
  /*    request.setHeader("Cookie",
                "u4ad=423esd7d6; u=423o09oj; pcsuv=1548658423804.a.1097133126; /smile=1D1; __v437044519e53dcc6025b1eae8e4eec3e=5de7d1c3026ce3f9c40cc1d738a8cdd8; __vaf2c2e2e5f82e3522bd203ec564c0118=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v882a81bf1abecefbc2b577ba0c2d83b0=5de7d1c3026ce3f9c40cc1d738a8cdd8; __ve754e44c22886155b3680cd6be2fe79d=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v7ff2d92460729180b05117db9b0a681e=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v5efc595db076f53f2e30cc9a9337dd32=5de7d1c3026ce3f9c40cc1d738a8cdd8; __va8195693074b53da7be71124f2488e55=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v606ef359c0dc2f064ef92f8f313fc0cc=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v496932=5de7d1c3026ce3f9c40cc1d738a8cdd8; __vfe51ad09f7aed1d067bdc0457b9d99ac=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v502580=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v502802=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v502800=5de7d1c3026ce3f9c40cc1d738a8cdd8; JSESSIONID=abcgGds1HqMzO70JkcOIw; __v502803=5de7d1c3026ce3f9c40cc1d738a8cdd8; channel=3192; visitedfid=2130D1988; pcuvdata=lastAccessTime=1549007627685|visits=7");
        request.setHeader("upgrade-insecure-requests", "1");
        request.setHeader("referer","https://bbs.pcbaby.com.cn/topic-3475887-2.html");*/
        request.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        //request.setHeader("X-Requested-With", "XMLHttpRequest");
        try {
            HttpResponse response = client.execute(request);
            htmlContent = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("306行" + request);
        return htmlContent;
    }
}
