package com.example.demo.elong;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TestCarHome {
    public static void main(String[] args) {
        String url = "https://club.autohome.com.cn/bbs/thread/069d026ad0cd1a87/60387616-1.html";
        String html = gethtml(url);
        System.out.println(html);
        List<Map<String, Object>> ceshi = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        //判断是不是第一页
        String first = doc.select(".pages .cur").get(0).text();
        System.out.println(first);
        if(first.equals("1")){//是第一页
            System.out.println("是第一页");
            //获取楼主信息
            String name = doc.select(".txtcenter .c01439a").get(0).text();
            String dengji = doc.select(".txtcenter .crade").get(0).attr("title");
            String time = doc.select(".plr26 span").get(1).text();
            String louceng = doc.select(".fr button.rightbutlz").get(0).text();
            String link = doc.select(".txtcenter a.c01439a").get(0).attr("href");

            //移除循环里面的楼主信息

        }else{
             //////////////////////////////////////////////////////////////

        }
        //直接在返回信息获取总页数===============yeshu3
        String yeshu = doc.select(".gopage .fs").text();
        System.out.println(yeshu);
        String yeshu2 = yeshu.substring(yeshu.indexOf("/ ")+2,yeshu.indexOf(" 页"));
        System.out.println("总页数："+yeshu2);
        Integer yeshu3 = Integer.valueOf(yeshu2);
        //在返回信息中获取下一页的url
        String nextpage = doc.select("#x-pages1 .afpage").attr("href");
        //浏览数
        String liulan = doc.select(".consnav #x-views").text();
        //回复数
        String huifu = doc.select(".consnav #x-replys").text();
        System.out.println(liulan+huifu);
        //循环
        String louceng111 = "";
        Elements elements = doc.select(".contstxt");
        for(Element element:elements){
            String name = element.select(".txtcenter  .c01439a").text();
            String dengji = element.select(".txtcenter  .crade").attr("title");
            String time = element.select(".plr26").text();
            Pattern pattern = Pattern.compile("\\d*-\\d*-\\d* \\d*:\\d*:\\d*");
            Matcher time2 =pattern.matcher(time);
            String time3 = "";
            if(time2.find()){
                time3 = String.valueOf(time2.group());
                System.out.println("匹配的发表时间"+time3);
            }
            louceng111 = element.select(".fr button.rightbutlz").text();
            String link = element.select(".txtcenter a.c01439a").attr("href");

            String content = element.select(".w740").text();
            System.out.println("评论："+content);
            //System.out.println("发表时间："+time3+"发表内容："+content+"楼层："+louceng111+"链接："+link
                     // +"等级："+dengji+"名字："+name);
            //if第一页则删除

            Map taskk = new HashMap();
            taskk.put("link",link);
            taskk.put("rawlink",link);
            taskk.put("linktype","bbsuserinfo");

            Map mapp  = new HashMap<>();
            mapp.put("replyusername",name);
            mapp.put("aaa",dengji);
            mapp.put("replydate",time);
            mapp.put("bbb",louceng111);
            mapp.put("replylink",taskk);
            mapp.put("replycontent",content);

            ceshi.add(mapp);
            /*楼主循环内容被移除*/
            if(louceng111.equals("楼主")){
                ceshi.remove(0);
            }
        }
        System.out.println("测试还有没有楼主了111："+ceshi);
    }





    public static String gethtml(String url) {
        String htmlContent = "";
        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpClient client = builder.build();
        HttpGet request = new HttpGet(url);
//        request.setHeader("accept-encoding", "gzip, deflate, br");
//        request.setHeader("accept-language", "zh-CN,zh;q=0.9");
//        request.setHeader("Host", "club.autohome.com.cn");
//        request.setHeader("Connection", "keep-alive");
        //"application/x-www-form-urlencoded; charset=utf-8");
//        request.setHeader("Cookie",
//                "__ah_uuid=753BF1E6-7F09-49E7-80C1-5A6D6F4E0E0F; fvlid=1548659833590weLBfRYw; sessionip=59.46.91.172; sessionid=F74560D3-1168-4B26-810A-A5800EC15410%7C%7C2019-01-28+15%3A17%3A14.704%7C%7C0; area=210102; ahpau=1; sessionvid=F1BBDAD2-27D5-4D0E-85DD-017FC0E49DBE; autoac=7553EEA2B4B8B2EFB129C17D1CFB7AC5; autotc=C4E0CAF988D5BA147088F89A463A7E3D; ahpvno=3; ref=172.18.1.114%7C0%7C0%7C0%7C2019-02-03+11%3A16%3A22.451%7C2019-01-28+15%3A20%3A07.583; ahrlid=1549163772251SkAvc2oMyo-1549163853370");
//        request.setHeader("upgrade-insecure-requests", "1");
//        request.setHeader("referer","https://club.autohome.com.cn/bbs/thread/dafcb2323596219f/60542814-1.html");
        request.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
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
