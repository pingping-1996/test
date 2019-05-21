package com.example.demo.test;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Tengxun {
    public static void main(String[] args) {
        File file = new File("D:\\TX\\tx.txt");
        List<String> list = txt2String(file);
        int j = 0;
        String url = "https://v.qq.com/x/search/?q=";

        for (String item : list) {
            try {
                Thread.sleep(900);
            } catch (Exception e) {
                e.printStackTrace();
            }
            j++;
            String url2 = url + item;
            //String url2 = "https://v.qq.com/x/search/?q=花火";
            System.out.println("链接1：" + url2);
            String html = getHtml(url2);
            Document doc = Jsoup.parse(html);
            if (doc.equals("")) {
                contentToTxt("d://TX/tx.xlsx", "腾讯：返回是空的");
                continue;
            }
            System.out.println(item);

            String pd = doc.select(".result_episode_list a").text();
            String url3;
            if(!pd.equals("")){
                url3 = doc.select(".result_episode_list a").get(0).attr("href");
                if (url3.equals("")) {
                    contentToTxt("d://TX/tx.xlsx", "腾讯：链接是空的");
                    continue;
                }
            }else{
                contentToTxt("d://TX/tx.xlsx", "腾讯：链接是空的");
                continue;
            }

            System.out.println("标签是不是空的："+item);
            if (url3.equals("")) {
                System.out.println("标签是空的："+item);
                contentToTxt("d://TX/tx.xlsx", "腾讯：视频是空的");
                continue;
            }
            System.out.println("第一个：" + url3);
            //得到第一个url再次抓取页面
            String html2 = getHtml(url3);
            Document doc2 = Jsoup.parse(html2);
            String str = doc2.select(".video_tags a").text();
            System.out.println("标签是不是空的："+str);
            if (str.equals("")) {
                System.out.println("标签空空:"+item);
                contentToTxt("d://TX/tx.xlsx", "腾讯：标签是空的");
                continue;
            }
            System.out.println("剧目类型：" + str);
            String str2 = str.substring(8);
            System.out.println("剧目类型：" + str2);
            contentToTxt("d://TX/tx.xlsx", "腾讯：" + str2);
        }
    }
    /*
     * 写入文档
     * */
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
    /*
     * 按行读取数据
     * */
    public static List<String> txt2String(File file) {
        List<String> list = new ArrayList<String>();
        // StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                // result.append(System.lineSeparator()+s);
                list.add(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    /*
    *
    * 获取静态页面
    * */
    public static String getHtml(String url) {
        String htmlContent = "";
        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpClient client = builder.build();
        builder.setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");//UA[(int) (Math.random()*UA.length)]
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        request.setHeader("X-Requested-With", "XMLHttpRequest");
        //request.setHeader("content-type", "text/plain;charset=gb2312");
        request.setHeader("content-type", "text/plain;charset=utf-8");
        //request.setHeader("content-type", "text/plain;charset=big5");
        try {
            HttpResponse response = client.execute(request);
            //htmlContent = EntityUtils.toString(response.getEntity());
            htmlContent = EntityUtils.toString(response.getEntity(), "UTF-8");
            //返回数据的乱码问题
            //htmlContent = new String(htmlContent.getBytes("ISO-8859-1"),"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlContent;
    }
}
