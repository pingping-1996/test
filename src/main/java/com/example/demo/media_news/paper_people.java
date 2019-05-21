package com.example.demo.media_news;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class paper_people {

    public static void main(String[] args) throws HttpProcessException {
        Header[] headers = HttpHeader.custom()
                //.cookie("UM_distinctid=16abb082b80679-0075c4ae8b8b2a-3a3a5d0c-100200-16abb082b81272; tt_webid=6691198395070563843; csrftoken=0ba67588da835d05492e0c0416a80f22; s_v_web_id=0d738da72c0ade680e1914af187d6d55; tt_webid=6691198395070563843; WEATHER_CITY=%E5%8C%97%E4%BA%AC; __tasessionId=9dczg5pr51557976459560; CNZZDATA1259612802=674838410-1557912530-https%253A%252F%252Flanding.toutiao.com%252F%7C1557974089")
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36")
                .build();
        String html = HttpClientUtil.get(HttpConfig.custom()
                .headers(headers)
                //设置headers，不需要时则无需设置
                .url("http://paper.people.com.cn/rmrb/html/2019-05/20/nw.D110000renmrb_20190520_6-01.htm").encoding("UTF-8"));
        //JSONObject jsonObject = JSONObject.parseObject(html);
        //System.out.println(html);
        String str = html.substring(html.indexOf("<!--目录与标题-->"),html.indexOf("<!--结束工具条-->"))
                .replace("<!--目录与标题-->","");

        Document doc = Jsoup.parse(str);
        System.out.println(doc);
        String title = doc.select("h1").html();
        Elements elements = doc.select("#articleContent p");
        StringBuffer sub = new StringBuffer();
        for (Element element:elements) {
            String content = element.text();
            System.out.println(content);
            sub.append(content);
        }
        System.out.println("xiangqing"+sub);

        String time = "(\\d+)年(\\d+)月(\\d+)日";
        Pattern p = Pattern.compile(time);
        Matcher m = p.matcher(html);
        String post_time = "";
        if(m.find()){
            post_time = m.group();
        }

        System.out.println(post_time);
         /*
        //列表的解析
        String str = html.substring(html.indexOf("<!--右侧目录部分-->"),html.indexOf("<!--我要评报-->"))
                .replace("<!--右侧目录部分-->","");
        //System.out.println(str);
        Document doc = Jsoup.parse(str);
        Elements elements = doc.select("#titleList ul li");
        for (Element element:elements) {
            String li = element.html();
            String href = element.select("a").attr("href");
            String url = "http://paper.people.com.cn/rmrb/html/2019-05/20/nbs.D110000renmrb_09.htm";
            String pattern2 = "(\\d+)-(\\d+)/(\\d+)";
            Pattern r2 = Pattern.compile(pattern2);
            Matcher m2 = r2.matcher(url);
            String href2 = "";
            if(m2.find()){
                href2 = m2.group();
            }


            String href3 = "http://paper.people.com.cn/rmrb/html/"+ href2 +"/"+ href;
            System.out.println(href3);
            String title = element.select("a script").html();
            System.out.println(title+href);
            String pattern = "view\\(\"(.*?)\"\\)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(title);
            String title2 = "";
            if(m.find()){
                title2 = m.group(1);
            }
            System.out.println("什么情况===="+title2);
        }*/
     }



}
