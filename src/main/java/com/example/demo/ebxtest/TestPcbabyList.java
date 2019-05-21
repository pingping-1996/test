package com.example.demo.ebxtest;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.jsoup.Jsoup.*;

public class TestPcbabyList {
    private static final Log LOG = LogFactory.getLog(TestPcbabyList.class);


    public static void main(String[] args) {
        int processcode = 0;
        Map<String, Object> processdata = new HashMap();
        String url = "https://ks.pcbaby.com.cn/kids_bbs.shtml?q=%C5%B7%C0%B3%D1%C5";
        String html = gethtml(url);
        //System.out.println("开始："+html+" ：结束");
        Document doc = parse(html);
        try{
            Elements elements=doc.select(".aListDl");
            List<Map<String, Object>> items = new ArrayList<>();
            for(Element element:elements){
                System.out.println("uiuiuoioiopk");
                String title = element.select(".oh .aList-title a").attr("title");//帖子主题
                String reply = element.select(".lt-num .red").text();//回复数
                String href  = element.select(".aList-title a").attr("href");
                System.out.println("所有信息"+title+reply+href);

                Map map = new HashedMap();
                map.put("link", href);
                map.put("rawlink", href);
                map.put("linktype", "bbspost");
                //tasks.add(map);

                Map mapP1 = new HashedMap();
                mapP1.put("itemlink", map);
                mapP1.put("itemname", title);
                mapP1.put("reply_cnt",reply);
                //mapP1.put("posttime",posttime);
                //mapP1.put("view_cnt",view_cnt);
                items.add(mapP1);
                //论坛名称（没有）
                //浏览数（没有）
                //发表日期（没有）
            }

            String pageidx = doc.select(".pcbaby_page span").text();
            System.out.println("当前页"+pageidx);

            String nextpagee = doc.select(".pcbaby_page .next").attr("class");
            System.out.println("判断条件"+nextpagee+"结束");
            if(nextpagee.equals("")){
                System.out.println("最后一页");
            }else{
                System.out.println("不是最后一页");
                String urll = doc.select(".l-box .pcbaby_page .next").attr("href");
                System.out.println("pcbaby下一页的url"+urll);

                Map task=new HashMap();
                task.put("link",url);
                task.put("rawlink",url);
                task.put("linktype","bbspostlist");

            }
        }catch (Exception e){
            e.printStackTrace();
            processcode = 500011;
        }

    }



    // accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
     /*accept-encoding: gzip, deflate, br
    accept-language: zh-CN,zh;q=0.9
    cookie: u4ad=423esd7d6; u=423o09oj; pcsuv=1548658423804.a.1097133126; JSESSIONID=abcQTGamf0Nyr-AsmcOIw; c=423lv0hl; visitedfid=1988D2130; UM_distinctid=168ad59c0ca9c7-0b33c5da05205f-3a3a5d0c-100200-168ad59c0cb374; pcuvdata=lastAccessTime=1549096600331|visits=17; channel=101; ksSearchHistory=%u6B27%u83B1%u96C5%3A%3A%3Ahttps%3A//ks.pcbaby.com.cn/kids_bbs.shtml%2C%2C%2C%u5A06%u0446%u5E48%u95C6%uFFFD%3A%3A%3Ahttps%3A//ks.pcbaby.com.cn/kids_bbs.shtml%3Fq%3D%25E6%25AC%25A7%25E8%258E%25B1%25E9%259B%2585%26; __va8195693074b53da7be71124f2488e55=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v606ef359c0dc2f064ef92f8f313fc0cc=5de7d1c3026ce3f9c40cc1d738a8cdd8; __ve754e44c22886155b3680cd6be2fe79d=5de7d1c3026ce3f9c40cc1d738a8cdd8; __vdc80c828a1977aafd9e108769304292b=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v7ff2d92460729180b05117db9b0a681e=5de7d1c3026ce3f9c40cc1d738a8cdd8; __vaf2c2e2e5f82e3522bd203ec564c0118=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v502802=5de7d1c3026ce3f9c40cc1d738a8cdd8
    referer: https://ks.pcbaby.com.cn/kids_bbs.shtml
    upgrade-insecure-requests: 1
    user-agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36*/
    public static String gethtml(String url) {
        String htmlContent = "";
        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpClient client = builder.build();
        HttpGet request = new HttpGet(url);
        request.setHeader("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        request.setHeader("accept-encoding", "gzip, deflate, br");
        request.setHeader("accept-language", "zh-CN,zh;q=0.9");
        //request.setHeader("Connection", "keep-alive");
        //request.setHeader("Content-Type",
        //"application/x-www-form-urlencoded; charset=utf-8");
        request.setHeader("Cookie",
                "u4ad=423esd7d6; u=423o09oj; pcsuv=1548658423804.a.1097133126; JSESSIONID=abcQTGamf0Nyr-AsmcOIw; c=423lv0hl; visitedfid=1988D2130; UM_distinctid=168ad59c0ca9c7-0b33c5da05205f-3a3a5d0c-100200-168ad59c0cb374; pcuvdata=lastAccessTime=1549096600331|visits=17; channel=101; ksSearchHistory=%u6B27%u83B1%u96C5%3A%3A%3Ahttps%3A//ks.pcbaby.com.cn/kids_bbs.shtml%2C%2C%2C%u5A06%u0446%u5E48%u95C6%uFFFD%3A%3A%3Ahttps%3A//ks.pcbaby.com.cn/kids_bbs.shtml%3Fq%3D%25E6%25AC%25A7%25E8%258E%25B1%25E9%259B%2585%26; __va8195693074b53da7be71124f2488e55=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v606ef359c0dc2f064ef92f8f313fc0cc=5de7d1c3026ce3f9c40cc1d738a8cdd8; __ve754e44c22886155b3680cd6be2fe79d=5de7d1c3026ce3f9c40cc1d738a8cdd8; __vdc80c828a1977aafd9e108769304292b=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v7ff2d92460729180b05117db9b0a681e=5de7d1c3026ce3f9c40cc1d738a8cdd8; __vaf2c2e2e5f82e3522bd203ec564c0118=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v502802=5de7d1c3026ce3f9c40cc1d738a8cdd8");
        request.setHeader("upgrade-insecure-requests", "1");
        request.setHeader("referer","https://ks.pcbaby.com.cn/kids_bbs.shtml");
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
        //System.out.println("307行" + htmlContent);


        return htmlContent;
    }

}
