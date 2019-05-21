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
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TestPcbaby {
    private static final Pattern patternpage = Pattern.compile("<span>1</span>");

    public static void main(String[] args) {
        String url = "https://bbs.pcbaby.com.cn/intf/topic/counter.ajax?tid=3475887&currentUrl=/topic.do?tid=3475887&currentReferer=https://bbs.pcbaby.com.cn/topic-3475887.html&1553483884700";
        String html = gethtml(url);
        System.out.println("下载内容"+html);
        /*Document doc = Jsoup.parse(html);
        //每一页的第一层需要单抓取，并判断是不是第一页
        Matcher matcherm = patternpage.matcher(html);
        if (matcherm.find()) {//有待解决
            String contents111 = doc.select(".post_first .replyBody").text();
            String cate = doc.select(".header_crumb a").get(2).text();
            System.out.println("cate======================================"+cate);
            String name111 = doc.select(".post_wrap_first .fb a").text();
            String time111 = doc.select(".post_wrap_first .post_time").text();
            String jifen111 = doc.select(".post_wrap_first .user_title nobr").text();
            String jifen222 = jifen111.substring(jifen111.indexOf("积分") + 2, jifen111.indexOf(","));
            System.out.println(jifen111);
            System.out.println("楼主积分值：" + jifen222);
            String author_avatar = doc.select(".avatar a img").get(0).attr("src");
            System.out.println("楼主头像"+author_avatar);
            //楼主的图片集合
            Elements elements = doc.select(".post_main-pic");
            for(Element element : elements){
                String pic = doc.select(".img_link").attr("src");
                System.out.println("图片集合"+ pic);

               *//* Map reply = new HashMap();
                reply.put("img", rep_time);
                reply.put("rawimg", rep_connect);
                reply.put("imgtag", rep_name);
                //replys.add(reply);*//*
            }


            *//*楼主名字
            主贴发表时间
            回复数
            浏览数
            楼主积分
            *
            * *//*
        } else {
            String contents211 = doc.select(".replyBody").get(0).text();
            String name211 = doc.select(".post_wrap_first .fb a").text();
            String time211 = doc.select(".post_wrap_first .post_time").text();
            String jifen211 = doc.select(".post_wrap_first .user_title nobr").text();
            String jifen311 = jifen211.substring(jifen211.indexOf("积分") + 2, jifen211.indexOf(","));
            System.out.println("第一层的回复内容"+ contents211);
            System.out.println("第一层积分值：" + jifen311);
        }
        *//*
         * 第一层楼的用户名
         *
         *
         * *//*
        //循环回复楼层
        Elements elements = doc.select(".post_wrap  .itemTable");
        for (Element element : elements) {
            String rep_name = element.select(".fb a").text();//回复人用户名
            String rep_time = element.select(".post_info .post_time").text()
                    .substring(4);//回复时间
            String rep_link = element.select(".fb a").attr("href");//回复人链接
            String rep_jingyan = element.select(".user_title nobr").text();//回复人论坛积分
            String rep_jingyan2 = rep_jingyan.substring(rep_jingyan.indexOf("积分") + 2, rep_jingyan.indexOf(","));
            //System.out.println("发表时间：==================="+rep_time);
            //System.out.println("积分值：" + rep_jingyan2);
            String rep_connect = element.select(".replyBody").text();
            //System.out.println("内容============================================="+rep_connect);
            String floor = element.select(".post_floor em").text();//楼层
            if(floor.isEmpty()){
                continue;

            }
            String floorr = "";

            System.out.println(rep_connect+"初始楼层=========="+ floor);
            if(floor.equals("沙发")){
                floorr = "2";
            }else if(floor.equals("板凳")){
                floorr = "3";
            }else if(floor.equals("地板")){
                floorr = "4";
            }else if(floor.equals("地下室")){
                floorr = "5";
            }else{
                floorr = floor.replace("楼","");
            }
            System.out.println("201903051714pcbaby楼================"+floorr);
            Integer floor2 = Integer.valueOf(floorr);

            System.out.println("================"+floor2);

        }
        //http://sou.xcar.com.cn/XcarSearch/infobbssearchresult/bbs/%E6%88%B4%E6%A3%AE/none/none/none/none/240
        String jiequ = "http://sou.xcar.com.cn/XcarSearch/infobbssearchresult/bbs/%E6%88%B4%E6%A3%AE/none/none/none/none/240";
        String jiequ2 = jiequ.substring(0,jiequ.indexOf("/none/none/none/none/")+21);

        System.out.println("jdhfkjd=======!!!!!!!"+jiequ2);
        String liulan = doc.select("#views").text();//得不到浏览数

        String huifu = doc.select(".overView .yh").get(1).text();
        System.out.println("浏览：" + liulan + "回复：" + huifu);
        String kkk = doc.select(".overView").text();
        System.out.println(kkk);
        *//*
         *
         *
         *
         * 楼主名字
         * 回复时间
         * 发表时间
         * 下一页
         *
         * *//*
        //获取最后一页信息
        String lastpage = doc.select(".iNum").get(0).text();
        String lastpage2 = lastpage.substring(1, lastpage.length() - 1);
        System.out.println("总页数：" + lastpage2);
        Integer lastpage3 = Integer.valueOf(lastpage2);
        System.out.println("数字总页数"+lastpage3);
        //截取url
        String url2 = "";
        String nextPage = "";
        //https://bbs.pcbaby.com.cn/topic-573767-6.html
        String postid = url.substring(url.indexOf("topic-")+6,url.length()-7);
        System.out.println("postid:"+postid);
        if (!matcherm.find()) {
            url2 = url.substring(0, url.indexOf("-"));
            //最终nextPage = url2 + "-" + url4 + "-" + shuzi + ".html"
            String url3 = url.substring(url.indexOf("-")+1);//573767-2.html
            String url4 = url3.substring(0,url3.indexOf("-"));//573767
            String url5 = url3.substring(url3.indexOf("-")+1,url3.indexOf("."));//页码
            System.out.println("测试:"+url5);
            Integer page = Integer.valueOf(url5);
            page = page + 1;
            nextPage = url2 + "-" + url4 + "-" + page + ".html";
            System.out.println("page:"+page);
            System.out.println("lastpage3:"+lastpage3);
            //生成下一页tasks
            if( page <= lastpage3){
                Map task = new HashMap();
                    task.put("link", nextPage);
                    task.put("rawlink", nextPage);
                    task.put("linktype","bbspost");
                    System.out.println("下一页链接"+nextPage);
             }
        }else{
            //判断是否有下一页
            if(lastpage3>=2){
                url2 = url.substring(0, url.indexOf(".html"));
                System.out.println("如果是首页"+url2);
                nextPage = url2 + "-"+ 2 +".html";
                System.out.println(nextPage);
                //生成第二页的tasks
                Map task = new HashMap();
                task.put("link", nextPage);
                task.put("rawlink", nextPage);
                task.put("linktype","bbspost");
            }
        }*/

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
//        request.setHeader("Cookie",
//               "u4ad=423esd7d6; u=423o09oj; pcsuv=1548658423804.a.1097133126; /smile=1D1; __v437044519e53dcc6025b1eae8e4eec3e=5de7d1c3026ce3f9c40cc1d738a8cdd8; __vaf2c2e2e5f82e3522bd203ec564c0118=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v882a81bf1abecefbc2b577ba0c2d83b0=5de7d1c3026ce3f9c40cc1d738a8cdd8; __ve754e44c22886155b3680cd6be2fe79d=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v7ff2d92460729180b05117db9b0a681e=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v5efc595db076f53f2e30cc9a9337dd32=5de7d1c3026ce3f9c40cc1d738a8cdd8; __va8195693074b53da7be71124f2488e55=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v606ef359c0dc2f064ef92f8f313fc0cc=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v496932=5de7d1c3026ce3f9c40cc1d738a8cdd8; __vfe51ad09f7aed1d067bdc0457b9d99ac=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v502580=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v502802=5de7d1c3026ce3f9c40cc1d738a8cdd8; __v502800=5de7d1c3026ce3f9c40cc1d738a8cdd8; JSESSIONID=abcgGds1HqMzO70JkcOIw; __v502803=5de7d1c3026ce3f9c40cc1d738a8cdd8; channel=3192; visitedfid=2130D1988; pcuvdata=lastAccessTime=1549007627685|visits=7");
        request.setHeader("upgrade-insecure-requests", "1");
        request.setHeader("referer","'https://bbs.pcbaby.com.cn/topic-3475887.html");
        request.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        request.setHeader("X-Requested-With", "XMLHttpRequest");
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
